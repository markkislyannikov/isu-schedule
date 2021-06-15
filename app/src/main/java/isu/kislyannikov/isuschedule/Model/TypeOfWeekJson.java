package isu.kislyannikov.isuschedule.Model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TypeOfWeekJson {
    String semester;
    String begin_date;
    String end_date;
    String begin_week;

    public TypeOfWeekJson() {

    }
    public TypeOfWeekJson(String semester, String begin_date, String end_date, String begin_week) {
        this.semester = semester;
        this.begin_date = begin_date;
        this.end_date = end_date;
        this.begin_week = begin_week;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getBegin_date() {
        return begin_date;
    }

    public void setBegin_date(String begin_date) {
        this.begin_date = begin_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getBegin_week() {
        return begin_week;
    }

    public void setBegin_week(String begin_week) {
        this.begin_week = begin_week;
    }

    public int typeOfWeek(){
        Date now = new Date();
        Date first = new Date();
        int nowTypeOfDate;
        int firstTypeOfDate;

        int typeOfStart = this.begin_week.equals("верхняя")? 0 : 1;

        GregorianCalendar calendar = new GregorianCalendar();
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        try {
            first = date.parse(this.begin_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTimeInMillis(first.getTime());
        firstTypeOfDate = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setTimeInMillis(now.getTime());
        nowTypeOfDate = calendar.get(Calendar.WEEK_OF_YEAR);

        if(firstTypeOfDate%2==nowTypeOfDate%2){
            return typeOfStart;
        }
        else{
            return (typeOfStart+1)%2;
        }
    }



}
