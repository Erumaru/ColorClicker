package koben.bubbles;

import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static koben.bubbles.MainActivity.database;

public class LoseFragment extends FragmentSwitcher implements OnClickListener
{
    @BindView(R.id.tryAgainButton) Button tryAgainButton;
    @BindView(R.id.goMenuButton) Button goMenuButton;
    @BindView(R.id.loseText) TextView showLastScore;

    private View viewRoot;
    private int lastScore;
    private double apm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        viewRoot = inflater.inflate(R.layout.fragment_lose, parent, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ButterKnife.bind(this, viewRoot);

        init();

        return viewRoot;
    }

    public void setLastScore(int lastScore) {
        this.lastScore = lastScore;
    }

    private void init()
    {
        tryAgainButton.setOnClickListener(this);
        goMenuButton.setOnClickListener(this);
        showLastScore.setText("" + lastScore);
        int highScore = database.highScore();
        if (highScore>=0)
        {
            if (highScore < lastScore) {
                database.update(lastScore, highScore);
            }

        }
        else
        {
            ContentValues cv = new ContentValues();
            cv.put("score", lastScore);
            SQLiteDatabase db = database.getWritableDatabase();
            db.insert("mytable", null, cv);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tryAgainButton:
                GameFragment GF = new GameFragment();
                switchFrag(GF);
                break;
            case R.id.goMenuButton:
                MenuFragment MF = new MenuFragment();
                switchFrag(MF);
                break;
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        init();
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onStop()
    {
        super.onStop();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }
}