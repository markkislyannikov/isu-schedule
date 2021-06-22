package isu.kislyannikov.isuschedule.Model;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;


public class SelectedSchedule {
    //name file and name SharedPreferense file
    private final String SELECTED_SCHEDULES = "SELECTEDSCHEDULES";

    //name SharedPreferense variable include keys
    private final String SELECTED_SCHEDULES_KEYS = "SELECTSCHEDULESKEYS";

    //name SharedPreferense variable include hash
    private final String SELECTED_SCHEDULES_HASH = "SELECTSCHEDULESHASH";

    SharedPreferences sharedPreferences;

    TreeSet<String> keys;
    ArrayList<String> hash;

    public SelectedSchedule(Context context){
        sharedPreferences= context.getSharedPreferences(SELECTED_SCHEDULES, Context.MODE_PRIVATE);
        if(sharedPreferences.contains(SELECTED_SCHEDULES_KEYS)){
            keys = new TreeSet<>(sharedPreferences.getStringSet(SELECTED_SCHEDULES_KEYS, keys));
        }else{
            keys = new TreeSet<>();
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

    public void deleteSelectedSchedule(String _key, Context _context){
        if(keys.contains(_key)){
            keys.remove(_key);
            _context.getSharedPreferences(SELECTED_SCHEDULES, Context.MODE_PRIVATE)
                    .edit()
                    .putStringSet(SELECTED_SCHEDULES_KEYS, this.keys)
                    .apply();
        }
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

    public boolean isKeySetInSelected(String _key){
        if(keys.isEmpty()){
            return false;
        }else{
            return keys.contains(_key);
        }
    }

}
