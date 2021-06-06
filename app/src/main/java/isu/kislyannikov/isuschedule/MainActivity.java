package isu.kislyannikov.isuschedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;


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
import java.util.Arrays;

import isu.kislyannikov.isuschedule.Metods.DownloadSchedule;
import isu.kislyannikov.isuschedule.Model.Lesson;
import isu.kislyannikov.isuschedule.Model.Schedule;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getSchedule();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView);
        bottomNavigationView.setSelectedItemId(R.id.scheduleActivity);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.searchActivity:
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.scheduleActivity:
                        return true;

                    case R.id.selectedActivity:
                        startActivity(new Intent(getApplicationContext(), SelectedActivity.class));
                        overridePendingTransition(0,0);
                        return true;


                    case R.id.settingsActivity:
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


       // getSchedule();
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

    public void getContent(String apiUrl) throws IOException {
        Gson gson = new Gson();

//        JsonReader jsonReader = new JsonReader();
//        Lesson[] lessons = gson.fromJson(jsonReader, Lesson[].class);
//        Schedule schedule = new Schedule(new ArrayList<>(Arrays.asList(lessons)));

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
            Schedule schedule = new Schedule(new ArrayList<>(Arrays.asList(lessons)));
            System.out.println(schedule);

            jsonReader.close();

            reader= new BufferedReader(new InputStreamReader(stream));
            StringBuilder buf=new StringBuilder();
            String line;
            System.out.println("\n\n");
            while ((line=reader.readLine()) != null) {
                buf.append(line).append("\n");
            }
            //return(buf.toString());
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
//
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
}