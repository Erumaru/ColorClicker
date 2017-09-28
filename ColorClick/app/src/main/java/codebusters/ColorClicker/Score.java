package codebusters.ColorClicker;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by Erumaru on 28.09.17.
 */


public class Score implements Comparable<Score> {
    public static final String PLAYER_NAME = "PLAYER_NAME";
    public static final String PLAYER_SCORE = "PLAYER_SCORE";
    public static final String PLAYER_AVATAR = "PLAYER_AVATAR";
    public static final String EMPTY_AVATAR = "EMPTY_AVATAR";

    private String id;
    private String name;
    private String scores;
    private String photoUrl;

    public Score () {}

    public Score (String name, String scores, String photoUrl) {
        this.name = name;
        this.scores = scores;
        this.photoUrl = photoUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public int compareTo(@NonNull Score score) {
        return score.scores.compareTo(scores);
    }
}
