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

    public AllSchedule(AllSchedule _allSchedule) {
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

    public void generateMap(ArrayList<Pair> _pairs) {
        for (int i = 0; i < _pairs.size(); i++) {
            Pair pair = _pairs.get(i);
            ArrayList<ArrayList<Pair>> arrayListPair = new ArrayList<>();
            ArrayList<Pair> arrayPair = new ArrayList<>();

            if (stringLessonMap.containsKey(pair.teachersName)) {
                Pair teacherPair = pair;

                arrayListPair = stringLessonMap.get(pair.teachersName);
                arrayListPair.get(pair.weekday).add(pair);
                stringLessonMap.put(pair.teachersName, arrayListPair);

            } else {
                arrayListPair = new ArrayList<>();
                for (int k = 0; k < 6; k++) {
                    arrayListPair.add(new ArrayList<Pair>());
                }
                arrayListPair.get(pair.weekday).add(pair);
                stringLessonMap.put(pair.teachersName, arrayListPair);
            }
        }

//        String [] times = {"08.30","10.10","12.10","14.10","15.50","17.30","19.10"};
//        ArrayList<String> set = this.getKeys();
//        for (int i = 0; i < set.size(); i++) {
//            ArrayList<ArrayList<Pair>> alalp = stringLessonMap.get(set.get(i));
//            ArrayList<ArrayList<Pair>> newListListPairs = new ArrayList<>();
//
//            for (int j = 0; j < alalp.size(); j++) {
//                ArrayList<Pair> alp = alalp.get(j);
//                ArrayList<Pair> newList = new ArrayList<>();
//                Pair p;
//                for (int k = 0; k < alp.size(); k++) {
//                    p = alp.get(k);
//
//                    for (int f = k; f < alp.size() - 1; i++) {
//                        Pair p1 = alp.get(f);
//                        if (p.pair_start_time == p1.pair_start_time && p.week_type == p1.week_type) {
//                            p.group_name = String.format("%s %s",p.group_name, p1.group_name);
//
//                        }
//
//                    }
//
//                }
//
//
//            }
//
//
//        }


        for (int i = 0; i < _pairs.size(); i++) {
            Pair pair = _pairs.get(i);

            if (this.stringLessonMap.containsKey(pair.group_name)) {
                ArrayList<ArrayList<Pair>> arrayListPair;
                ArrayList<Pair> arrayPair;

                arrayListPair = this.stringLessonMap.get(pair.group_name);
                arrayPair = arrayListPair.get(pair.weekday);
                arrayPair.add(pair);
                stringLessonMap.put(pair.group_name, arrayListPair);

            } else {
                ArrayList<ArrayList<Pair>> arrayListPair;
                ArrayList<Pair> arrayPair = new ArrayList<>();

                arrayListPair = new ArrayList<>();
                for (int k = 0; k < 6; k++) {
                    arrayListPair.add(new ArrayList<Pair>());
                }
                arrayListPair.get(pair.weekday).add(pair);
                stringLessonMap.put(pair.group_name, arrayListPair);
            }
        }
    }


    public static int dayOfWeekSchedule() {
        Calendar c = Calendar.getInstance();
        java.util.Date date = new java.util.Date();
        c.setTime(date);
        int dayOfWeek = (c.get(Calendar.DAY_OF_WEEK) + 5) % 7;

        return dayOfWeek == 6 ? 0 : dayOfWeek;
    }

    public ArrayList<ArrayList<Pair>> getPairsByKey(String key) {
        return stringLessonMap.get(key);
    }

    public ArrayList<String> getKeys() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        Set<String> stringSet = stringLessonMap.keySet();
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
