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
    ImageButton i_1;
    ImageButton i_2;
    ImageButton i_3;
    ImageButton i_4;
    TextView scores, start;
    Vibrator vibrator;

    void unsetAllViews() {
        i_1.setVisibility(View.INVISIBLE);
        i_2.setVisibility(View.INVISIBLE);
        i_3.setVisibility(View.INVISIBLE);
        i_4.setVisibility(View.INVISIBLE);
        scores.setVisibility(View.INVISIBLE);
        start.setVisibility(View.VISIBLE);
    }

    void setAllViews() {
        i_1.setVisibility(View.VISIBLE);
        i_2.setVisibility(View.VISIBLE);
        i_3.setVisibility(View.VISIBLE);
        i_4.setVisibility(View.VISIBLE);
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
        i_1.setImageResource(R.drawable.state_relax);
        i_2.setImageResource(R.drawable.state_relax);
        i_3.setImageResource(R.drawable.state_relax);
        i_4.setImageResource(R.drawable.state_relax);
    }

    public void showScore() {
        scores.setText("" + score);
    }

    public void loose() {
        i_1.setImageResource(R.drawable.lose_state);
        i_2.setImageResource(R.drawable.lose_state);
        i_3.setImageResource(R.drawable.lose_state);
        i_4.setImageResource(R.drawable.lose_state);
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

        switch(v.getId()){
            case R.id.im_1:
                if (!stopped) {
                    play(1);
                    if (!check(1)) loose();
                    showScore();
                    vibrator.vibrate(100);
                }
                break;
            case R.id.im_2:
                if (!stopped) {
                    play(2);
                    if (!check(2)) loose();
                    showScore();
                    vibrator.vibrate(100);
                }
                break;
            case R.id.im_3:
                if (!stopped) {
                    play(3);
                    if (!check(3)) loose();
                    showScore();
                    vibrator.vibrate(100);
                }
                break;
            case R.id.im_4:
                if (!stopped) {
                    play(4);
                    if (!check(4)) loose();
                    showScore();
                    vibrator.vibrate(100);
                }
                break;
        }
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
            switch (randomChoose)
            {
                case 1:
                    i_1.setImageResource(R.drawable.state_relax);
                    break;
                case 2:
                    i_2.setImageResource(R.drawable.state_relax);
                    break;
                case 3:
                    i_3.setImageResource(R.drawable.state_relax);
                    break;
                case 4:
                    i_4.setImageResource(R.drawable.state_relax);
                    break;
            }
            previous = randomChoose;
            while (randomChoose==previous) {
                randomChoose = rand.nextInt(4) + 1;
            }
            switch (randomChoose)
            {
                case 1:
                    i_1.setImageResource(R.drawable.for_1);
                    data.add(1);
                    break;
                case 2:
                    i_2.setImageResource(R.drawable.for_2);
                    data.add(2);
                    break;
                case 3:
                    i_3.setImageResource(R.drawable.for_3);
                    data.add(3);
                    break;
                case 4:
                    i_4.setImageResource(R.drawable.for_4);
                    data.add(4);
                    break;
            }
            usedTime+=mInterval;
            if (mInterval>500)
                mInterval-=20;
            if (mInterval>400 && mInterval<=500)
                mInterval-=5;
            if (mInterval>200 && mInterval<=400)
                mInterval-=1;
            mHandler.postDelayed(newOne, mInterval);
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

    private void init()
    {
        mp = new MediaPlayer[4];
        for (int i=0; i<4; i++)
            mp[i] = MediaPlayer.create(getActivity().getApplicationContext(),R.raw.for_click);
        Log.i(TAG, "in init");
        data = new Vector<Integer>();
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        mHandler = new Handler();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        i_1 = (ImageButton) viewRoot.findViewById(R.id.im_1);
        i_2 = (ImageButton) viewRoot.findViewById(R.id.im_2);
        i_3 = (ImageButton) viewRoot.findViewById(R.id.im_3);
        i_4 = (ImageButton) viewRoot.findViewById(R.id.im_4);
        scores = (TextView) viewRoot.findViewById(R.id.textView);
        start = (TextView) viewRoot.findViewById(R.id.textView3);
        showScore();
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.hide();
        i_1.setOnClickListener(this);
        i_2.setOnClickListener(this);
        i_3.setOnClickListener(this);
        i_4.setOnClickListener(this);
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
