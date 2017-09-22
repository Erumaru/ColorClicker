package koben.bubbles;


import android.app.ActionBar;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Vector;


public class GameFragment extends FragmentSwitcher implements View.OnClickListener {

    MediaPlayer mp[];
    private View viewRoot;
    boolean stopped = true;
    Vector<Integer> data;
    int mInterval = 800;
    int score = 0, startTime = 3, usedTime = 0;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    public Random rand = new Random();
    public int randomChoose = 1, previous = 1;
    ImageButton imageButtons[];
    TextView scores, start;
    Vibrator vibrator;

    void unsetAllViews() {
        for (int i = 0; i < 4; i ++) imageButtons[i].setVisibility(View.INVISIBLE);
        scores.setVisibility(View.INVISIBLE);
        start.setVisibility(View.VISIBLE);
    }

    void setAllViews() {
        for (int i = 0; i < 4; i ++) imageButtons[i].setVisibility(View.VISIBLE);
        scores.setVisibility(View.VISIBLE);
        start.setVisibility(View.INVISIBLE);
    }

    public boolean check(int currentButton) {
        if (score >= data.size()) return false;
        if (data.isEmpty()) return false;
        if (data.elementAt(score) == currentButton) {
            score++;
            return true;
        }
        return false;
    }

    Runnable goToLose = new Runnable() {
        @Override
        public void run() {
            LoseFragment LF = new LoseFragment();
            LF.setLastScore(score);
            switchFrag(LF);
            mHandler.removeCallbacks(goToLose);
        }
    };

    public void restart() {
        score = 0;
        scores.setText("0");
        data.clear();
        stopped = false;
        mInterval = 800;
        randomChoose = 1;
        previous = 1;
        for (int i = 0; i < 4; i ++) imageButtons[i].setImageResource(R.drawable.state_relax);
    }

    public void showScore() {
        scores.setText("" + score);
    }

    public void loose() {
        for (int i = 0; i < 4; i ++) imageButtons[i].setImageResource(R.drawable.lose_state);
        stopRepeatingTask();
        stopped = true;
        mHandler.postDelayed(goToLose, 1000);
    }

    void play(int num)
    {
        if (mp[num-1].isPlaying()) mp[num-1].stop();
        mp[num-1].start();
    }

    public void onClick(View v) {

        int index = getIndexOfTheButton(v.getId()) + 1;
        play(index);
        if (!check(index)) loose();
        showScore();
        vibrator.vibrate(100);
    }

    void showTime(int timer)
    {
        start.setText(""+timer);
    }

    Runnable startTimer = new Runnable() {
        @Override
        public void run() {
            showTime(startTime);
            if (startTime>0)
            {
                startTime--;
                mHandler.removeCallbacks(startTimer);
                mHandler.postDelayed(startTimer,1000);
            }
            else
            {
                mHandler.removeCallbacks(startTimer);
                setAllViews();
                startRepeatingTask();
                restart();
            }
        }
    };

    Runnable newOne = new Runnable() {
        @Override
        public void run() {
            imageButtons[randomChoose - 1].setImageResource(R.drawable.state_relax);
            previous = randomChoose;
            while (randomChoose == previous) {
                randomChoose = rand.nextInt(4) + 1;
            }
            imageButtons[randomChoose - 1].setImageResource(getResources().
                                getIdentifier("for_" + randomChoose, "drawable",
                                     getActivity().getPackageName()));
            data.add(randomChoose);

            usedTime+=mInterval;
            if (mInterval>500)
                mInterval-=25;
            if (mInterval>400 && mInterval<=500)
                mInterval-=10;
            if (mInterval>250 && mInterval<=400)
                mInterval-=2;
            mHandler.postDelayed(newOne, mInterval);
            Log.d(TAG, "run: " + mInterval);
        }
    };

    void startRepeatingTask() {
        mHandler.postDelayed(newOne,mInterval);
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(newOne);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.fragment_game, parent, false);

        init();

        return viewRoot;
    }

    private int getIndexOfTheButton (int id) {
        switch (id) {
            case R.id.im_1:
                return 0;
            case R.id.im_2:
                return 1;
            case R.id.im_3:
                return 2;
            case R.id.im_4:
                return 3;
        }

        return -1;
    }

    private void init()
    {
        mp = new MediaPlayer[4];
        for (int i=0; i<4; i++)
            mp[i] = MediaPlayer.create(getActivity().getApplicationContext(),R.raw.for_click);

        imageButtons = new ImageButton[4];

        data = new Vector<Integer>();
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        mHandler = new Handler();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        for (int i = 1; i <= 4; i ++ ) {
            int id = getResources().getIdentifier("im_" + i, "id", getActivity().getPackageName());
            imageButtons[i - 1] = (ImageButton) viewRoot.findViewById(id);
        }

        scores = (TextView) viewRoot.findViewById(R.id.textView);
        start = (TextView) viewRoot.findViewById(R.id.textView3);
        showScore();
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.hide();

        for (int i = 0; i < 4; i ++) {
            imageButtons[i].setOnClickListener(this);
        }

        unsetAllViews();
        mHandler.postDelayed(startTimer,0);
    }

    @Override
    public void onResume()
    {
        Log.i(TAG, "onResume");
        super.onResume();
        init();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    public void onStop()
    {
        super.onStop();
        Log.i(TAG, "onStop");
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }


}
