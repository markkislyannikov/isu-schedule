package isu.kislyannikov.isuschedule.Activity;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import isu.kislyannikov.isuschedule.Model.MySchedule;
import isu.kislyannikov.isuschedule.Model.SelectedSchedule;
import isu.kislyannikov.isuschedule.R;

public class SearchActivity extends Activity {
    String LOG_TAG = "<SEARCH_ACTIVITY> ->>>>>>";

    Context _context;
    ListView listViewAnimals;
    ArrayAdapter arrayAdapter;
    ArrayList<String> stringArrayList= new ArrayList<>();
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        _context = this;

        listViewAnimals = findViewById(R.id.listViewSearch);

        searchView = (SearchView)findViewById(R.id.searchView);
        listViewAnimals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d(LOG_TAG, "itemClick: position =" + position + ", id = " + id);

                Intent intent = new Intent(SearchActivity.this, MainActivity.class);


                SelectedSchedule selectedSchedule = new SelectedSchedule(_context);
                selectedSchedule.addSchedule((String)arrayAdapter.getItem(position),_context);


//                getSharedPreferences("", Context.MODE_PRIVATE)
//                        .edit()
//                        .putString("2", (String)arrayAdapter.getItem(position))
//                        .apply();
                intent.putExtra("2", (String)arrayAdapter.getItem(position));
                startActivity(intent);
            }
        });
        stringArrayList.add("2461");
        stringArrayList.add("2361");
        stringArrayList.add("2261");
        stringArrayList.add("2161");
        stringArrayList.add("2441");
        stringArrayList.add("2341");
        stringArrayList.add("2241");
        stringArrayList.add("2141");
        stringArrayList.add("2421");
        stringArrayList.add("2321");
        stringArrayList.add("2221");
        stringArrayList.add("2121");
        stringArrayList.add("Пантелеев Владимир Иннокентиевич");
        stringArrayList.add("Зинченко Анна Сергеевна");
        stringArrayList.add("Казимиров Алексей Сергеевич");
        stringArrayList.add("Рябец Леонид Владимирович");
        stringArrayList.add("Кириченко Константин Дмитриевич");
        stringArrayList.add("Зубков Олег Владимирович");
        stringArrayList.add("Семечива Наталья Леонидовна");


        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, stringArrayList);
        listViewAnimals.setAdapter(arrayAdapter);
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
                        finishAfterTransition();
                        return true;


                    case R.id.selectedItemActivity:
                        startActivity(new Intent(getApplicationContext(), SelectedActivity.class));
                        overridePendingTransition(0, 0);
                        finishAfterTransition();
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
