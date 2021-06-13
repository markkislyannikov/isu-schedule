package isu.kislyannikov.isuschedule.Model;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import isu.kislyannikov.isuschedule.Metods.HashMetods;

public class Schedule {
    String hash;
    ArrayList<Lesson> lessons = new ArrayList<>();
    Map<String, Set<Lesson>> stringSetLessonMap;

    public Schedule(){

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public Schedule(Schedule _lessons){
        lessons = _lessons.lessons;
    }

    public Schedule(ArrayList<Lesson> _lessons){
        this.lessons = _lessons;
    }

    public void setHash(String _hash) {
        this.hash = _hash;
    }

    public void downloadSchedule(){


    }

    private void createHashMap(){
        int lengthLessons = lessons.size();
        for(int i=0; i<lengthLessons; i++){

        }
    }

    public boolean isChangedAllSchedule(Schedule _schedule){
        return false;
    }

    public boolean isChangedTheSchedule(Schedule _schedule){
        return false;
    }





    @Override
    public String toString() {
        String output = "";
        for(Lesson lesson: lessons){
            output+=lesson.toString();
        }
        return output;
    }
}
