package codebusters.ColorClicker;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;
import java.util.Vector;

import static codebusters.ColorClicker.MainActivity.mHandler;
import static codebusters.ColorClicker.MainActivity.soundMode;
import static codebusters.ColorClicker.MainActivity.vibrationMode;


public class GameFragment extends FragmentSwitcher implements View.OnClickListener {

    private static final String TAG = "GameFragment";
    MediaPlayer mp[];
    private View viewRoot;
    boolean stopped = true;
    Vector<Integer> data;
    int mInterval = 800;
    int score = 0, startTime = 3, usedTime = 0;
    public Random rand = new Random();
    public int randomChoose = 1, previous = 1;
    ImageButton imageButtons[];
    TextView scores, start;
    Vibrator vibrator;

    private void unsetAllViews() {
        for (int i = 0; i < 4; i ++) imageButtons[i].setVisibility(View.INVISIBLE);
        scores.setVisibility(View.INVISIBLE);
        start.setVisibility(View.VISIBLE);
    }

    private void setAllViews() {
        for (int i = 0; i < 4; i ++) imageButtons[i].setVisibility(View.VISIBLE);
        scores.setVisibility(View.VISIBLE);
        start.setVisibility(View.INVISIBLE);
    }

    private boolean check(int currentButton) {
        if (score >= data.size()) return false;
        if (data.isEmpty()) return false;
        if (data.elementAt(score) == currentButton) {
            score ++;
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

    private void restart() {
        score = 0;
        scores.setText("0");
        data.clear();
        stopped = false;
        mInterval = 800;
        randomChoose = 1;
        previous = 1;
        for (int i = 0; i < 4; i ++) imageButtons[i].setImageResource(R.drawable.state_relax);
    }

    private void showScore() {
        scores.setText("" + score);
    }

    private void loose() {
        for (int i = 0; i < 4; i ++) imageButtons[i].setImageResource(R.drawable.lose_state);
        stopRepeatingTask();
        stopped = true;
        mHandler.postDelayed(goToLose, 1000);
    }

    private void play(int num)
    {
        if (!soundMode) return;
        if (mp[num - 1].isPlaying()) mp[num - 1].stop();
        mp[num - 1].start();
    }

    private void vibrate() {
        if (!vibrationMode) return;
        vibrator.vibrate(100);
    }

    public void onClick(View v) {

        int index = getIndexOfTheButton(v.getId()) + 1;
        play(index);
        if (!check(index)) loose();
        showScore();
        vibrate();
    }

    private void showTime(int timer)
    {
        start.setText("" + timer);
    }

    Runnable startTimer = new Runnable() {
        @Override
        public void run() {
            showTime(startTime);
            if (startTime > 0)
            {
                startTime --;
                mHandler.removeCallbacks(startTimer);
                mHandler.postDelayed(startTimer, 1000);
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

            usedTime += mInterval;
            if (mInterval > 500)
                mInterval -= 25;
            if (mInterval > 400 && mInterval <= 500)
                mInterval -= 10;
            if (mInterval > 250 && mInterval <= 400)
                mInterval -= 2;
            mHandler.postDelayed(newOne, mInterval);
        }
    };

    void startRepeatingTask() {
        mHandler.postDelayed(newOne, mInterval);
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

    private void init() {
        mp = new MediaPlayer[4];
        for (int i = 0; i < 4; i++)
            mp[i] = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.for_click);

        imageButtons = new ImageButton[4];

        data = new Vector<Integer>();
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        for (int i = 1; i <= 4; i++) {
            int id = getResources().getIdentifier("im_" + i, "id", getActivity().getPackageName());
            imageButtons[i - 1] = (ImageButton) viewRoot.findViewById(id);
        }

        scores = (TextView) viewRoot.findViewById(R.id.textView);
        start = (TextView) viewRoot.findViewById(R.id.textView3);
        showScore();

        for (int i = 0; i < 4; i++) {
            imageButtons[i].setOnClickListener(this);
        }

        unsetAllViews();
        mHandler.postDelayed(startTimer, 0);
    }

}
