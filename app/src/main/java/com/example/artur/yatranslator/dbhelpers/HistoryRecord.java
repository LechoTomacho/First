package com.example.artur.yatranslator.dbhelpers;


import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.artur.yatranslator.dbhelpers.DBContract.HistoryContract.HISTORY_DATE;
import static com.example.artur.yatranslator.dbhelpers.DBContract.HistoryContract.HISTORY_ID;
import static com.example.artur.yatranslator.dbhelpers.DBContract.HistoryContract.HISTORY_IS_FAVORITE;
import static com.example.artur.yatranslator.dbhelpers.DBContract.HistoryContract.HISTORY_TEXT_AFTER_TRANSLATION;
import static com.example.artur.yatranslator.dbhelpers.DBContract.HistoryContract.HISTORY_TEXT_BEFORE_TRANSLATION;
import static com.example.artur.yatranslator.dbhelpers.DBContract.HistoryContract.HISTORY_TRANSLATION_LANGUAGE;

public class HistoryRecord {
    private long id;
    private String textBeforeTranslation;
    private String textAfterTranslation;
    private String lang;
    private Date date;
    private boolean isFavorite;

    public HistoryRecord(long id,String textBeforeTranslation,String textAfterTranslation,String lang, long date,Integer isFavorite)
    {
        this.id = id;
        this.textBeforeTranslation = textBeforeTranslation;
        this.textAfterTranslation = textAfterTranslation;
        this.lang = lang;
        this.date = new Date((long)date*1000);
        if(isFavorite==0)
            this.isFavorite = false;
        else
            this.isFavorite=true;
    }
    public  static  HistoryRecord getFromCursor(Cursor c)
    {
        int idColIndx = c.getColumnIndex(HISTORY_ID);
        int txtBeforeColIndx = c.getColumnIndex(HISTORY_TEXT_BEFORE_TRANSLATION);
        int txtAfterColIndx=c.getColumnIndex(HISTORY_TEXT_AFTER_TRANSLATION);
        int langColIndx = c.getColumnIndex(HISTORY_TRANSLATION_LANGUAGE);
        int dateColIndx = c.getColumnIndex(HISTORY_DATE);
        int isFavColIndx = c.getColumnIndex(HISTORY_IS_FAVORITE);
        return new HistoryRecord(c.getInt(idColIndx),c.getString(txtBeforeColIndx),c.getString(txtAfterColIndx),
            c.getString(langColIndx),c.getInt(dateColIndx),c.getInt(isFavColIndx));
    }



    @Override
    public String toString()
    {
        return String.format("[%s]\n[%s]\n[%s]\n[%s]\n[%s]\n[%b]\n",Long.toString(id),textBeforeTranslation,textAfterTranslation,
                lang,getStringDate(),isFavorite);

    }
    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
    public Date getDate()
    {


        return date;
    }
    public String getStringDate()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy HH:mm");
        return  sdf.format(date);

    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getTextAfterTranslation() {
        return textAfterTranslation;
    }

    public void setTextAfterTranslation(String textAfterTranslation) {
        this.textAfterTranslation = textAfterTranslation;
    }

    public String getTextBeforeTranslation() {
        return textBeforeTranslation;
    }

    public void setTextBeforeTranslation(String textBeforeTranslation) {
        this.textBeforeTranslation = textBeforeTranslation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
