package com.mahediapps.personalreport;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import com.mahediapps.adapter.AdapterForDailyReport;

public class DailyReport extends Activity {

    private ArrayList<String> allDayIdList;
    private ImageView ibNextMonth, ibPreviousMonth;
    private ListView lvDailyReport;
    private TextView tvTitleDR;
    private Calendar currentMonth, month;
    private String monthId = "", dayId = "";


    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_daily_report);

        currentMonth = Calendar.getInstance();
        month = Calendar.getInstance();

        tvTitleDR = (TextView) findViewById(R.id.tvTitleDR);
        lvDailyReport = (ListView) findViewById(R.id.lvDailyReportDR);
        refreshCalendar();

        ibNextMonth = (ImageView) findViewById(R.id.ibNextDR);
        ibPreviousMonth = (ImageView) findViewById(R.id.ibPreviousDR);

        ibNextMonth.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (setNextMonth()) {
                    refreshCalendar();
                }
            }
        });


        ibPreviousMonth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (setPreviousMonth()) {
                    refreshCalendar();
                }
            }
        });
    }


    public ArrayList getAllDataForDailyReport() {
        allDayIdList = new ArrayList<>();
        int i = 1;

        do {
            if (i > month.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                return allDayIdList;
            }

            month.set(Calendar.DAY_OF_MONTH, i);
            dayId = DateFormat.format("EE, dd MMMM yyyy", month).toString();
            allDayIdList.add(dayId);

            if (month.get(Calendar.YEAR) == currentMonth.get(Calendar.YEAR) &&
                    month.get(Calendar.MONTH) == currentMonth.get(Calendar.MONTH) &&
                    month.get(Calendar.DAY_OF_MONTH) == currentMonth.get(Calendar.DAY_OF_MONTH)) {

                return allDayIdList;
            }
            i++;
        } while (true);
    }


    public boolean setNextMonth() {
        if (month.get(Calendar.MONTH) == currentMonth.get(Calendar.MONTH) &&
                month.get(Calendar.YEAR) == currentMonth.get(Calendar.YEAR)) {

            Toast.makeText(this, "You can't see next month report !", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (month.get(Calendar.MONTH) == month.getActualMaximum(Calendar.MONTH)) {
            month.set(month.get(Calendar.YEAR) + 1, month.getActualMinimum(Calendar.MONTH), 1);

        } else {
            month.add(Calendar.MONTH, 1);
        }
        return true;
    }


    public boolean setPreviousMonth() {
        if (month.get(Calendar.MONTH) == month.getActualMinimum(Calendar.MONTH)) {
            month.set(month.get(Calendar.YEAR) - 1, month.getActualMaximum(Calendar.MONTH), 31);
            return true;

        } else {
            month.add(Calendar.MONTH, -1);
            return true;
        }
    }


    private void  refreshCalendar(){
        allDayIdList = getAllDataForDailyReport();
        monthId = DateFormat.format("MMMM yyyy", month).toString();
        tvTitleDR.setText((new StringBuilder("Daily Report - ")).append(monthId).toString());
        lvDailyReport.setAdapter(new AdapterForDailyReport(this, allDayIdList));
    }
}
