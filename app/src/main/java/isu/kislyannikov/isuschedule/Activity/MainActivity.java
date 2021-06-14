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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


import isu.kislyannikov.isuschedule.Model.Lesson;
import isu.kislyannikov.isuschedule.R;

import static isu.kislyannikov.isuschedule.Model.AllSchedule.dayOfWeekSchedule;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String LOG_TAG = "<MAIN_ACTIVITY> ->>>>>>";
    String SETTINGS = "SETTINGS";
    String FIRST_USE = "FIRST_USE";
    String MYSCHEDULE = "MYSCHEDULE";
    String myFavorites= "MYFAVORITES";
    SharedPreferences sharedPreferences;
    ArrayList<TextView> textViewList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getFirstSchedule();


        if(!isFirstUse()){
            Log.d(LOG_TAG,"dont has schedule");
            getFirstSchedule();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(MainActivity.this, FirstStartSearchActivity.class);
            startActivity(intent);
        }


        int dayOfWeek = dayOfWeekSchedule();
        textViewList = new ArrayList<>();
        textViewList.add(findViewById(R.id.monday_number));
        textViewList.add(findViewById(R.id.tuesday_number));
        textViewList.add(findViewById(R.id.wednesday_number));
        textViewList.add(findViewById(R.id.thursday_number));
        textViewList.add(findViewById(R.id.friday_number));
        textViewList.add(findViewById(R.id.saturday_number));

        for(int i=0; i<textViewList.size(); i++){
            textViewList.get(i).setSelected(false);
            textViewList.get(i).setActivated(false);
            if(i==dayOfWeek){
//                тут будет загрузка раписания в list
                textViewList.get(i).setActivated(true);
                textViewList.get(i).setSelected(true);
            }
            textViewList.get(i).setOnClickListener(this);

        }

        Log.d(LOG_TAG,"Create");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView);
        setBottomNavigationView(bottomNavigationView);

    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    public void onClick(View view) {
        int day=7;
        System.out.println("click");
        switch(view.getId()){
            case R.id.monday_number:
                day=0;
                break;
            case R.id.tuesday_number:
                day=1;
                break;
            case R.id.wednesday_number:
                day=2;
                break;
            case R.id.thursday_number:
                day=3;
                break;
            case R.id.friday_number:
                day=4;
                break;
            case R.id.saturday_number:
                day=5;
                break;
        }

        for(int i=0; i<textViewList.size(); i++){
            if(day==i){
                textViewList.get(i).setSelected(true);
                //подгрузка нужного раписания
            }
            else{
                textViewList.get(i).setSelected(false);
            }
        }
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
                        return true;

                    case R.id.scheduleItemActivity:
                        return true;
                    case R.id.selectedItemActivity:
                        startActivity(new Intent(getApplicationContext(), SelectedActivity.class));
                        overridePendingTransition(0,0);
                        return true;

//                    case R.id.settingsItemActivity:
//                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
//                        overridePendingTransition(0,0);
//                        finishAfterTransition();
//                        return true;
                }
                return false;
            }
        });
    }

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

            FileOutputStream outputStream = openFileOutput("JSON.json", Context.MODE_PRIVATE);
            outputStream.write(json.getBytes());
            outputStream.close();
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

    public void getFirstSchedule(){
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
//    public void getContent(String apiUrl) throws IOException {
//        Gson gson = new Gson();
//
//        BufferedReader reader=null;
//        InputStream stream = null;
//        HttpURLConnection connection = null;
//        try {
//            URL url=new URL(apiUrl);
//            connection =(HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.connect();
//            stream = connection.getInputStream();
//
//            JsonReader jsonReader = new JsonReader( new InputStreamReader(stream));
//            Lesson[] lessons = gson.fromJson(jsonReader, Lesson[].class);
//            String json = gson.toJson(lessons);
//
//
//            //AllSchedule schedule = new AllSchedule(new ArrayList<>(Arrays.asList(lessons)));
//
//            jsonReader.close();
//
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



    private boolean isFirstUse(){
        this.sharedPreferences = getSharedPreferences(this.SETTINGS,Context.MODE_PRIVATE);
        return sharedPreferences.contains(this.FIRST_USE);
    }


}