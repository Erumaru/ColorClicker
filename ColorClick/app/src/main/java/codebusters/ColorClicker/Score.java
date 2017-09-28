package codebusters.ColorClicker;

/**
 * Created by Erumaru on 28.09.17.
 */

public class Score {
    private String name;
    private int scores;
    private String photoUrl;

    public Score () {}

    public Score (String name, int scores, String photoUrl) {
        this.name = name;
        this.scores = scores;
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

}
