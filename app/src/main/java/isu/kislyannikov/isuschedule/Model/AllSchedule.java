package isu.kislyannikov.isuschedule.Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AllSchedule {
    ArrayList<Pair> pairs;
    HashMap<String, ArrayList<ArrayList<Pair>>> stringLessonMap;

    public AllSchedule(AllSchedule _allSchedule){
        this.pairs = _allSchedule.pairs;
        this.stringLessonMap = _allSchedule.stringLessonMap;
    }

    public AllSchedule(Lesson[] lessons) {
        stringLessonMap = new HashMap<>();
        pairs = new ArrayList<>();
        for (int i = 0; i < lessons.length; i++) {
            pairs.add(new Pair(lessons[i]));
        }
        generateMap(pairs);
    }

    private void generateMap(ArrayList<Pair> _pairs) {
        for (int i = 0; i < _pairs.size(); i++) {
            Pair pair = _pairs.get(i);
            ArrayList<ArrayList<Pair>> arrayListPair = new ArrayList<>();
            ArrayList<Pair> arrayPair = new ArrayList<>();

            if (stringLessonMap.containsKey(pair.group_name)) {
                arrayListPair = stringLessonMap.get(pair.group_name);
                arrayListPair.get(pair.weekday).add(pair);
                stringLessonMap.put(pair.group_name, arrayListPair);

            } else {
                arrayListPair = new ArrayList<>();
                for(int k=0; k<6; k++){
                    arrayListPair.add(new ArrayList<Pair>());
                }
                arrayListPair.get(pair.weekday).add(pair);
                stringLessonMap.put(pair.group_name, arrayListPair);
            }

            if (stringLessonMap.containsKey(pair.teachersName)) {
                arrayListPair = stringLessonMap.get(pair.teachersName);
                arrayListPair.get(pair.weekday).add(pair);
                stringLessonMap.put(pair.teachersName, arrayListPair);

            } else {
                arrayListPair = new ArrayList<>();
                for(int k=0; k<6; k++){
                    arrayListPair.add(new ArrayList<Pair>());
                }
                arrayListPair.get(pair.weekday).add(pair);
                stringLessonMap.put(pair.teachersName, arrayListPair);
            }
        }
    }



    public static int dayOfWeekSchedule(){
        Calendar c = Calendar.getInstance();
        java.util.Date date = new java.util.Date();
        c.setTime(date);
        int dayOfWeek = (c.get(Calendar.DAY_OF_WEEK) + 5) % 7;

        return dayOfWeek==6? 0: dayOfWeek;
    }

    public ArrayList<ArrayList<Pair>> getPairsByKey(String key){
        return stringLessonMap.get(key);
    }

    public ArrayList<String> getKeys(){
        ArrayList<String> stringArrayList = new ArrayList<>();
        Set<String> stringSet =  stringLessonMap.keySet();
        for (String str : stringSet)
            stringArrayList.add(str);
        return stringArrayList;
    }


    @Override
    public String toString() {
        String output = "";
        for (Pair pair : this.pairs) {
            output += pair.toString();
        }
        return output;
    }


}
