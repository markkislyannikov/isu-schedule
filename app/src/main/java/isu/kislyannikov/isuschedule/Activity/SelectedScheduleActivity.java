package isu.kislyannikov.isuschedule.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import isu.kislyannikov.isuschedule.Adapter.SelectorAdapter;
import isu.kislyannikov.isuschedule.Model.AllSchedule;
import isu.kislyannikov.isuschedule.Model.Lesson;
import isu.kislyannikov.isuschedule.Model.Pair;
import isu.kislyannikov.isuschedule.Model.SelectedSchedule;
import isu.kislyannikov.isuschedule.Model.TypeOfWeek;
import isu.kislyannikov.isuschedule.R;

import static isu.kislyannikov.isuschedule.Model.AllSchedule.dayOfWeekSchedule;
import static isu.kislyannikov.isuschedule.Model.TypeOfWeek.getDaysOfTheWeek;

public class SelectedScheduleActivity extends AppCompatActivity implements View.OnClickListener {
    String TYPEOFWEEK = "TYPEOFWEEK";
    Gson gson = new Gson();
    SharedPreferences sharedPreferences;
    Context _context;
    ArrayList<ArrayList<Pair>> pairArrayList = new ArrayList<>();
    SelectedSchedule selectedSchedule;
    boolean select;
    ArrayList<TextView> textViewList;
    int typeOfWeek;
    ListView listView;
    SelectorAdapter selectorAdapter;
    String key;
    String pastActivity;
    String[] days = {"Пн", "Вт", "Ср", "Чт", "Пн", "Сб"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_schedule);



        sharedPreferences = getSharedPreferences("ALLSCHEDULE", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("ALLSCHEDULE", "");
        Lesson[] lessons = gson.fromJson(json, Lesson[].class);
//        for(int i=0; i<lessons.length; i++){
//            System.out.println(lessons[i]);
//        }
        AllSchedule allSchedule = new AllSchedule(lessons);




        _context = this;
        selectedSchedule = new SelectedSchedule(this);
        key = (String) getIntent().getSerializableExtra("keySchedule");
        pastActivity= (String)getIntent().getSerializableExtra("activity");
        System.out.println(pastActivity);

        Log.d("LOG->>>>>>>>>>>>>>>", key);



        select = selectedSchedule.isKeySetInSelected(key);


        int count=0;
        for(ArrayList<Pair> lp:  allSchedule.getPairsByKey(key)){
            System.out.println(count);
            for(Pair p:  lp){
                System.out.println(p);
            }
            count++;
        }



//        for(ArrayList<Pair> lp:  allSchedule.getPairsByKey(key)){
//            System.out.println();
//            for(Pair p:  lp){
//                System.out.println(p);
//            }
//        }
        listView = findViewById(R.id.listViewMainActivity);

        this.typeOfWeek = typeOfWeek();
        pairArrayList = allSchedule.getPairsByKey(key);

        TextView tvTypeOfWeek = findViewById(R.id.mainTextView);
        tvTypeOfWeek.setText(String.format("%s неделя", typeOfWeek == 0 ? "Верхняя" : "Нижняя"));


        setTitle(key);

        int dayOfWeek = dayOfWeekSchedule();
        textViewList = new ArrayList<>();
        textViewList.add(findViewById(R.id.monday_number));
        textViewList.add(findViewById(R.id.tuesday_number));
        textViewList.add(findViewById(R.id.wednesday_number));
        textViewList.add(findViewById(R.id.thursday_number));
        textViewList.add(findViewById(R.id.friday_number));
        textViewList.add(findViewById(R.id.saturday_number));


        int[] daysOfTheWeek = getDaysOfTheWeek();
        for (int i = 0; i < daysOfTheWeek.length; i++) {
            textViewList.get(i).setText(String.format("%s\n%s",days[i],daysOfTheWeek[i]));
        }

        selectorAdapter = new SelectorAdapter(this, pairArrayList.get(dayOfWeek), this.typeOfWeek);
        listView.setAdapter(selectorAdapter);

        for (int i = 0; i < textViewList.size(); i++) {
            textViewList.get(i).setSelected(false);
            textViewList.get(i).setActivated(false);
            if (i == dayOfWeek) {
//                тут будет загрузка раписания в list
                textViewList.get(i).setActivated(true);
                textViewList.get(i).setSelected(true);
            }
            textViewList.get(i).setOnClickListener(this);
        }
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    public void onClick(View view) {
        int day = 7;
        System.out.println("click");
        switch (view.getId()) {
            case R.id.monday_number:
                day = 0;
                break;
            case R.id.tuesday_number:
                day = 1;
                break;
            case R.id.wednesday_number:
                day = 2;
                break;
            case R.id.thursday_number:
                day = 3;
                break;
            case R.id.friday_number:
                day = 4;
                break;
            case R.id.saturday_number:
                day = 5;
                break;
        }

        CharSequence charSequence = Integer.toString(day);
        selectorAdapter = new SelectorAdapter(_context, pairArrayList.get(day),typeOfWeek);
        listView.setAdapter(selectorAdapter);

        selectorAdapter.getFilter().filter(charSequence);
        for (int i = 0; i < textViewList.size(); i++) {
            if (day == i) {
                textViewList.get(i).setSelected(true);
            } else {
                textViewList.get(i).setSelected(false);
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_icon, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (select) {
            menu.findItem(R.id.action_select).setVisible(true);
            menu.findItem(R.id.noaction_select).setVisible(false);
        } else {
            menu.findItem(R.id.action_select).setVisible(false);
            menu.findItem(R.id.noaction_select).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Toast toast;
        switch(item.getItemId()){
            case R.id.action_select:
                select =!select;
                selectedSchedule.deleteSelectedSchedule(key, _context);
                toast = Toast.makeText(getApplicationContext(),
                        "Удалено!", Toast.LENGTH_SHORT);
                toast.show();
                invalidateOptionsMenu();
                break;
            case R.id.noaction_select:
                select =!select;
                selectedSchedule.addSchedule(key, _context);
                toast = Toast.makeText(getApplicationContext(),
                        "Раписание добавлено!", Toast.LENGTH_SHORT);
                toast.show();
                invalidateOptionsMenu();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private int typeOfWeek(){
        Gson gson = new Gson();
        this.sharedPreferences = getSharedPreferences(TYPEOFWEEK, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(TYPEOFWEEK, "");
        TypeOfWeek typeOfWeek = gson.fromJson(json, TypeOfWeek.class);
        return typeOfWeek.typeOfWeek();
    }

    @Override
    public void onBackPressed() {
        if (pastActivity != null && pastActivity.equals("SelectedActivity")) {
            Intent intent = new Intent(this, SelectedActivity.class);
            finish();
            this.startActivity(intent);
        } else {
            System.out.println("nen");
            Intent intent = new Intent(this, SearchActivity.class);
            finish();
            this.startActivity(intent);
        }
    }

}