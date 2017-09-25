package koben.bubbles;


import android.app.ListFragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static koben.bubbles.MainActivity.database;


public class HistoryFragment extends FragmentSwitcher {

    private View viewRoot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewRoot = inflater.inflate(R.layout.fragment_list, container, false);
        TextView showHighScore = (TextView) viewRoot.findViewById(R.id.showHighScore);
        showHighScore.setTextSize(90);
        if (database.highScore() > 0) showHighScore.setText("" + database.highScore());
        else showHighScore.setText("" + 0);
        return viewRoot;
    }

}


