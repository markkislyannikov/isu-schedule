package isu.kislyannikov.isuschedule.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import isu.kislyannikov.isuschedule.Model.AllSchedule;
import isu.kislyannikov.isuschedule.Model.Lesson;
import isu.kislyannikov.isuschedule.Model.MySchedule;
import isu.kislyannikov.isuschedule.R;

public class FirstStartSearchActivity extends Activity {
    String LOG_TAG = "<FIRST_START_SEARCH_ACTIVITY> ->>>>>>";
    String SETTINGS = "SETTINGS";
    String FIRST_USE = "FIRST_USE";
    Gson gson = new Gson();

    Context _context;
    ListView listViewStartActivity;
    ArrayAdapter arrayAdapter;
    ArrayList<String> stringArrayListKeys = new ArrayList<>();
    SearchView searchView;
    AllSchedule allSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start_search);
        _context = this;

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            FileInputStream fileInputStream = openFileInput("JSON.json");
            InputStreamReader isr = new InputStreamReader(fileInputStream);
            Lesson [] lessons = gson.fromJson(isr,Lesson[].class);
            allSchedule = new AllSchedule(lessons);
            Log.d(LOG_TAG, lessons.toString());
            isr.close();
            fileInputStream.close();
            } catch (IOException e) {
            e.printStackTrace();
        }


        stringArrayListKeys = allSchedule.getKeys();
        listViewStartActivity = findViewById(R.id.listViewStartActivity);
        searchView = findViewById(R.id.searchViewStartActivity);
        listViewStartActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String key = (String)arrayAdapter.getItem(position);
                try {
                    saveSettingsFirstStart();
                    MySchedule mySchedule = new MySchedule(_context,allSchedule.getPairsByKey(key), key);
                }
                catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(FirstStartSearchActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, stringArrayListKeys);
        listViewStartActivity.setAdapter(arrayAdapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                arrayAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

    private void saveSettingsFirstStart(){
        _context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
                        .edit()
                        .putBoolean(FIRST_USE, true)
                        .apply();
    }

}