package isu.kislyannikov.isuschedule.Model;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import static isu.kislyannikov.isuschedule.Metods.HashMetods.generateHash;

public class MySchedule {
    Gson gson = new Gson();

    String key;
    String hashCode;
    ArrayList<ArrayList<Pair>> arrayListPair = new ArrayList<>();
    ArrayList<Pair> arrayPair = new ArrayList<>();

    SharedPreferences sharedPreferences;

    String MY_SCHEDULE = "MYSCHEDULE";
    String MY_SCHEDULE_KEY = "MY_SCHEDULE_KEY";
    String MY_SCHEDULE_DATA = "MY_SCHEDULE_DATA";
    String MY_SCHEDULE_HASH = "MY_SCHEDULE_HASH";
    String MY_SCHEDULE_TYPEOFDATE = "MY_SCHEDULE_TYPEOFDATE";


    //Constructor for get data in shared preferense
    public MySchedule(Context _context) {
        sharedPreferences = _context.getSharedPreferences(MY_SCHEDULE, Context.MODE_PRIVATE);
        Pair[][] pairs = gson.fromJson(sharedPreferences.getString(MY_SCHEDULE_DATA, ""), Pair[][].class);
        ArrayList<Pair> arrayList = new ArrayList<>();
        for(int i=0; i<pairs.length; i++){
            for(int j=0; j<pairs[i].length; j++){
                arrayList.add(pairs[i][j]);
            }
            arrayListPair.add(arrayList);
        }

        key = sharedPreferences.getString(MY_SCHEDULE_KEY, "");
        hashCode = sharedPreferences.getString(MY_SCHEDULE_HASH, "");
    }


    //Constructor for set new data to shared preferense
    public MySchedule(Context _context, ArrayList<ArrayList<Pair>> _pairArrayList, String _key) throws NoSuchAlgorithmException {
        key = _key;
        arrayListPair = _pairArrayList;

        String jsonData = gson.toJson(arrayListPair);
        System.out.println(jsonData);
        hashCode = generateHash(jsonData);

        _context.getSharedPreferences(MY_SCHEDULE, Context.MODE_PRIVATE)
                .edit()
                .putString(MY_SCHEDULE_DATA, jsonData)
                .putString(MY_SCHEDULE_HASH, hashCode)
                .putString(MY_SCHEDULE_KEY, key)
                .apply();
    }

    //Metod for check change and change schedule
    public boolean changeSchedule(Context _context, ArrayList<Pair> _pairArrayList) throws NoSuchAlgorithmException {

        for(Pair p:_pairArrayList){
            System.out.println(p);
        }

        System.out.println("\n\n\n\n\n");
        for(ArrayList<Pair> ap:arrayListPair) {
            System.out.println("\n");
            for (Pair p : ap) {
                System.out.println(p);
            }
        }


        String jsonNewLessons = gson.toJson(_pairArrayList);
        if (isChangedSchedule(jsonNewLessons)) {

            arrayPair = _pairArrayList;

            _context.getSharedPreferences(MY_SCHEDULE, Context.MODE_PRIVATE)
                    .edit()
                    .putString(MY_SCHEDULE_DATA, jsonNewLessons)
                    .putString(MY_SCHEDULE_HASH, hashCode)
                    .putString(MY_SCHEDULE_KEY, key)
                    .apply();
            return true;
        }
        else{
            return false;
        }
    }

    public ArrayList<ArrayList<Pair>> getMySchedule(){
        return this.arrayListPair;
    }

    public String getKey(){
        return key;
    }


    //Check changes
    private boolean isChangedSchedule(String _jsonData) throws NoSuchAlgorithmException {
        boolean isChanged;
        String newKey = generateHash(_jsonData);
        if (key.equals(newKey)) {
            isChanged = false;
        } else {
            key = newKey;
            isChanged = true;
        }
        return isChanged;
    }
}

//public class Array