package isu.kislyannikov.isuschedule.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import java.util.ArrayList;
import isu.kislyannikov.isuschedule.Model.Pair;
import isu.kislyannikov.isuschedule.R;

public class ScheduleAdapter extends BaseAdapter implements Filterable {
    private Context context;
    ArrayList<Pair>  arrayListPair;
    ArrayList<Pair> pairArrayList;
    private LayoutInflater inflater;
    private int typeOfWeek;

    ArrayList<Pair> mValue;
    ValueFilter valueFilter;

    public ScheduleAdapter(Context _context, ArrayList<Pair> _arrayListPair, int day, int typeOfWeek){
        this.context = _context;
        this.arrayListPair = _arrayListPair;
        mValue = new ArrayList<>();
        for(int i=0; i<arrayListPair.size(); i++){
            if(arrayListPair.get(i).weekday==day && (arrayListPair.get(i).week_type==typeOfWeek || arrayListPair.get(i).week_type==2)){
                mValue.add(arrayListPair.get(i));
            }
        }
        System.out.println(mValue);
    }

    @Override
    public int getCount() {
        return mValue.size();
    }

    @Override
    public Pair getItem(int position) {
        return mValue.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null){
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView==null){
            convertView = inflater.inflate(R.layout.lesson_listview,null);
        }

        Pair _pair = mValue.get(position);

        TextView textViewStartTime = convertView.findViewById(R.id.firstInformFirstTime);
        TextView textViewEndTime = convertView.findViewById(R.id.firstInformSecondTime);
        TextView textViewLocaltion = convertView.findViewById(R.id.firstInformPlace);
        TextView textViewTeacher = convertView.findViewById(R.id.secondInformTeacher);
        TextView textViewSubject = convertView.findViewById(R.id.secondInformLesson);

        String txtStartTime = _pair.pair_start_time;
        String txtEndTime = _pair.pair_end_time;
        String txtLocaltion = _pair.class_name;
        String txtTeacher = _pair.teachersName;
        String txtSubject = _pair.subject_name;

        textViewStartTime.setText(txtStartTime);
        textViewEndTime.setText(txtEndTime);
        textViewLocaltion.setText(txtLocaltion);
        textViewTeacher.setText(txtTeacher);
        textViewSubject.setText(txtSubject);

//        textViewStartTime.setText("8:30");
//        textViewEndTime.setText("10:30");
//        textViewLocaltion.setText("320");
//        textViewTeacher.setText("Зубков Олег Владимирович");
//        textViewSubject.setText("Олимпиадное программирование");
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if(valueFilter==null){
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();

            if(charSequence !=null && charSequence.length()>0){
                int elem = Integer.parseInt(charSequence.toString());
                ArrayList<Pair> filterList = new ArrayList<>();
                for(int i=0; i<arrayListPair.size(); i++){
                    if(arrayListPair.get(i).weekday==elem && (arrayListPair.get(i).week_type==typeOfWeek || arrayListPair.get(i).week_type==2)){
                        filterList.add(arrayListPair.get(i));
                    }
                }

                results.count = filterList.size();
                results.values = filterList;
            }
            else{
                results.count = mValue.size();
                results.values = mValue;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            pairArrayList = (ArrayList<Pair>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
