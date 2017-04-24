package com.example.artur.yatranslator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.artur.yatranslator.dbhelpers.HistoryDB;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;


public class Translator extends AppCompatActivity {

    TextView etBeforeTranslation;
    TextView tvAfterTranslation;
    Button btnTranslate;
    Button btnGoToHistory;
    Button btnFromLang;
    Button btnToLang;
    Button btnSwapLang;

    private static final String API_KEY = "trnsl.1.1.20170420T083241Z.d06162cfc1956421.abd8c91b74ac531423d798d3f02553df69eb9b53";
    private static final String PATH = "https://translate.yandex.net/api/v1.5/tr.json/translate";
    private static String fromLang="ru";
    private static String toLang = "en";

    private static String translatedText = null;
    HistoryDB history;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_translator);
        etBeforeTranslation = (EditText)findViewById(R.id.tvBeforeTranslation);
        tvAfterTranslation = (TextView)findViewById(R.id.tvAfterTranslation);

        btnTranslate= (Button)findViewById(R.id.btnTranslate);
        btnGoToHistory=(Button)findViewById(R.id.btnGoToHistory);

        btnFromLang = (Button)findViewById(R.id.btnFromLang);
        btnFromLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Translator.this,ChooseLanguage.class);
                startActivityForResult(intent,1);
            }
        });
        btnToLang = (Button)findViewById(R.id.btnToLang);
        btnToLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Translator.this,ChooseLanguage.class);
                startActivityForResult(intent,2);
            }
        });

        history = new HistoryDB(this);
        history.open();


        View.OnClickListener oclBtnTranslate = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Translate(etBeforeTranslation.getText().toString(),String.format("%s-%s",fromLang,toLang));
            }
        };
        btnTranslate.setOnClickListener(oclBtnTranslate);
        View.OnClickListener oclBtnGoToHistory = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Translator.this, History.class);
                startActivity(intent);
            }
        };
        btnGoToHistory.setOnClickListener(oclBtnGoToHistory);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        String lang = data.getStringExtra("lang");
        if(requestCode==1)
            fromLang = lang;
        else
            toLang = lang;
    }

    public String Translate(String textForTranslation, String lang)
    {

        // Instantiate the RequestQueue.


        RequestQueue queue = Volley.newRequestQueue(this);
        String url=null;
        try {
            url = String.format("%s?key=%s&text=%s&lang=%s",PATH,API_KEY, URLEncoder.encode(textForTranslation, "UTF-8"),lang);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }



        JsonObjectRequest  stringRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        try {
                            int code = response.getInt("code");
                            if (code == 200) {
                                String text = response.getString("text");
                                text = text.substring(2,text.length()-2);
                                String lang = response.getString("lang");
                                tvAfterTranslation.setText(text);
                                translatedText = text;
                                long unixTime = System.currentTimeMillis() / 1000L;
                                history.addRec(etBeforeTranslation.getText().toString(), text, lang, Long.toString(unixTime));
                            }
                        }
                        catch (JSONException ex)
                        {
                            //show dialog
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvAfterTranslation.append("That didn't work!");
                tvAfterTranslation.append(error.networkResponse.data.toString());

            }
        });

        queue.add(stringRequest);
        return translatedText;



    }
}
