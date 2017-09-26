package koben.bubbles;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import java.security.PublicKey;


public class MainActivity extends FragmentActivity {

    public static Database database;
    public static Handler mHandler = new Handler(Looper.getMainLooper());
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor sharedPreferencesEditor;
    public static FirebaseAuth auth = FirebaseAuth.getInstance();
    public static final int RC_SIGN_IN = 777;
    public static final String VIBRATION_MODE = "VIBRATION_MODE";
    public static final String SOUND_MODE = "SOUND_MODE";
    public static boolean vibrationMode;
    public static boolean soundMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new Database(this);
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
        vibrationMode = sharedPreferences.getBoolean(VIBRATION_MODE, true);
        soundMode = sharedPreferences.getBoolean(SOUND_MODE, true);
        if (findViewById(R.id.fragment_container_root) != null) {
            if (savedInstanceState != null)
                return;

            MenuFragment firstFragment = new MenuFragment();

            firstFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_root, firstFragment).commit();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        mHandler.removeCallbacksAndMessages(null);
        super.onBackPressed();
    }

    public void backButton (View view) {
        onBackPressed();
    }

}
