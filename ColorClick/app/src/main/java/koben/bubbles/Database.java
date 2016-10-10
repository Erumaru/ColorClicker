package koben.bubbles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    public Database(Context context) {
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table mytable ("
                + "id integer primary key autoincrement,"
                + "score integer,"
                + "apm double" + ");");
        db.execSQL("create table mytable2 ("
                + "vb boolean" + ");");
        ContentValues cv = new ContentValues();
        cv.put("vb", true);
        db.insert("mytable2", null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void update(int newScore, int oldScore)
    {
        ContentValues cv = new ContentValues();
        cv.put("score",newScore);
        this.getWritableDatabase().update("mytable",cv,"score = "+oldScore,null);
    }

    public int highScore() {
        String selectQuery = "SELECT  * FROM mytable";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            return Integer.parseInt(cursor.getString(1));
        }
        return -1;
    }

    public boolean vbr()
    {
        String selectQuery = "SELECT  * FROM mytable2";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            return Boolean.parseBoolean(cursor.getString(1));
        }
        return true;
    }
}
