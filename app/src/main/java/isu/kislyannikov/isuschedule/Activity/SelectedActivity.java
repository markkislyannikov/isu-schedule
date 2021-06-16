package isu.kislyannikov.isuschedule.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import isu.kislyannikov.isuschedule.Model.SelectedSchedule;
import isu.kislyannikov.isuschedule.R;

public class SelectedActivity extends AppCompatActivity {
    String LOG_TAG = "SELECTEDACTIVITY";
    ListView listViewSelectedSchedules;
    ArrayAdapter arrayAdapter;
    ArrayList<String> stringArrayListSchedules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected);

        listViewSelectedSchedules = findViewById(R.id.selectedSchedules);

        SelectedSchedule selectedSchedule = new SelectedSchedule(this);
        stringArrayListSchedules = selectedSchedule.getScheduleKeys();

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, stringArrayListSchedules);
        listViewSelectedSchedules.setAdapter(arrayAdapter);
        listViewSelectedSchedules.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d(LOG_TAG,(String)arrayAdapter.getItem(position));
                Intent intent = new Intent(SelectedActivity.this, SelectedSchedulActivity.class);
                intent.putExtra("keySchedule", (String)arrayAdapter.getItem(position));
                intent.putExtra("activity", "SelectedActivity");
                finish();
                startActivity(intent);

            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView);
        bottomNavigationView.setSelectedItemId(R.id.selectedItemActivity);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.searchItemActivity:
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.scheduleItemActivity:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.selectedItemActivity:
                        return true;

//                    case R.id.settingsItemActivity:
//                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
                }
                return false;
            }
        });
    }
}