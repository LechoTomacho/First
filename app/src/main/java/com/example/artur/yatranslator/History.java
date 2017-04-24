package com.example.artur.yatranslator;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SearchView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.artur.yatranslator.dbhelpers.HistoryDB;
import com.example.artur.yatranslator.dbhelpers.HistoryRecord;
import com.example.artur.yatranslator.layouthelpers.HistoryCursorAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.example.artur.yatranslator.dbhelpers.DBContract.HistoryContract.HISTORY_ID;

public class History extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static boolean isOnFavoriteNow=false;
    public static ArrayList selectedItems;
    static int selectedItem;
    ListView lvRecords;
    SearchView svSearch;
    Button btnHistory;
    Button btnFavorites;
    Button btnDelete;
    HistoryDB history;
    SimpleCursorAdapter scAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_history);
        selectedItems = new ArrayList();

        history = new HistoryDB(this);
        history.open();
        int[] layoutItems_IDs = new int[]{R.id.tvID, R.id.tvBeforeTranslation, R.id.tvAfterTranslation, R.id.tvLang,
                R.id.tvDate, R.id.cbIsFavorite};


        scAdapter = new HistoryCursorAdapter(this, R.layout.history_item, null, HistoryDB.getColumnsString(), layoutItems_IDs,0,history);
        lvRecords = (ListView) findViewById(R.id.lvRecords);
        lvRecords.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        btnFavorites = (Button)findViewById(R.id.btnFavorite);
        btnFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOnFavoriteNow = true;
                Cursor c = history.getFavorites();
                scAdapter.swapCursor(c);
            }
        });

        btnHistory = (Button)findViewById(R.id.btnHistory);
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOnFavoriteNow =false;
                Cursor c = history.getAllData();
                scAdapter.swapCursor(c);
            }
        });
        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = (Cursor) lvRecords.getItemAtPosition(selectedItem);
                long id = c.getInt(c.getColumnIndex(HISTORY_ID));
                history.delRec(id);
                if(History.isOnFavoriteNow)
                   scAdapter.swapCursor(history.getFavorites());
                else
                    scAdapter.swapCursor(history.getAllData());
            }
        });


            svSearch = (SearchView) findViewById(R.id.searchView1);
        lvRecords.setAdapter(scAdapter);


        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    Cursor c = history.searchRecByTranslationText(query, isOnFavoriteNow);
                    scAdapter.swapCursor(c);
                }
                catch (Exception ex)
                {
                    String s = ex.getMessage();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


       // lvRecords.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);



        /*lvRecords.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int a =5;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        lvRecords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               try {
                   selectedItem = position;
                   view.setSelected(!view.isSelected());
                   if(!selectedItems.contains(position))
                       selectedItems.add(position);
                   else
                       selectedItems.remove(position);

                   /*CheckBox cb = (CheckBox)view.findViewById(R.id.cbIsFavorite);
                   String recID = ((TextView)view.findViewById(R.id.tvID)).getText().toString();
                   cb.setChecked(!cb.isChecked());
                   history.makeFavorite(recID,cb.isChecked());*/
               }
               catch (Exception ex)
               {
                   String s = ex.getMessage();
               }

               // lvRecords.setItemChecked(position,true);
               // lvRecords.setSelection(position);
            }
        });

        getSupportLoaderManager().initLoader(0,null,this);

       // Cursor c=history.getAllData();
        // HistoryRecord[] Records = HistoryRecord.getArrayFromCursor(c);

        getSupportLoaderManager().getLoader(0).forceLoad();
        //lvRecords.performItemClick(lvRecords,2,lvRecords.getItemIdAtPosition(2));

    }

    protected void onDestroy()
    {
        super.onDestroy();
        history.close();
    }

    public void updateCursor()
    {
        if(isOnFavoriteNow)
            scAdapter.swapCursor(history.getFavorites());
        else
            scAdapter.swapCursor(history.getAllData());
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader(this,history);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        scAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    static class MyCursorLoader extends CursorLoader {
        HistoryDB db;

        public MyCursorLoader(Context context, HistoryDB db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.getAllData();
            return cursor;
        }
    }

}

