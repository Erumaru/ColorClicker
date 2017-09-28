package codebusters.ColorClicker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Erumaru on 28.09.17.
 */

public class InstructionDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.
                from(getActivity()).inflate(R.layout.instruction_dialog_layout, null);

        builder.setView(view);

        return builder.create();
    }
}
