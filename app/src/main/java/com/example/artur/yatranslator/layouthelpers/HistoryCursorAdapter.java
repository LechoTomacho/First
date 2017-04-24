package com.example.artur.yatranslator.layouthelpers;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.artur.yatranslator.History;
import com.example.artur.yatranslator.R;
import com.example.artur.yatranslator.dbhelpers.HistoryDB;
import com.example.artur.yatranslator.dbhelpers.HistoryRecord;

public class HistoryCursorAdapter extends SimpleCursorAdapter {

    private Context context;
    private int layout;
    private Cursor cursor;
    private final LayoutInflater inflater;
    private HistoryDB historyDB;

    private static class ViewHolder {
        TextView tvID;
        TextView tvBeforeTranslation;
        TextView tvAfterTranslation;
        TextView tvLang;
        TextView tvDate;
        CheckBox cbIsFavorite;

    }
    private int[] layoutItems_IDs = new int[]{R.id.tvID, R.id.tvBeforeTranslation, R.id.tvAfterTranslation, R.id.tvLang,
            R.id.tvDate, R.id.cbIsFavorite};

    public HistoryCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags, HistoryDB history) {
        super(context, layout, c, from, to, flags);
        this.context = context;
        this.layout = layout;
        this.inflater = LayoutInflater.from(context);
        this.cursor = c;
        try {


            this.historyDB = history;
        }
        catch (Exception ex)
        {
            String s = ex.getMessage();
        }

    }

    @Override
    public void bindView(View view, final Context ctxt, Cursor c) {
        if(view.isSelected())
            view.setBackgroundColor(0xf000f0);
        super.bindView(view, ctxt, c);
        ViewHolder holder = (ViewHolder) view.getTag();
        final HistoryRecord rec = HistoryRecord.getFromCursor(c);
            holder.tvID.setText(Long.toString(rec.getId()));
            holder.tvBeforeTranslation.setText(rec.getTextBeforeTranslation());
            holder.tvAfterTranslation.setText(rec.getTextAfterTranslation());
            holder.tvLang.setText(rec.getLang());
            holder.tvDate.setText(rec.getStringDate());
            holder.cbIsFavorite.setChecked(rec.isFavorite());
            holder.cbIsFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    historyDB.makeFavorite(Long.toString(rec.getId()), !rec.isFavorite());
                    if(History.isOnFavoriteNow)
                        HistoryCursorAdapter.this.swapCursor(historyDB.getFavorites());
                    else
                        HistoryCursorAdapter.this.swapCursor(historyDB.getAllData());


                }
                catch (Exception ex)
                {String s = ex.getMessage();}
            }
        });

    }

    @Override
    public View newView(Context ctxt, Cursor c, ViewGroup parent) {
        View view = inflater.inflate(layout,null);
        ViewHolder holder = new ViewHolder();
        holder.tvID = (TextView)view.findViewById(R.id.tvID);
        holder.tvBeforeTranslation = (TextView)view.findViewById(R.id.tvItemBeforeTranslation);
        holder.tvAfterTranslation = (TextView)view.findViewById(R.id.tvItemAfterTranslation);
        holder.tvLang = (TextView)view.findViewById(R.id.tvLang);
        holder.tvDate = (TextView)view.findViewById(R.id.tvDate);
        holder.cbIsFavorite = (CheckBox)view.findViewById(R.id.cbIsFavorite);

        view.setTag(holder);
        return view;
    }




}

