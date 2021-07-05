package isu.kislyannikov.isuschedule.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


import isu.kislyannikov.isuschedule.Adapter.ScheduleAdapter;
import isu.kislyannikov.isuschedule.Model.Lesson;
import isu.kislyannikov.isuschedule.Model.MySchedule;
import isu.kislyannikov.isuschedule.Model.Pair;
import isu.kislyannikov.isuschedule.Model.TypeOfWeek;
import isu.kislyannikov.isuschedule.R;

import static isu.kislyannikov.isuschedule.Model.AllSchedule.dayOfWeekSchedule;
import static isu.kislyannikov.isuschedule.Model.TypeOfWeek.getDaysOfTheWeek;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String LOG_TAG = "<MAIN_ACTIVITY> ->>>>>>";
    String SETTINGS = "SETTINGS";
    String FIRST_USE = "FIRST_USE";
    String MYSCHEDULE = "MYSCHEDULE";
    String myFavorites = "MYFAVORITES";
    String TYPEOFWEEK = "TYPEOFWEEK";
    String[] days = {"Пн", "Вт", "Ср", "Чт", "Пт", "Сб"};

    SharedPreferences sharedPreferences;
    MySchedule mySchedule;
    ArrayList<TextView> textViewList;
    int typeOfWeek;
    ListView listView;
    ArrayList<ArrayList<Pair>> arrayListArrayListPair;
    ScheduleAdapter scheduleAdapter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFirstSchedule();


        if (!isFirstUse()) {
            getTypeWeekSchedule();
            getFirstSchedule();
            Intent intent = new Intent(MainActivity.this, FirstStartSearchActivity.class);
            startActivity(intent);
        }
        mySchedule = new MySchedule(this);
        arrayListArrayListPair = mySchedule.getMySchedule();

        listView = findViewById(R.id.listViewMainActivity);

        this.typeOfWeek = typeOfWeek();

        TextView tvTypeOfWeek = findViewById(R.id.mainTextView);
        tvTypeOfWeek.setText(String.format("%s неделя", typeOfWeek == 0 ? "Верхняя" : "Нижняя"));


        setTitle(mySchedule.getKey());

        int dayOfWeek = dayOfWeekSchedule();
        textViewList = new ArrayList<>();

        textViewList.add(findViewById(R.id.monday_number));
        textViewList.add(findViewById(R.id.tuesday_number));
        textViewList.add(findViewById(R.id.wednesday_number));
        textViewList.add(findViewById(R.id.thursday_number));
        textViewList.add(findViewById(R.id.friday_number));
        textViewList.add(findViewById(R.id.saturday_number));

        int[] daysOfTheWeek = getDaysOfTheWeek();
        for (int i = 0; i < daysOfTheWeek.length; i++) {
            textViewList.get(i).setText(String.format("%s\n%s",days[i],daysOfTheWeek[i]));
        }



        scheduleAdapter = new ScheduleAdapter(this, arrayListArrayListPair.get(0), dayOfWeek, this.typeOfWeek);
        listView.setAdapter(scheduleAdapter);

        for (int i = 0; i < textViewList.size(); i++) {
            textViewList.get(i).setSelected(false);
            textViewList.get(i).setActivated(false);
            if (i == dayOfWeek) {
//                тут будет загрузка раписания в list
                textViewList.get(i).setActivated(true);
                textViewList.get(i).setSelected(true);
            }
            textViewList.get(i).setOnClickListener(this);

        }
        Log.d(LOG_TAG, "Create");

        bottomNavigationView = findViewById(R.id.bottomNavView);
        setBottomNavigationView(bottomNavigationView);

    }


    public void onClick(View view) {
        int day = 7;
        System.out.println("click");
        switch (view.getId()) {
            case R.id.monday_number:
                day = 0;
                break;
            case R.id.tuesday_number:
                day = 1;
                break;
            case R.id.wednesday_number:
                day = 2;
                break;
            case R.id.thursday_number:
                day = 3;
                break;
            case R.id.friday_number:
                day = 4;
                break;
            case R.id.saturday_number:
                day = 5;
                break;
        }

        CharSequence charSequence = Integer.toString(day);
        scheduleAdapter = new ScheduleAdapter(this, arrayListArrayListPair.get(0), day, typeOfWeek);
        listView.setAdapter(scheduleAdapter);

        scheduleAdapter.getFilter().filter(charSequence);
        for (int i = 0; i < textViewList.size(); i++) {
            if (day == i) {
                textViewList.get(i).setSelected(true);
            } else {
                textViewList.get(i).setSelected(false);
            }
        }
    }


    private void setBottomNavigationView(BottomNavigationView bottomNavigationView) {
        //bottomNavigationView = findViewById(R.id.bottomNavView);
        bottomNavigationView.setSelectedItemId(R.id.scheduleItemActivity);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.searchItemActivity:
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.scheduleItemActivity:
                        return true;

                    case R.id.selectedItemActivity:
                        startActivity(new Intent(getApplicationContext(), SelectedActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.settingsItemActivity:
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        overridePendingTransition(0, 0);

                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        bottomNavigationView.setSelectedItemId(R.id.scheduleItemActivity);
    }

    public void getContent(String apiUrl) throws IOException {
        Gson gson = new Gson();

        BufferedReader reader = null;
        InputStream stream = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            stream = connection.getInputStream();

            JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
            Lesson[] lessons = gson.fromJson(jsonReader, Lesson[].class);

            String json = gson.toJson(lessons);

            getSharedPreferences("ALLSCHEDULE", Context.MODE_PRIVATE)
                    .edit()
                    .putString("ALLSCHEDULE", json)
                    .apply();


        } finally {
            if (reader != null) {
                reader.close();
            }
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public void getFirstSchedule() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    getContent("http://raspmath.isu.ru/getSchedule");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getTypeWeekSchedule() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Gson gson = new Gson();
                    URL url = new URL("http://raspmath.isu.ru/getSemesterData");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    InputStream stream = connection.getInputStream();

                    JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
                    TypeOfWeek[] typeOfWeeks = gson.fromJson(jsonReader, TypeOfWeek[].class);

                    String json = gson.toJson(typeOfWeeks[0]);

                    getSharedPreferences("TYPEOFWEEK", Context.MODE_PRIVATE)
                            .edit()
                            .putString("TYPEOFWEEK", json)
                            .apply();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Log.d(LOG_TAG,"Пауза");
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        Log.d(LOG_TAG, "Завершён");
    }


//    public void getSchedule(){
//        new Thread(new Runnable() {
//            public void run() {
//                try {
//                    getContent("http://raspmath.isu.ru/getSchedule");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }

    private int typeOfWeek() {
        Gson gson = new Gson();
        this.sharedPreferences = getSharedPreferences(TYPEOFWEEK, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(TYPEOFWEEK, "");
        TypeOfWeek typeOfWeek = gson.fromJson(json, TypeOfWeek.class);
        return typeOfWeek.typeOfWeek();
    }

    private boolean isFirstUse() {
        this.sharedPreferences = getSharedPreferences(this.SETTINGS, Context.MODE_PRIVATE);
        return sharedPreferences.contains(this.FIRST_USE);
    }
}