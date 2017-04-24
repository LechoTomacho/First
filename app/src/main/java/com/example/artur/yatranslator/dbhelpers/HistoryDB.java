package com.example.artur.yatranslator.dbhelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.artur.yatranslator.dbhelpers.DBContract.DATABASE_NAME;
import static com.example.artur.yatranslator.dbhelpers.DBContract.DATABASE_VERSION;
import static com.example.artur.yatranslator.dbhelpers.DBContract.HistoryContract.CREATE_HISTORY;
import static com.example.artur.yatranslator.dbhelpers.DBContract.HistoryContract.HISTORY_DATE;
import static com.example.artur.yatranslator.dbhelpers.DBContract.HistoryContract.HISTORY_ID;
import static com.example.artur.yatranslator.dbhelpers.DBContract.HistoryContract.HISTORY_IS_FAVORITE;
import static com.example.artur.yatranslator.dbhelpers.DBContract.HistoryContract.HISTORY_TEXT_AFTER_TRANSLATION;
import static com.example.artur.yatranslator.dbhelpers.DBContract.HistoryContract.HISTORY_TEXT_BEFORE_TRANSLATION;
import static com.example.artur.yatranslator.dbhelpers.DBContract.HistoryContract.HISTORY_TRANSLATION_LANGUAGE;
import static com.example.artur.yatranslator.dbhelpers.DBContract.HistoryContract.TABLE_HISTORY;



public class HistoryDB {
    private final Context mCtx;
    private HistoryDBHelper mDBHelper;


    private SQLiteDatabase mDB;

    public HistoryDB(Context ctx) {
        mCtx = ctx;
    }


    public static String[] getColumnsString() {
        return new String[]{HISTORY_ID, HISTORY_TEXT_BEFORE_TRANSLATION, HISTORY_TEXT_AFTER_TRANSLATION,
                HISTORY_TRANSLATION_LANGUAGE, HISTORY_DATE, HISTORY_IS_FAVORITE};

    }

    public void open() {
        mDBHelper = new HistoryDBHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    public void close() {
        if (mDBHelper != null) mDBHelper.close();
    }


    public Cursor getAllData() {
        return mDB.query(TABLE_HISTORY, null, null, null, null, null, HISTORY_DATE + " DESC");
    }


    public Cursor getFirstNRecords(int count, String beginningDate) {
        String clause = null;
        if (beginningDate != null)
            clause = String.format("$s > $s", HISTORY_DATE, beginningDate);
        return mDB.query(TABLE_HISTORY, null, clause, null, null, null, HISTORY_DATE + " DESC", Integer.toString(count));
    }


    public void addRec(String textBeforeTranslation, String textAfterTranslation,
                       String lang, String date) {
        ContentValues cv = new ContentValues();
        cv.put(HISTORY_TEXT_BEFORE_TRANSLATION, textBeforeTranslation);
        cv.put(HISTORY_TEXT_AFTER_TRANSLATION, textAfterTranslation);
        cv.put(HISTORY_TRANSLATION_LANGUAGE, lang);
        cv.put(HISTORY_DATE, date);
        cv.put(HISTORY_IS_FAVORITE, 0);
        mDB.insert(TABLE_HISTORY, null, cv);
    }


    public void delRec(long id) {
        mDB.delete(TABLE_HISTORY, HISTORY_ID + " = " + id, null);
    }

    public Cursor searchRecByTranslationText(String input, boolean searchOnlyFavorites) {
        String clause;
        if(searchOnlyFavorites)
            clause = String.format("(%s LIKE \'%%%s%%\' OR %s LIKE \'%%%s%%\') " +
                            "AND %s = 1",
                    HISTORY_TEXT_BEFORE_TRANSLATION, input, HISTORY_TEXT_AFTER_TRANSLATION, input,
                    HISTORY_IS_FAVORITE);
        else
        clause = String.format("(%s LIKE \'%%%s%%\' OR %s LIKE \'%%%s%%\')",
                HISTORY_TEXT_BEFORE_TRANSLATION, input, HISTORY_TEXT_AFTER_TRANSLATION, input);
        return mDB.query(TABLE_HISTORY, null, clause, null, null, null, HISTORY_DATE  + " DESC", null);
    }

    public Cursor getFavorites()
    {
        String clause = String.format("%s = 1",HISTORY_IS_FAVORITE);
        return  mDB.query(false,TABLE_HISTORY, null, clause, null, null, null, HISTORY_DATE + " DESC", null);
    }

    public void makeFavorite(String id, boolean favorite) {
        ContentValues cv = new ContentValues();
        if (favorite)
            cv.put(HISTORY_IS_FAVORITE, 1);
        else
            cv.put(HISTORY_IS_FAVORITE, 0);
        String clause = String.format("%s=%s", HISTORY_ID, id);
        mDB.update(TABLE_HISTORY, cv, clause, null);
    }


    private class HistoryDBHelper extends SQLiteOpenHelper {
        public HistoryDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                               int version) {
            super(context, name, factory, version);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_HISTORY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
