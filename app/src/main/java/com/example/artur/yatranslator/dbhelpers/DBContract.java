package com.example.artur.yatranslator.dbhelpers;


import android.provider.BaseColumns;


public final class DBContract {


    public static final  int    DATABASE_VERSION   = 1;
    public static final  String DATABASE_NAME      = "history.db";
    private static final String TEXT_TYPE          = " TEXT";
    private static final String INTEGER_TYPE          = " INTEGER";
    private static final String COMMA_SEP          = ",";
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public DBContract() {}

    /* Inner class that defines the table contents */
    public static abstract class HistoryContract implements BaseColumns {
        public static final String TABLE_HISTORY = "History";
        public static final String HISTORY_ID = "_id";
        public static final String HISTORY_TEXT_BEFORE_TRANSLATION = "txtBeforeTranslation";
        public static final String HISTORY_TEXT_AFTER_TRANSLATION = "txtAfterTranslation";
        public static final String HISTORY_TRANSLATION_LANGUAGE = "translationLanguage";
        public static final String HISTORY_DATE = "date";
        public static final String HISTORY_IS_FAVORITE = "isFavorite";

        public static final String CREATE_HISTORY = "CREATE TABLE " +
                TABLE_HISTORY + " (" +
                HISTORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                HISTORY_TEXT_BEFORE_TRANSLATION + TEXT_TYPE + COMMA_SEP +
                HISTORY_TEXT_AFTER_TRANSLATION + TEXT_TYPE + COMMA_SEP +
                HISTORY_TRANSLATION_LANGUAGE + TEXT_TYPE + COMMA_SEP +
                HISTORY_DATE + INTEGER_TYPE + COMMA_SEP +
                HISTORY_IS_FAVORITE + INTEGER_TYPE + " )";
        public static final String DELETE_HISTORY = "DROP TABLE IF EXISTS " + TABLE_HISTORY;



    }
}