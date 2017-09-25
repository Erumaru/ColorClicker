package koben.bubbles;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import butterknife.BindView;
import butterknife.ButterKnife;

import static koben.bubbles.MainActivity.SOUND_MODE;
import static koben.bubbles.MainActivity.VIBRATION_MODE;
import static koben.bubbles.MainActivity.sharedPreferences;
import static koben.bubbles.MainActivity.sharedPreferencesEditor;
import static koben.bubbles.MainActivity.soundMode;
import static koben.bubbles.MainActivity.vibrationMode;


public class SettingsFragment extends Fragment implements View.OnClickListener{


    @BindView(R.id.soundBoolean) CheckBox soundBoolean;
    @BindView(R.id.vibrationBoolean) CheckBox vibrationBoolean;

    private OnFragmentInteractionListener mListener;

    public SettingsFragment() {

    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        ButterKnife.bind(this, view);
        soundBoolean.setOnClickListener(this);
        vibrationBoolean.setOnClickListener(this);
        updateModes();

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void updateModes() {
        soundBoolean.setChecked(soundMode);
        vibrationBoolean.setChecked(vibrationMode);
    }

    private void setSoundMode() {
        soundMode = soundBoolean.isChecked();
        sharedPreferencesEditor.putBoolean(SOUND_MODE, soundMode);
        sharedPreferencesEditor.commit();
    }

    private void setVibrationMode() {
        vibrationMode = vibrationBoolean.isChecked();
        sharedPreferencesEditor.putBoolean(VIBRATION_MODE, vibrationMode);
        sharedPreferencesEditor.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.soundBoolean:
                setSoundMode();
                break;
            case R.id.vibrationBoolean:
                setVibrationMode();
                break;
        }
        updateModes();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
