package isu.kislyannikov.isuschedule.Model;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;


//Set<String> keys = new TreeSet<>();
//        SharedPreferences sharedPreferences = getSharedPreferences("MY_SCHEDULE", MODE_PRIVATE);
//        keys = sharedPreferences.getStringSet("MY_SCHEDULE_KEY",keys);

public class SelectedSchedule {
    //name file and name SharedPreferense file
    private final String SELECTED_SCHEDULES = "SELECTEDSCHEDULES";

    //name SharedPreferense variable include keys
    private final String SELECTED_SCHEDULES_KEYS = "SELECTSCHEDULESKEYS";

    //name SharedPreferense variable include hash
    private final String SELECTED_SCHEDULES_HASH = "SELECTSCHEDULESHASH";

    SharedPreferences sharedPreferences;
    File file;

    TreeSet<String> keys;
    ArrayList<String> hash;

    public SelectedSchedule(Context context){
        sharedPreferences= context.getSharedPreferences(SELECTED_SCHEDULES, Context.MODE_PRIVATE);
        if(sharedPreferences.contains(SELECTED_SCHEDULES_KEYS)){
            keys = new TreeSet<>(sharedPreferences.getStringSet(SELECTED_SCHEDULES_KEYS, keys));
        }
    }

    public void addSchedule(String _key, Context context){
        if(this.keys==null){
            this.keys = new TreeSet<>();
            this.keys.add(_key);
        }
        else{
            this.keys.add(_key);
        }
        context.getSharedPreferences(SELECTED_SCHEDULES, Context.MODE_PRIVATE)
                        .edit()
                        .putStringSet(SELECTED_SCHEDULES_KEYS, this.keys)
                        .apply();
    }

    public ArrayList<String> getScheduleKeys(){
        ArrayList<String> arrayListString;
        if(this.keys==null){
            arrayListString = new ArrayList<>(0);
            return arrayListString;
        }
        else {
            return new ArrayList<>(this.keys);
        }
    }

}
