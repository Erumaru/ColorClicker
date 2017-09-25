package koben.bubbles;

import android.app.ActionBar;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;


public class MainActivity extends FragmentActivity {

    public static Database database;
    public static Handler mHandler = new Handler(Looper.getMainLooper());;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new Database(this);
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
}
