package isu.kislyannikov.isuschedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SearchActivity extends Activity {
    String LOG_TAG = "<SEARCH_ACTIVITY> ->>>>>>";
    ListView listViewAnimals;
    ArrayAdapter arrayAdapter;
    ArrayList<String> stringArrayList= new ArrayList<>();
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listViewAnimals = findViewById(R.id.listViewSearch);
        listViewAnimals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d("->>>>>>>>>>>>>>>>>>>>", "itemClick: position = " + position + ", id = "
                        + id);

                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                intent.putExtra("ANIMAL", (String)arrayAdapter.getItem(position));
                startActivity(intent);

            }
        });

        searchView = (SearchView)findViewById(R.id.searchView);

        stringArrayList.add("Walrus");
        stringArrayList.add("Rino");
        stringArrayList.add("Elephant");
        stringArrayList.add("Cat");
        stringArrayList.add("Puma");
        stringArrayList.add("Rat");
        stringArrayList.add("Frog");
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, stringArrayList);
        listViewAnimals.setAdapter(arrayAdapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(LOG_TAG, s);
                return false;

            }

            @Override
            public boolean onQueryTextChange(String s) {
                System.out.println(s);
                //arrayAdapter
                //Log.d(LOG_TAG, s);
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

                    case R.id.settingsItemActivity:
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }




//    @Override
//    protected void onResume() {
//        super.onResume();
//    }

}