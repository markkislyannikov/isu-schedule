package isu.kislyannikov.isuschedule.Metods;

import android.os.AsyncTask;
import android.text.PrecomputedText;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import isu.kislyannikov.isuschedule.Model.Lesson;
import isu.kislyannikov.isuschedule.Model.Schedule;

public class DownloadSchedule {
    //http://raspmath.isu.ru/getSchedule GET
    public DownloadSchedule(){
    }



    public void getContent(String apiUrl) throws IOException {
        Gson gson = new Gson();

//        JsonReader jsonReader = new JsonReader();
//        Lesson[] lessons = gson.fromJson(jsonReader, Lesson[].class);
//        Schedule schedule = new Schedule(new ArrayList<>(Arrays.asList(lessons)));

        BufferedReader reader=null;
        InputStream stream = null;
        HttpURLConnection connection = null;
        try {
            URL url=new URL(apiUrl);
            connection =(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            stream = connection.getInputStream();

            JsonReader jsonReader = new JsonReader( new InputStreamReader(stream));
            Lesson[] lessons = gson.fromJson(jsonReader, Lesson[].class);
            Schedule schedule = new Schedule(new ArrayList<>(Arrays.asList(lessons)));
            System.out.println(schedule);

            jsonReader.close();

            reader= new BufferedReader(new InputStreamReader(stream));
            StringBuilder buf=new StringBuilder();
            String line;
            System.out.println("\n\n");
            while ((line=reader.readLine()) != null) {
                buf.append(line).append("\n");
            }
            //return(buf.toString());
        }
        finally {
            if (reader != null) {
                reader.close();
            }
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
