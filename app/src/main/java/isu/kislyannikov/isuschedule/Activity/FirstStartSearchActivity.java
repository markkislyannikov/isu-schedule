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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import isu.kislyannikov.isuschedule.Model.AllSchedule;
import isu.kislyannikov.isuschedule.Model.Lesson;
import isu.kislyannikov.isuschedule.Model.MySchedule;
import isu.kislyannikov.isuschedule.R;

public class FirstStartSearchActivity extends Activity {
    String LOG_TAG = "<FIRST_START_SEARCH_ACTIVITY> ->>>>>>";
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
            InputStream inputStream = openFileInput("JSON");
            if (inputStream != null) {
                InputStreamReader isr = new InputStreamReader(inputStream);
                Lesson [] lessons = gson.fromJson(isr,Lesson[].class);
                allSchedule = new AllSchedule(lessons);
                inputStream.close();
            }
        } catch (Throwable t) {
            Toast.makeText(getApplicationContext(),
                    "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }

        stringArrayListKeys = allSchedule.getKeys();
        listViewStartActivity = findViewById(R.id.listViewStartActivity);
        searchView = findViewById(R.id.searchViewStartActivity);
        listViewStartActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d(LOG_TAG, "itemClick: position =" + position + ", id = " + id);
                String key = (String)arrayAdapter.getItem(position);
                Log.d(LOG_TAG,"key");
                /*try {
                    MySchedule mySchedule = new MySchedule(_context,allSchedule.getPairsByKey(key), key);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(FirstStartSearchActivity.this, MainActivity.class);

                intent.putExtra("", (String) arrayAdapter.getItem(position));
                startActivity(intent);*/
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
//            OutputStream outputStream = openFileOutput("JSON", Context.MODE_PRIVATE);
//            try (OutputStreamWriter osw = new OutputStreamWriter(outputStream)) {
//                osw.write(json);
//                osw.close();
//            }
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
//
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

}