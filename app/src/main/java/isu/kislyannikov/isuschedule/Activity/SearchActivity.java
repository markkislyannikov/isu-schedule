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

import java.util.ArrayList;
import java.util.Collections;

import isu.kislyannikov.isuschedule.Model.AllSchedule;
import isu.kislyannikov.isuschedule.Model.Lesson;
import isu.kislyannikov.isuschedule.R;

public class SearchActivity extends Activity {
    String LOG_TAG = "<SEARCH_ACTIVITY> ->>>>>>";

    Gson gson = new Gson();
    SharedPreferences sharedPreferences;
    BottomNavigationView bottomNavigationView;
    AllSchedule allSchedule;
    Context _context;
    ListView listViewKeys;
    ArrayAdapter arrayAdapter;
    ArrayList<String> stringArrayListKeys = new ArrayList<>();
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        sharedPreferences= getSharedPreferences("ALLSCHEDULE", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("ALLSCHEDULE","");
        Lesson[] lessons = gson.fromJson(json, Lesson[].class);
        allSchedule = new AllSchedule(lessons);
        stringArrayListKeys = allSchedule.getKeys();
        Collections.sort(stringArrayListKeys);

        _context = this;

        listViewKeys = findViewById(R.id.listViewSearch);

        searchView = (SearchView)findViewById(R.id.searchView);


        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, stringArrayListKeys);
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

        listViewKeys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d(LOG_TAG,(String)arrayAdapter.getItem(position));
                Intent intent = new Intent(SearchActivity.this, SelectedScheduleActivity.class);
                intent.putExtra("keySchedule", (String)arrayAdapter.getItem(position));
                startActivity(intent);
            }
        });

        bottomNavigation();
    }

    private void bottomNavigation(){
        bottomNavigationView = findViewById(R.id.bottomNavView);
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
        bottomNavigationView.setSelectedItemId(R.id.searchItemActivity);
    }

}
