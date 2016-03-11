package com.example.ethan.pokerjournal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 3/10/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    //db info
    private static final String DATABASE_NAME = "pokerJournalDatabase";
    private static final int DATABASE_VERSION = 1;
    //tables
    private static final String TABLE_GAMES = "games";
    private static final String TABLE_BANK = "bank";
    //games fields (columns)
    private static final String GAMES_ID = "id";
    private static final String GAMES_TYPE = "type";
    private static final String GAMES_BLINDS = "blinds";//will leave out for now, since would be two fields
    private static final String GAMES_LOC = "location";
    private static final String GAMES_DATE = "date";
    private static final String GAMES_TIME = "time";
    private static final String GAMES_BUY_IN = "buyIn";
    private static final String GAMES_CASH_OUT = "cashOut";
    //bank fields (columns)
    private static final String BANK_ID = "id";
    private static final String BANK_DW = "dw"; //deposit or withdrawal
    private static final String BANK_AMOUNT = "amount";
    //create table statements
    private static final String CREATE_TABLE_GAMES = "CREATE TABLE " + TABLE_GAMES +
            "(" +
            GAMES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            GAMES_TYPE + " TEXT NOT NULL," +
            GAMES_LOC + " TEXT NOT NULL," +
            GAMES_DATE + " TEXT NOT NULL," +
            GAMES_TIME + " REAL NOT NULL," +
            GAMES_BUY_IN + " REAL NOT NULL," +
            GAMES_CASH_OUT + " REAL NOT NULL" +
            ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_GAMES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES);
            onCreate(db);
        }
    }

    //game database methods
    public void createGame(Game game) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(GAMES_ID, game.getId());
        values.put(GAMES_TYPE, game.getType());
        values.put(GAMES_LOC, game.getLocation());
        values.put(GAMES_DATE, game.getDate());
        values.put(GAMES_TIME, game.getTime());
        values.put(GAMES_BUY_IN, game.getBuyIn());
        values.put(GAMES_CASH_OUT, game.getCashOut());

        long game_return = db.insert(TABLE_GAMES, null, values);
    }

    public List<Game> getAllGames() {
        List<Game> games = new ArrayList<Game>();
        String selectQuery = "SELECT * FROM " + TABLE_GAMES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Game g = new Game();
                g.setId(c.getInt(c.getColumnIndex(GAMES_ID)));
                g.setType(c.getString(c.getColumnIndex(GAMES_TYPE)));
                g.setLocation(c.getString(c.getColumnIndex(GAMES_LOC)));
                g.setDate(c.getString(c.getColumnIndex(GAMES_DATE)));
                g.setTime(c.getInt(c.getColumnIndex(GAMES_TIME)));
                g.setBuyIn(c.getDouble(c.getColumnIndex(GAMES_BUY_IN)));
                g.setCashOut(c.getDouble(c.getColumnIndex(GAMES_CASH_OUT)));

                games.add(g);
            } while (c.moveToNext());
        }

        return games;
    }
}
