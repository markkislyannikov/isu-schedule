package isu.kislyannikov.isuschedule.Model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import isu.kislyannikov.isuschedule.Metods.HashMetods;

import static isu.kislyannikov.isuschedule.Metods.HashMetods.generateHash;

public class MySchedule {
    Gson gson = new Gson();

    String key;
    String hashCode;
    ArrayList<Lesson> arrayListLessons;
    SharedPreferences sharedPreferences;

    String MY_SCHEDULE = "MYSCHEDULE";
    String MY_SCHEDULE_KEY = "MY_SCHEDULE_KEY";
    String MY_SCHEDULE_DATA = "MY_SCHEDULE_DATA";
    String MY_SCHEDULE_HASH = "MY_SCHEDULE_HASH";

    //Constructor for get data in shared preferense
    public MySchedule(Context _context){
        sharedPreferences= _context.getSharedPreferences(MY_SCHEDULE, Context.MODE_PRIVATE);
//        sharedPreferences.contains(MY_SCHEDULE_DATA);

            Lesson[] lessons = gson.fromJson(sharedPreferences.getString(MY_SCHEDULE_DATA,""),Lesson[].class);
            arrayListLessons = new ArrayList<>(Arrays.asList(lessons));

            key = sharedPreferences.getString(MY_SCHEDULE_KEY,"");
            hashCode = sharedPreferences.getString(MY_SCHEDULE_HASH,"");

    }

    //Constructor for set new data to shared preferense
    public MySchedule(Context _context, ArrayList<Lesson> _lessonArrayList, String _key) throws NoSuchAlgorithmException {

        key = _key;
        arrayListLessons = _lessonArrayList;

        String jsonData = gson.toJson(arrayListLessons);
        hashCode = generateHash(jsonData);

        _context.getSharedPreferences(MY_SCHEDULE, Context.MODE_PRIVATE)
                        .edit()
                        .putString(MY_SCHEDULE_DATA, jsonData)
                        .putString(MY_SCHEDULE_HASH,hashCode)
                        .putString(MY_SCHEDULE_KEY, key)
                        .apply();
    }

    //Metod for check change and change schedule
    public void changeSchedule(Context _context, ArrayList<Lesson> _arrayListNewLessons) throws NoSuchAlgorithmException {
        //boolean isChanged;

        String jsonNewLessons = gson.toJson(_arrayListNewLessons);
        if(isChangedSchedule(jsonNewLessons)) {
            //isChanged = true;

            _context.getSharedPreferences(MY_SCHEDULE, Context.MODE_PRIVATE)
                    .edit()
                    .putString(MY_SCHEDULE_DATA, jsonNewLessons)
                    .putString(MY_SCHEDULE_HASH, hashCode)
                    .putString(MY_SCHEDULE_KEY, key)
                    .apply();
        }
        else{
            //isChanged= false;
        }

    }
    //Check changes
    private boolean isChangedSchedule(String _jsonData) throws NoSuchAlgorithmException {
        boolean isChanged;
        String newKey= generateHash(_jsonData);
        if(key.equals(newKey)) {
            isChanged = false;
        }
        else{
            key = newKey;
            isChanged = true;
        }
        return isChanged;
    }
}

//public class Array