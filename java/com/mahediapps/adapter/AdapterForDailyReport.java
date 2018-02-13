package com.mahediapps.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import com.mahediapps.personalreport.R;
import com.mahediapps.model.DatabaseHelper;

public class AdapterForDailyReport extends BaseAdapter {

    private ArrayList<String> allDayIdList;
    private Context context;
    private DatabaseHelper dbHelper;
    private HashMap<String, String> myMap;

    public AdapterForDailyReport(Context context, ArrayList<String> allDayIdList) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
        this.allDayIdList = allDayIdList;
    }


    public int getCount() {
        return allDayIdList.size();
    }


    public Object getItem(int i) {
        return null;
    }


    public long getItemId(int i) {
        return 0L;
    }


    private int getTotalPage(String rel, String other){
        double dIslamicPage, dOtherPage;
        dIslamicPage= 0.0D;
        dOtherPage  = dIslamicPage;

        if (rel != null) {
            if (!(rel.equals(""))) {
                dIslamicPage= Double.parseDouble(rel);
            }
        }

        if (other != null) {
            if (!(other.equals(""))) {
                dOtherPage = Double.parseDouble(other);
            }
        }
        return (int) (dIslamicPage + dOtherPage);
    }


    public View getView(int i, View convertView, ViewGroup viewgroup) {

        myMap = dbHelper.getDailyReportFromDatabase(allDayIdList.get(i));
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            row = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.li_daily_report, null);
            
            holder = new ViewHolder(row);
            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        holder.tvDateDR.setText((i + 1) + "");
        holder.tvQuranDR.setText(myMap.get("dr_study_quran"));
        holder.tvHadithDR.setText(myMap.get("dr_study_hadith"));
        
        int sum = getTotalPage(myMap.get("dr_study_literature_is"), myMap.get("dr_study_literature_other"));
        if (sum == 0) {
            holder.tvLiteratureDR.setText("");
        } else {
            holder.tvLiteratureDR.setText(String.valueOf(sum));
        }
        holder.tvAcademicDR.setText(myMap.get("dr_study_academic"));

        holder.tvJamaatDR.setText(myMap.get("dr_namaz_jamaat"));
        holder.tvKajaDR.setText(myMap.get("dr_namaz_kajja"));

        holder.tvCallDR.setText(myMap.get("dr_duty_call"));
        holder.tvOtherDR.setText(myMap.get("dr_duty_other"));
        
        holder.tvMemberDR.setText(myMap.get("dr_contact_member"));
        holder.tvAssociateDR.setText(myMap.get("dr_contact_associate"));
        holder.tvWorkerDR.setText(myMap.get("dr_contact_worker"));
        holder.tvSupporterDR.setText(myMap.get("dr_contact_supporter"));
        holder.tvFriendDR.setText(myMap.get("dr_contact_friend"));
        holder.tvTalentStudentDR.setText(myMap.get("dr_contact_talent_student"));
        holder.tvWellWisherDR.setText(myMap.get("dr_contact_wellwisher"));
  

        if (myMap.get("dr_class") != null) {
            if (Integer.parseInt(myMap.get("dr_class")) == 1) {
                holder.cbClassDR.setChecked(true);
            } else {
                holder.cbClassDR.setChecked(false);
            }
        } else {
            holder.cbClassDR.setChecked(false);
        }

        if (myMap.get("dr_newspaper") != null) {
            if (Integer.parseInt(myMap.get("dr_newspaper")) == 1) {
                holder.cbNewspaperDR.setChecked(true);
            } else {
                holder.cbNewspaperDR.setChecked(false);
            }
        } else {
            holder.cbNewspaperDR.setChecked(false);
        }


        if (myMap.get("dr_physical_exercise") != null) {
            if (Integer.parseInt(myMap.get("dr_physical_exercise")) == 1) {
                holder.cbPhysicalExerciseDR.setChecked(true);
            } else {
                holder.cbPhysicalExerciseDR.setChecked(false);
            }
        } else {
            holder.cbPhysicalExerciseDR.setChecked(false);
        }


        if (myMap.get("dr_criticism") != null) {
            if (Integer.parseInt(myMap.get("dr_criticism")) == 1) {
                holder.cbCriticismDR.setChecked(true);
            } else {
                holder.cbCriticismDR.setChecked(false);
            }
        } else {
            holder.cbCriticismDR.setChecked(false);
        }
        return row;
    }


    private class ViewHolder {

        public CheckBox cbClassDR, cbNewspaperDR, cbPhysicalExerciseDR, cbCriticismDR;
        public TextView tvDateDR, tvQuranDR, tvHadithDR, tvLiteratureDR, tvAcademicDR,
                tvJamaatDR, tvKajaDR, tvCallDR, tvOtherDR,
                tvMemberDR, tvAssociateDR, tvWorkerDR, tvSupporterDR, tvFriendDR,
                tvTalentStudentDR, tvWellWisherDR;
          


        public ViewHolder(View view) {
            tvDateDR = (TextView) view.findViewById(R.id.tvDateDR);
            tvQuranDR = (TextView) view.findViewById(R.id.tvQuranDR);
            tvHadithDR = (TextView) view.findViewById(R.id.tvHadithDR);
            tvLiteratureDR = (TextView) view.findViewById(R.id.tvLiteratureDR);
            tvAcademicDR = (TextView) view.findViewById(R.id.tvAcademicDR);

            cbClassDR = (CheckBox) view.findViewById(R.id.cbclassDR);
            cbNewspaperDR = (CheckBox) view.findViewById(R.id.cbNewspaperDR);
            cbPhysicalExerciseDR = (CheckBox) view.findViewById(R.id.cbPhysicalExerciseDR);
            cbCriticismDR = (CheckBox) view.findViewById(R.id.cbCriticismDR);

            tvJamaatDR = (TextView) view.findViewById(R.id.tvJamaatDR);
            tvKajaDR = (TextView) view.findViewById(R.id.tvKajjaDR);

            tvCallDR = (TextView) view.findViewById(R.id.tvCallDR);
            tvOtherDR = (TextView) view.findViewById(R.id.tvOtherDR);

     
            tvMemberDR = (TextView) view.findViewById(R.id.tvMemberDR);
            tvAssociateDR = (TextView) view.findViewById(R.id.tvAssociateDR);
            tvWorkerDR = (TextView) view.findViewById(R.id.tvWorkerDR);
            tvSupporterDR = (TextView) view.findViewById(R.id.tvSupporterDR);
            tvFriendDR = (TextView) view.findViewById(R.id.tvFriendDR);
            tvTalentStudentDR = (TextView) view.findViewById(R.id.tvTalentStudentDR);
            tvWellWisherDR = (TextView) view.findViewById(R.id.tvWellWisherDR);
        }
    }

}

