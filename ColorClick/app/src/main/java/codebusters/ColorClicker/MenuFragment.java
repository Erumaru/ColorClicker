package codebusters.ColorClicker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static codebusters.ColorClicker.MainActivity.FIRST_TIME;
import static codebusters.ColorClicker.MainActivity.RC_SIGN_IN;
import static codebusters.ColorClicker.MainActivity.auth;
import static codebusters.ColorClicker.MainActivity.databaseReference;
import static codebusters.ColorClicker.MainActivity.firebaseDatabase;
import static codebusters.ColorClicker.MainActivity.isInternetAvailable;
import static codebusters.ColorClicker.MainActivity.sharedPreferences;
import static codebusters.ColorClicker.MainActivity.sharedPreferencesEditor;

public class MenuFragment extends FragmentSwitcher implements OnClickListener
{
    @BindView(R.id.settingsButton) Button settingsButton;
    @BindView(R.id.historyButton) Button historyButton;
    @BindView(R.id.newGameButton) Button newGameButton;

    private static final String TAG = "MenuFragment";

	private View viewRoot;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
	{
		viewRoot = inflater.inflate(R.layout.fragment_menu, parent, false);
        ButterKnife.bind(this, viewRoot);
        settingsButton.setOnClickListener(this);
        historyButton.setOnClickListener(this);
        newGameButton.setOnClickListener(this);

        if (isFirstTime()) showInstructionDialog();

		return viewRoot;
	}

	private void showInstructionDialog () {
        InstructionDialog instructionDialog = new InstructionDialog();
        instructionDialog.show(getFragmentManager(), TAG);
    }

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
        {
			case R.id.newGameButton:
				GameFragment GF = new GameFragment();
				switchFrag(GF);
				break;
			case R.id.historyButton:
			    if (isInternetAvailable()) {
                    if (auth.getCurrentUser() != null) {
                        HistoryFragment LF = new HistoryFragment();
                        switchFrag(LF);
                    } else {
                        startActivityForResult(
                                AuthUI.getInstance()
                                        .createSignInIntentBuilder()
                                        .setAvailableProviders(
                                                Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                                        new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                                        .setIsSmartLockEnabled(false)
                                        .setTheme(R.style.NoActionBarFullscreen)
                                        .build(),
                                RC_SIGN_IN);
                    }
                }
                else {
                    Toast.makeText(getActivity(), R.string.no_internet_access,
                            Toast.LENGTH_LONG).show();
                }
				break;
            case R.id.settingsButton:
                SettingsFragment SF = new SettingsFragment();
                switchFrag(SF);
                break;
		}
	}

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RC_SIGN_IN:
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                databaseReference = firebaseDatabase.getReference(auth.getCurrentUser().getUid());
                HistoryFragment LF = new HistoryFragment();
                switchFrag(LF);
                return;
            } else {
                Toast.makeText(getActivity(), R.string.no_internet_access,
                        Toast.LENGTH_LONG).show();
            }
            break;
        }
    }

    private boolean isFirstTime() {
        if (sharedPreferences.contains(FIRST_TIME)) return false;
        sharedPreferencesEditor.putBoolean(FIRST_TIME, true);
        sharedPreferencesEditor.commit();
        return true;
    }

}