package isu.kislyannikov.isuschedule.Activity;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.sql.SQLOutput;
import java.util.ArrayList;

import isu.kislyannikov.isuschedule.Model.AllSchedule;
import isu.kislyannikov.isuschedule.Model.Lesson;
import isu.kislyannikov.isuschedule.Model.SelectedSchedule;
import isu.kislyannikov.isuschedule.R;

public class SearchActivity extends Activity {
    String LOG_TAG = "<SEARCH_ACTIVITY> ->>>>>>";

    Gson gson = new Gson();
    SharedPreferences sharedPreferences;
    AllSchedule allSchedule;
    Context _context;
    ListView listViewKeys;
    ArrayAdapter arrayAdapter;
    ArrayList<String> stringArrayList= new ArrayList<>();
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        sharedPreferences= getSharedPreferences("ALLSCHEDULE", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("ALLSCHEDULE","");


        _context = this;

        listViewKeys = findViewById(R.id.listViewSearch);
        searchView = (SearchView)findViewById(R.id.searchView);
        listViewKeys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d(LOG_TAG,(String)arrayAdapter.getItem(position));
                Intent intent = new Intent(SearchActivity.this, SelectedSchedulActivity.class);
                intent.putExtra("keySchedule", (String)arrayAdapter.getItem(position));
                finish();
                startActivity(intent);
            }
        });

        Lesson[] lessons = gson.fromJson(json, Lesson[].class);
        AllSchedule allSchedule = new AllSchedule(lessons);
        stringArrayList= allSchedule.getKeys();


        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, stringArrayList);
        listViewKeys.setAdapter(arrayAdapter);
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


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView);
        bottomNavigationView.setSelectedItemId(R.id.searchItemActivity);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.searchItemActivity:Activity:
                        return true;

                    case R.id.scheduleItemActivity:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;


                    case R.id.selectedItemActivity:
                        startActivity(new Intent(getApplicationContext(), SelectedActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

//                    case R.id.settingsItemActivity:
//                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
//                        overridePendingTransition(0, 0);
//                        finishAfterTransition();
//                        return true;
                }
                return false;
            }
        });
    }

}
