package codebusters.ColorClicker;

/**
 * Created by Erumaru on 28.09.17.
 */


public class Score {
    public static final String PLAYER_NAME = "PLAYER_NAME";
    public static final String PLAYER_SCORE = "PLAYER_SCORE";
    public static final String PLAYER_AVATAR = "PLAYER_AVATAR";
    public static final String EMPTY_AVATAR = "EMPTY_AVATAR";

    private String name;
    private String scores;
    private String photoUrl;

    public Score () {}

    public Score (String name, String scores, String photoUrl) {
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

}
