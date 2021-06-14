package isu.kislyannikov.isuschedule.Metods;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import isu.kislyannikov.isuschedule.Model.Lesson;
import isu.kislyannikov.isuschedule.Model.AllSchedule;

public class DownloadSchedule {
    private final String API = "http://raspmath.isu.ru/getSchedule";

    public void getNewContent() throws IOException {
        Gson gson = new Gson();

//        BufferedReader reader=null;
        InputStream stream = null;
        HttpURLConnection connection = null;
        try {
            URL url=new URL(API);
            connection =(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            stream = connection.getInputStream();

            JsonReader jsonReader = new JsonReader( new InputStreamReader(stream));
            Lesson[] lessons = gson.fromJson(jsonReader, Lesson[].class);
            //AllSchedule schedule = new AllSchedule(new ArrayList<>(Arrays.asList(lessons)));


            //System.out.println(schedule);
            jsonReader.close();

//            reader= new BufferedReader(new InputStreamReader(stream));
//            StringBuilder buf=new StringBuilder();
//            String line;
//            System.out.println("\n\n");
//            while ((line=reader.readLine()) != null) {
//                buf.append(line).append("\n");
//            }
            //return(buf.toString());
        }
        finally {
//            if (reader != null) {
//                reader.close();
//            }
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
//        JsonReader jsonReader = new JsonReader();
//        Lesson[] lessons = gson.fromJson(jsonReader, Lesson[].class);
//        Schedule schedule = new Schedule(new ArrayList<>(Arrays.asList(lessons)));