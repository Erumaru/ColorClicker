//
//  CalculatorBrain.swift
//  Calculator
//
//  Created by Abzal on 04.10.2017.
//  Copyright © 2017 Erumaru. All rights reserved.
//

import Foundation

struct CalculatorBrain {
    
    public var userInTheMiddleOfTyping = false
    public var floatingPointAdded = false
    public var floatingPointRemainder = false
    private enum Stack {
        case operand(Double)
        case operation(String)
        case variable(String)
    }
    
    private var internalStack = [Stack]()
    
    mutating func setOperand(_ operand: Double) {
        internalStack.append(Stack.operand(operand))
    }
    
    mutating func setOperand(_ operand: String) {
        internalStack.append(Stack.variable(operand))
    }
    
    mutating func performOperation(_ operation: String) {
        internalStack.append(Stack.operation(operation))
    }
    
    mutating func undo() {
        guard !internalStack.isEmpty else {
            return
        }
        
        internalStack.removeLast()
    }
    
    private enum Operation {
        case constant(Double)
        case unaryOperation((Double) -> Double, ((String) -> String)?)
        case binaryOperation((Double, Double) -> Double, ((String, String) -> String)?)
        case equal
    }
    
    private var operations: Dictionary <String, Operation> = [
        "e" : Operation.constant(M_E),
        "+" : Operation.binaryOperation(+, nil),
        "*" : Operation.binaryOperation(*, nil),
        "/" : Operation.binaryOperation(/, nil),
        "-" : Operation.binaryOperation(-, nil),
        "cos" : Operation.unaryOperation(cos, nil),
        "sin" : Operation.unaryOperation(sin, nil),
        "±" : Operation.unaryOperation({-$0}, nil),
        "=" : Operation.equal
    ]
    
    private struct PendingBinaryOperation {
        let function: (Double, Double) -> Double
        let firstOperand: Double
        let descriptionFunction: (String, String) -> String
        let descriptionOperand: String
        
        func perform(with secondOperand: Double) -> Double {
            return function(firstOperand, secondOperand)
        }
        
        func performDescription(with secondOperand: String) -> String {
            return descriptionFunction(descriptionOperand, secondOperand)
        }
    }

    func evaluate (using variables: Dictionary <String, Double>? = nil) ->
        (result: Double?, isPending: Bool, description: String) {
        
            var cache: (accumulator: Double?, descriptionAccumulator: String?)
            var pendingBinaryOperation: PendingBinaryOperation?
            var result: Double? {
                get {
                    return cache.accumulator
                }
            }
            
            var description: String? {
                get {
                    if pendingBinaryOperation == nil {
                        return cache.descriptionAccumulator
                    }
                    
                    return pendingBinaryOperation!.descriptionFunction(pendingBinaryOperation!.descriptionOperand,
                                                                       cache.descriptionAccumulator ?? "")
                }
            }
            
            var resultIsPending: Bool {
                get {
                    return pendingBinaryOperation != nil
                }
            }
            
            
            func setOperand(number operand: Double) {
                cache.accumulator = operand
                if let value = cache.accumulator {
                    cache.descriptionAccumulator = ViewController.formatter.string(from: NSNumber(value : value)) ?? " "
                }
            }
            
            func setOperand(variable operand: String) {
                cache.accumulator = variables?[operand] ?? 0
                cache.descriptionAccumulator = operand
            }
            
            func performOperation (_ symbol: String) {
                if let operation = operations[symbol] {
                    switch operation {
                    case .constant(let value):
                        cache = (value, symbol)
                        break
                        
                    case .unaryOperation(let function, var descriptionFunction):
                        if cache.accumulator != nil {
                            cache.accumulator = function(cache.accumulator!)
                            
                            if descriptionFunction == nil {
                                descriptionFunction = {symbol + "(" + $0 + ")"}
                            }
                            cache.descriptionAccumulator = descriptionFunction!(cache.descriptionAccumulator!)
                        }
                    case .binaryOperation(let function, var descriptionFunction):
                        performBinaryOperation()
                        if cache.accumulator != nil {
                            if descriptionFunction == nil {
                                descriptionFunction =  {"(\($0) \(symbol) \($1))"}
                            }
                            
                            pendingBinaryOperation = PendingBinaryOperation (function: function,
                                                                             firstOperand: cache.accumulator!,
                                                                             descriptionFunction: descriptionFunction!,
                                                                             descriptionOperand: cache.descriptionAccumulator!)
                            cache = (nil, nil)
                        }
                    case .equal:
                        performBinaryOperation()
                    }
                }
            }
            
            func performBinaryOperation() {
                if pendingBinaryOperation != nil && cache.accumulator != nil {
                    cache.accumulator = pendingBinaryOperation!.perform(with: cache.accumulator!)
                    cache.descriptionAccumulator = pendingBinaryOperation?.performDescription(with: cache.descriptionAccumulator!)
                    
                    pendingBinaryOperation = nil
                }
            }
            
            guard !internalStack.isEmpty else {
                return (nil, false, " ")
            }
            for operation in internalStack {
                switch operation {
                case .operand(let operand):
                    setOperand(number: operand)
                case .operation(let inOperation):
                    performOperation(inOperation)
                case .variable(let variable):
                    setOperand(variable: variable)
                }
            }
            
            return (result, resultIsPending, description ?? " ")
    }
    
    mutating public func touchDigit (_ currentValue: String, _ touchedDigit: String) -> String {
        if touchedDigit == "." {
            if !floatingPointAdded {
                if !floatingPointRemainder {
                    floatingPointRemainder = true
                }
            }
            return currentValue
        }
        
        if floatingPointRemainder {
            floatingPointAdded = true
            floatingPointRemainder = false
            userInTheMiddleOfTyping = true
            return currentValue + "." + touchedDigit
        }
        
        if userInTheMiddleOfTyping {
            return currentValue + touchedDigit
        }
        else {
            userInTheMiddleOfTyping = true
            return touchedDigit
        }
    }
    
    mutating func clear() {
        internalStack.removeAll()
    }
}
