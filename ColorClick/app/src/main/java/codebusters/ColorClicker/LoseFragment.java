package codebusters.ColorClicker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.Math.max;
import static codebusters.ColorClicker.MainActivity.MAX_SCORE;
import static codebusters.ColorClicker.MainActivity.auth;
import static codebusters.ColorClicker.MainActivity.databaseReference;
import static codebusters.ColorClicker.MainActivity.sharedPreferences;
import static codebusters.ColorClicker.MainActivity.sharedPreferencesEditor;

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
        ButterKnife.bind(this, viewRoot);

        init();

        return viewRoot;
    }

    public void setLastScore(int lastScore) {
        this.lastScore = lastScore;
    }

    private void init()
    {
        int maxScore = max(sharedPreferences.getInt(MAX_SCORE, 0), lastScore);
        sharedPreferencesEditor.putInt(MAX_SCORE, maxScore);
        sharedPreferencesEditor.commit();
        if (auth.getCurrentUser() != null) {
            databaseReference.child("score").setValue(maxScore);
            databaseReference.child("name").setValue(auth.getCurrentUser().getDisplayName());
        }
        tryAgainButton.setOnClickListener(this);
        showLastScore.setText("" + lastScore);
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
        }
    }
}