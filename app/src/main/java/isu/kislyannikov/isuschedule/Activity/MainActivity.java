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
import android.widget.TextView;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import isu.kislyannikov.isuschedule.Model.Lesson;
import isu.kislyannikov.isuschedule.Model.Schedule;
import isu.kislyannikov.isuschedule.R;

public class MainActivity extends AppCompatActivity {
    String LOG_TAG = "<MAIN_ACTIVITY> ->>>>>>";
    String settings = "SETTINGS";
    String firstUse = "FIRST_USE";
    String mySchedule = "MYSCHEDULE";
    String myFavorites= "MYFAVORITES";
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSchedule();

        if(isFirstUse()){
            Intent intent = new Intent(MainActivity.this, StartActivtity.class);
            getSchedule();
            startActivity(intent);
        }


        Log.d(LOG_TAG,"Create");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView);

        TextView textView = (TextView)findViewById(R.id.mainTextView);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        //System.out.println(extras==null);

        setBottomNavigationView(bottomNavigationView);

        if(extras!=null && extras.containsKey("ANIMAL")) {
            String animal = (String) getIntent().getSerializableExtra("ANIMAL");
            //textView.setText(animal);
            bottomNavigationView.setVisibility(View.GONE);

        }
        else {
            textView.setText("жирная живность");
            //Log.d(LOG_TAG,"bottomNavigationView");
        }

//        Set<String> keys = new TreeSet<>();
//        SharedPreferences sharedPreferences = getSharedPreferences("MY_SCHEDULE", MODE_PRIVATE);
//        keys = sharedPreferences.getStringSet("MY_SCHEDULE_KEY",keys);

        getSharedPreferences("1", Context.MODE_PRIVATE);
        textView.setText(String.valueOf(sharedPreferences.getString("2","")));
        //if (sharedPreferences.contains("2")) {
        System.out.println("yes it have");
        String elem = sharedPreferences.getString("2","");
        System.out.println(sharedPreferences.getString("2",""));
        //textView.setText(elem);
        Log.d(LOG_TAG,sharedPreferences.getString("2",""));
        //}

    }

    private void setBottomNavigationView(BottomNavigationView bottomNavigationView){
        //bottomNavigationView = findViewById(R.id.bottomNavView);
        bottomNavigationView.setSelectedItemId(R.id.scheduleItemActivity);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.searchItemActivity:
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                        overridePendingTransition(0,0);
                        finishAfterTransition();
                        return true;

                    case R.id.scheduleItemActivity:
                        return true;

                    case R.id.selectedItemActivity:
                        startActivity(new Intent(getApplicationContext(), SelectedActivity.class));
                        overridePendingTransition(0,0);
                        finishAfterTransition();
                        return true;

                    case R.id.settingsItemActivity:
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        overridePendingTransition(0,0);
                        finishAfterTransition();
                        return true;
                }
                return false;
            }
        });
    }



//    private void getContent() throws IOException {
//        String path = "http://raspmath.isu.ru/getSchedule";
//        BufferedReader reader=null;
//        InputStream stream = null;
//        HttpURLConnection connection = null;
//        try {
//            URL url=new URL(path);
//            connection =(HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.connect();
//            stream = connection.getInputStream();
//            reader= new BufferedReader(new InputStreamReader(stream));
//            StringBuilder buf=new StringBuilder();
//            String line;
//            while ((line=reader.readLine()) != null) {
//                buf.append(line).append("\n");
//            }
//            System.out.println(buf.toString());
//        }
//        finally {
//            if (reader != null) {
//                reader.close();
//            }
//            if (stream != null) {
//                stream.close();
//            }
//            if (connection != null) {
//                connection.disconnect();
//            }
//        }
//    }

//JsonReader jsonReader = new JsonReader();System.out.println(json); //System.out.println(schedule);
    //        Lesson[] lessons = gson.fromJson(jsonReader, Lesson[].class);
//        Schedule schedule = new Schedule(new ArrayList<>(Arrays.asList(lessons)));

    //            reader= new BufferedReader(new InputStreamReader(stream));
//            StringBuilder buf=new StringBuilder();
//            String line;
//            System.out.println("\n\n");
//            while ((line=reader.readLine()) != null) {
//                buf.append(line).append("\n");
//            }
    //return(buf.toString());
    public void getContent(String apiUrl) throws IOException {
        Gson gson = new Gson();

        BufferedReader reader=null;
        InputStream stream = null;
        HttpURLConnection connection = null;
        try {
            URL url=new URL(apiUrl);
            connection =(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            stream = connection.getInputStream();

            JsonReader jsonReader = new JsonReader( new InputStreamReader(stream));
            Lesson[] lessons = gson.fromJson(jsonReader, Lesson[].class);
            String json = gson.toJson(lessons);


            Schedule schedule = new Schedule(new ArrayList<>(Arrays.asList(lessons)));

            jsonReader.close();

        }
        finally {
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

    @Override
    protected void onPause() {
        super.onPause();
        //Log.d(LOG_TAG,"Пауза");
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        Log.d(LOG_TAG,"Завершён");
    }


    public void getSchedule(){
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

    private boolean isFirstUse(){
        this.sharedPreferences = getSharedPreferences(this.settings,Context.MODE_PRIVATE);
        return sharedPreferences.contains(this.firstUse);
    }
}