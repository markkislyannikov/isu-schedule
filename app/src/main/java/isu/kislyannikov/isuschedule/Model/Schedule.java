package isu.kislyannikov.isuschedule.Model;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import isu.kislyannikov.isuschedule.Metods.HashMetods;

public class Schedule {
    String hash;
    ArrayList<Lesson> lessons = new ArrayList<>();

    //


    public Schedule(){

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

    public void isChanged() throws IOException, NoSuchAlgorithmException {
        Gson gson = new Gson();
        boolean flag;

//        byte[] byteNEW = Files.readAllBytes(Paths.get("schedule_imei_NEW.json"));
//        byte[] byteHashNEW = MessageDigest.getInstance("SHA-256").digest(byteNEW);
//
//        String strHashNEW = HashMetods.bytesToHexString(byteHashNEW);
//        flag = hash.equals(strHashNEW);
//
//        if(!flag){
//            hash = strHashNEW;
//            Path path1 = Path.of("schedule_imei_NEW.json");
//            Path path2 = Path.of("OLD.json");
//            Files.delete(path2);
//            Files.copy(path1, path2);
//            Files.delete(path1);
//        }
//        return flag;
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
