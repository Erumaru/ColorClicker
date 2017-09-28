package codebusters.ColorClicker;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static codebusters.ColorClicker.MainActivity.firebaseDatabase;
import static codebusters.ColorClicker.Score.PLAYER_AVATAR;
import static codebusters.ColorClicker.Score.PLAYER_NAME;
import static codebusters.ColorClicker.Score.PLAYER_SCORE;


public class HistoryFragment extends FragmentSwitcher {

    private View viewRoot;
    @BindView(R.id.scoresList)
    RecyclerView scoresList;
    RecyclerView.LayoutManager layoutManager;
    ScoresAdapter scoresAdapter;
    List<Score> scores;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        scores = new ArrayList<>();

        viewRoot = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, viewRoot);

        firebaseDatabase.getReference().addListenerForSingleValueEvent(valueEventListener);

        layoutManager = new LinearLayoutManager(getActivity());
        scoresList.setLayoutManager(layoutManager);
        scoresAdapter = new ScoresAdapter(this, scores);
        scoresList.setAdapter(scoresAdapter);

        return viewRoot;
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (!dataSnapshot.exists()) return;
            for (DataSnapshot child: dataSnapshot.getChildren()) {
                HashMap<String, String> map = (HashMap<String, String>) child.getValue();
                Score score = new Score(map.get(PLAYER_NAME),
                                        map.get(PLAYER_SCORE),
                                        map.get(PLAYER_AVATAR));
                scores.add(score);
            }
            scoresAdapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG);
        }
    };

}


