//
//  ViewController.swift
//  Calculator
//
//  Created by Abzal on 04.10.2017.
//  Copyright © 2017 Erumaru. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    
    private var brain = CalculatorBrain()
    private var variableValues = [String : Double] ()
    
    @IBAction func clearAll(_ sender: UIButton) {
        brain.clear()
        variableValues.removeAll()
        displayResult = brain.evaluate()
    }
    
    @IBAction func backspace(_ sender: UIButton) {
        if brain.userInTheMiddleOfTyping {
            
        }
    }
    
    @IBAction func pushVar(_ sender: UIButton) {
        brain.setOperand(sender.currentTitle!)
        displayResult = brain.evaluate(using: variableValues)
    }
    
    @IBAction func setVar(_ sender: UIButton) {
        brain.userInTheMiddleOfTyping = false;
        variableValues["M"] = displayValue;
        displayResult = brain.evaluate(using: variableValues);
    }
    
    @IBOutlet private weak var display: UILabel!
    
    @IBOutlet weak var descriptionDisplay: UILabel!
    
    public static var formatter: NumberFormatter = {
        let formatter = NumberFormatter()
        formatter.minimumFractionDigits = 0
        formatter.maximumFractionDigits = 20
        formatter.minimumIntegerDigits = 1
        return formatter
    }()
    
    @IBAction private func performFunction(_ sender: UIButton) {
        
        if brain.userInTheMiddleOfTyping {
            if let value = displayValue {
                brain.setOperand(value)
            }
            brain.userInTheMiddleOfTyping = false
        }
        if let mathSymbol = sender.currentTitle {
            brain.performOperation(mathSymbol)
        }
        displayResult = brain.evaluate(using: variableValues)
        
        brain.userInTheMiddleOfTyping = false
        brain.floatingPointAdded = false
        brain.floatingPointRemainder = false
    }
    
    private var displayValue: Double? {
        get {
            return Double(display.text!)!
        }
        set {
            if let value = newValue {
                display.text = ViewController.formatter.string(from: NSNumber(value: value))
            }
        }
    }
    
    private var displayResult: (result: Double?, isPending: Bool, description: String) = (nil, false, " ") {
        didSet {
            switch displayResult {
            case (nil, _, " ") : displayValue = 0
            case (let result, _, _) : displayValue = result
            }
            
            descriptionDisplay.text = displayResult.description != " " ?
                        displayResult.description + (displayResult.isPending ? " ..." : " =") : " "
        }
    }
    
    
    @IBAction private func touchDigit(_ sender: UIButton) {
        displayValue = Double(brain.touchDigit(display.text!, sender.currentTitle!))!
    }
    
    
}

