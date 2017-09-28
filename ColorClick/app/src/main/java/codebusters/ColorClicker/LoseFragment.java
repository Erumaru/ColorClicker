package codebusters.ColorClicker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

import static codebusters.ColorClicker.MainActivity.firebaseDatabase;
import static codebusters.ColorClicker.MainActivity.isInternetAvailable;
import static codebusters.ColorClicker.Score.EMPTY_AVATAR;
import static codebusters.ColorClicker.Score.PLAYER_AVATAR;
import static codebusters.ColorClicker.Score.PLAYER_NAME;
import static codebusters.ColorClicker.Score.PLAYER_SCORE;
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
        if (isInternetAvailable() && auth.getCurrentUser() != null) {
            firebaseDatabase.getReference(auth.getCurrentUser().getUid())
                            .addListenerForSingleValueEvent(valueEventListener);

        }
        tryAgainButton.setOnClickListener(this);
        showLastScore.setText("" + lastScore);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            HashMap<String, Object> map = (HashMap<String, Object>) dataSnapshot.getValue();

            //update max score in firebase database
            databaseReference.child(PLAYER_SCORE).
                    setValue(String.valueOf(max(Integer.valueOf(map.get(PLAYER_SCORE).toString()),
                            sharedPreferences.getInt(MAX_SCORE, 0))));

            //update name
            databaseReference.child(PLAYER_NAME).setValue(auth.getCurrentUser().getDisplayName());

            //update avatar
            if (auth.getCurrentUser().getPhotoUrl() != null) databaseReference.child(PLAYER_AVATAR).
                    setValue(auth.getCurrentUser().getPhotoUrl().toString());
            else databaseReference.child(PLAYER_AVATAR).setValue(EMPTY_AVATAR);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

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