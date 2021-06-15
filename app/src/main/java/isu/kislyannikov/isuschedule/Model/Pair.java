package isu.kislyannikov.isuschedule.Model;

import java.io.Serializable;

import isu.kislyannikov.isuschedule.R;

public class Pair implements Serializable {
    public String group_name;
    public int    weekday;
    public String pair_start_time;
    public String pair_end_time;
    public String subject_name;
//    private String pair_type;
    public String teachersName;
    public String class_name;
    public String week_type;


    public Pair(String _group_name,
                String _weekday,
                String _pair_start_time,
                String _pair_end_time,
                String _subject_name,
                String _pair_type,
                String _lastname,
                String _firstname,
                String _patronymic,
                String _class_name,
                String _week_type
    ) {
        this.group_name = _group_name.substring(1);
        switch(_weekday){
            case "Понедельник":
                this.weekday=0;
                break;
            case "Вторник":
                this.weekday=1;
                break;
            case "Среда":
                this.weekday=2;
                break;
            case "Четверг":
                this.weekday=3;
                break;
            case "Пятница":
                this.weekday=4;
                break;
            case "Суббота":
                this.weekday=5;
                break;
        }
        if(_pair_start_time.length()==4){
            this.pair_start_time = String.format("0%s",_pair_start_time);
        }
        else{
            this.pair_start_time = _pair_start_time;
        }
        this.pair_end_time = _pair_end_time;
        this.subject_name = String.format("%s(%s)",_subject_name,_pair_type.substring(0,3));

//        this.pair_type = _pair_type.substring(0,3);
        this.teachersName = String.format("%s %s %s",_lastname, _firstname, _patronymic);
        this.class_name = _class_name;
        this.week_type = _week_type;
    }

    public Pair(Lesson lesson){
        this.group_name = lesson.getGroup_name().substring(1);
        switch(lesson.weekday){
            case "Понедельник":
                this.weekday=0;
                break;
            case "Вторник":
                this.weekday=1;
                break;
            case "Среда":
                this.weekday=2;
                break;
            case "Четверг":
                this.weekday=3;
                break;
            case "Пятница":
                this.weekday=4;
                break;
            case "Суббота":
                this.weekday=5;
                break;
        }
        if(lesson.pair_start_time.length()==4){
            this.pair_start_time = String.format("0%s",lesson.pair_start_time);
        }
        else{
            this.pair_start_time = lesson.pair_start_time;
        }
        this.pair_end_time = lesson.pair_end_time;
        this.subject_name = String.format("%s(%s)",lesson.subject_name,lesson.pair_type.substring(0,3));

//        this.pair_type = lesson.pair_type.substring(0,3);
        this.teachersName = String.format("%s %s %s",lesson.lastname, lesson.firstname, lesson.patronymic);
        this.class_name = lesson.class_name;
        this.week_type = lesson.week_type;
    }


    @Override
    public String toString() {
        return "Pair{" +
                "group_name='" + group_name + '\'' +
                ", weekday=" + weekday +
                ", pair_start_time='" + pair_start_time + '\'' +
                ", pair_end_time='" + pair_end_time + '\'' +
                ", subject_name='" + subject_name + '\'' +
                ", teachersName='" + teachersName + '\'' +
                ", class_name='" + class_name + '\'' +
                ", week_type='" + week_type + '\'' +
                '}';
    }
}
