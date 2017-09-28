package codebusters.ColorClicker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends FragmentActivity {

    public static Handler mHandler = new Handler(Looper.getMainLooper());
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor sharedPreferencesEditor;
    public static FirebaseAuth auth = FirebaseAuth.getInstance();
    public static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    public static DatabaseReference databaseReference;
    public static final int RC_SIGN_IN = 777;
    public static final String VIBRATION_MODE = "VIBRATION_MODE";
    public static final String SOUND_MODE = "SOUND_MODE";
    public static final String MAX_SCORE = "MAX_SCORE";
    public static final String FIRST_TIME = "FIRST_TIME";
    public static boolean vibrationMode;
    public static boolean soundMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing db
        if (auth.getCurrentUser() != null) databaseReference =
                firebaseDatabase.getReference(auth.getCurrentUser().getUid());
        //Initializing modes
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
        vibrationMode = sharedPreferences.getBoolean(VIBRATION_MODE, true);
        soundMode = sharedPreferences.getBoolean(SOUND_MODE, true);

        if (findViewById(R.id.fragment_container_root) != null) {
            if (savedInstanceState != null)
                return;

            MenuFragment firstFragment = new MenuFragment();

            firstFragment.setArguments(getIntent().getExtras());

            getFragmentManager().beginTransaction().add(R.id.fragment_container_root, firstFragment).commit();
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
