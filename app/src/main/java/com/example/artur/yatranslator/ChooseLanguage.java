package com.example.artur.yatranslator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ChooseLanguage extends AppCompatActivity {

    Map<String,String> langs;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);

        langs = new HashMap<String, String>();
        oops();
        lv = (ListView)findViewById(R.id.lv);
        String[] l = langs.keySet().toArray(new String[langs.keySet().size()]);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,l);
        lv.setAdapter(arrayAdapter);
        lv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ne uspel(
            }
        });


    }
    void oops()
    {
        langs.put("Африкаанс","af");
        langs.put("Амхарский","am");
        langs.put("Арабский","ar");
        langs.put("Азербайджанский","az");
        langs.put("Башкирский","ba");
        langs.put("Белорусский","be");
        langs.put("Болгарский","bg");
        langs.put("Бенгальский","bn");
        langs.put("Боснийский","bs");
        langs.put("Каталанский","ca");
        langs.put("Себуанский","ceb");
        langs.put("Чешский","cs");
        langs.put("Валлийский","cy");
        langs.put("Датский","da");
        langs.put("Немецкий","de");
        langs.put("Греческий","el");
        langs.put("Английский","en");
        langs.put("Эсперанто","eo");
        langs.put("Испанский","es");
        langs.put("Эстонский","et");
        langs.put("Баскский","eu");
        langs.put("Персидский","fa");
        langs.put("Финский","fi");
        langs.put("Французский","fr");
        langs.put("Ирландский","ga");
        langs.put("Шотландский(гэльский)","gd");
        langs.put("Галисийский","gl");
        langs.put("Гуджарати","gu");
        langs.put("Иврит","he");
        langs.put("Хинди","hi");
        langs.put("Хорватский","hr");
        langs.put("Гаитянский","ht");
        langs.put("Венгерский","hu");
        langs.put("Армянский","hy");
        langs.put("Индонезийский","id");
        langs.put("Исландский","is");
        langs.put("Итальянский","it");
        langs.put("Японский","ja");
        langs.put("Яванский","jv");
        langs.put("Грузинский","ka");
        langs.put("Казахский","kk");
        langs.put("Кхмерский","km");
        langs.put("Каннада","kn");
        langs.put("Корейский","ko");
        langs.put("Киргизский","ky");
        langs.put("Латынь","la");
        langs.put("Люксембургский","lb");
        langs.put("Лаосский","lo");
        langs.put("Литовский","lt");
        langs.put("Латышский","lv");
        langs.put("Малагасийский","mg");
        langs.put("Марийский","mhr");
        langs.put("Маори","mi");
        langs.put("Македонский","mk");
        langs.put("Малаялам","ml");
        langs.put("Монгольский","mn");
        langs.put("Маратхи","mr");
        langs.put("Горномарийский","mrj");
        langs.put("Малайский","ms");
        langs.put("Мальтийский","mt");
        langs.put("Бирманский","my");
        langs.put("Непальский","ne");
        langs.put("Голландский","nl");
        langs.put("Норвежский","no");
        langs.put("Панджаби","pa");
        langs.put("Папьяменто","pap");
        langs.put("Польский","pl");
        langs.put("Португальский","pt");
        langs.put("Румынский","ro");
        langs.put("Русский","ru");
        langs.put("Сингальский","si");
        langs.put("Словацкий","sk");
        langs.put("Словенский","sl");
        langs.put("Албанский","sq");
        langs.put("Сербский","sr");
        langs.put("Сунданский","su");
        langs.put("Шведский","sv");
        langs.put("Суахили","sw");
        langs.put("Тамильский","ta");
        langs.put("Телугу","te");
        langs.put("Таджикский","tg");
        langs.put("Тайский","th");
        langs.put("Тагальский","tl");
        langs.put("Турецкий","tr");
        langs.put("Татарский","tt");
        langs.put("Удмуртский","udm");
        langs.put("Украинский","uk");
        langs.put("Урду","ur");
        langs.put("Узбекский","uz");
        langs.put("Вьетнамский","vi");
        langs.put("Коса","xh");
        langs.put("Идиш","yi");
        langs.put("Китайский","zh");

    }
}
