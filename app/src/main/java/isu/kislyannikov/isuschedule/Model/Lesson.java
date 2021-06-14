package isu.kislyannikov.isuschedule.Model;

public class Lesson {
    public String group_name;
    public String weekday;
    public String pair_start_time;
    public String pair_end_time;
    public String subject_name;
    public String pair_type;
    public String lastname;
    public String firstname;
    public String patronymic;
    public String class_name;
    public String week_type;
    public String begin_date_pairs;
    public String end_date_pairs;

    public Lesson(){

    }

    public Lesson(String _group_name,
                  String _weekday,
                  String _pair_start_time,
                  String _pair_end_time,
                  String _subject_name,
                  String _pair_type,
                  String _lastname,
                  String _firstname,
                  String _patronymic,
                  String _class_name,
                  String _week_type,
                  String _begin_date_pairs,
                  String _end_date_pairs){
        group_name = _group_name;
        weekday = _weekday;
        pair_start_time = _pair_start_time;
        pair_end_time = _pair_end_time;
        subject_name = _subject_name;
        pair_type = _pair_type;
        lastname = _lastname;
        firstname = _firstname;
        patronymic = _patronymic;
        class_name = _class_name;
        week_type = _week_type;
        begin_date_pairs = _begin_date_pairs;
        end_date_pairs = _end_date_pairs;
    }

    @Override
    public String toString() {
        String output = String.format("group_name: %s,\n" +
                        "    weekday: %s,\n" +
                        "    pair_start_time: %s,\n" +
                        "    pair_end_time: %s,\n" +
                        "    subject_name: %s,\n" +
                        "    pair_type: %s,\n" +
                        "    lastname: %s,\n" +
                        "    firstname: %s,\n" +
                        "    patronymic: %s,\n" +
                        "    class_name: %s,\n" +
                        "    week_type: %s,\n" +
                        "    begin_date_pairs: %s,\n" +
                        "    end_date_pairs: %s\n\n",
                group_name,
                weekday,
                pair_start_time,
                pair_end_time,
                subject_name,
                pair_type,
                lastname,
                firstname,
                patronymic,
                class_name,
                week_type,
                begin_date_pairs,
                end_date_pairs);
        return output;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getPair_start_time() {
        return pair_start_time;
    }

    public void setPair_start_time(String pair_start_time) {
        this.pair_start_time = pair_start_time;
    }

    public String getPair_end_time() {
        return pair_end_time;
    }

    public void setPair_end_time(String pair_end_time) {
        this.pair_end_time = pair_end_time;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getPair_type() {
        return pair_type;
    }

    public void setPair_type(String pair_type) {
        this.pair_type = pair_type;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getWeek_type() {
        return week_type;
    }

    public void setWeek_type(String week_type) {
        this.week_type = week_type;
    }

    public String getBegin_date_pairs() {
        return begin_date_pairs;
    }

    public void setBegin_date_pairs(String begin_date_pairs) {
        this.begin_date_pairs = begin_date_pairs;
    }

    public String getEnd_date_pairs() {
        return end_date_pairs;
    }

    public void setEnd_date_pairs(String end_date_pairs) {
        this.end_date_pairs = end_date_pairs;
    }
}


/*{group_name: %s,
    weekday: %s,
    pair_start_time: %s,
    pair_end_time: %s,
    subject_name: %s,
    pair_type: %s,
    lastname: %s,
    firstname: %s,
    patronymic: %s,
    class_name: %s,
    week_type: %s,
    begin_date_pairs: %s,
    end_date_pairs: %s}

        group_name,
        weekday,
        pair_start_time,
        pair_end_time,
        subject_name,
        pair_type,
        lastname,
        firstname,
        patronymic,
        class_name,
        week_type,
        begin_date_pairs,
        end_date_pairs,

        */