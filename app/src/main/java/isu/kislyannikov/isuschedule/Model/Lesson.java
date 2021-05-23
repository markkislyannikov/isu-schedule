package isu.kislyannikov.isuschedule.Model;

public class Lesson {
    private String group_name;
    private String weekday;
    private String pair_start_time;
    private String pair_end_time;
    private String subject_name;
    private String pair_type;
    private String lastname;
    private String firstname;
    private String patronymic;
    private String class_name;
    private String week_type;
    private String begin_date_pairs;
    private String end_date_pairs;

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