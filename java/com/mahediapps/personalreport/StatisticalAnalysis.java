package com.mahediapps.personalreport;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.mahediapps.model.AllData;
import com.mahediapps.model.DatabaseHelper;
import com.mahediapps.model.MonthlyReportCalculation;

public class StatisticalAnalysis extends ActionBarActivity {

    private TextView tvPlanTotalDayAcademicSA, tvPlanAverageHourAcademicSA, tvPlanTotalDayQuranSA,
            tvPlanAverageAyatQuranSA, tvPlanTotalDayHadithSA, tvPlanAverageHadisHadithSA,
            tvPlanTotalPageLiteratureSA, tvPlanIslamicPageLiteratureSA, tvPlanOtherPageLiteratureSA,
            tvPlanPresentClassSA, tvPlanTotalJamaatNamazSA, tvPlanTotalDayCallOrgDutySA,
            tvPlanTotalHourCallOrgDutySA, tvPlanTotalDayOtherOrgDutySA, tvPlanAverageHourOtherOrgDutySA,
            tvPlanCriticismMiscSA, tvPlanPhysicalExerciseMiscSA, tvPlanNewspaperMiscSA;


    private TextView tvReportTotalDayAcademicSA, tvReportAverageHourAcademicSA, tvReportTotalDayQuranSA,
            tvReportAverageAyatQuranSA, tvReportTotalDayHadithSA, tvReportAverageHadisHadithSA,
            tvReportTotalPageLiteratureSA, tvReportIslamicPageLiteratureSA, tvReportOtherPageLiteratureSA,
            tvReportPresentClassSA, tvReportTotalJamaatNamazSA, tvReporTotalDayCallOrgDutySA,
            tvReportTotalHourCallOrgDutySA, tvReportTotalDayOtherOrgDutySA, tvReportAverageHourOtherOgrDutySA,
            tvReportCriticismMiscSA, tvReportPhysicalExerciseMiscSA, tvReportNewspaperMiscSA;

    private TextView tvPercentageTotalDayAcademicSA, tvPercentageAverageHourAcademicSA, tvPercentageTotalDayQuranSA,
            tvPercentageAverageAyatQuranSA, tvPercentageTotalDayHadithSA, tvPercentageAverageHadisHadithSA,
            tvPercentageTotalPageLiteratureSA, tvPercentageIslamicPageLiteratureSA, tvPercentageOtherPageLiteratureSA,
            tvPercentagePresentClassSA, tvPercentageTotalJamaatNamazSA, tvPercentageTotalDayCallOrgDutySA,
            tvPercentageTotalHourCallOrgDutySA, tvPercentageTotalDayOtherOrgDutySA, tvPercentageAverageHourOtherOrgDutySA,
            tvPercentageCriticismMiscSA, tvPercentagePhysicalExerciseMiscSA, tvPercentageNewspaperMiscSA;

    private Toolbar mToolbar;
    private ImageButton ibNext, ibPrevious;
    private Calendar month, currentMonth;
    private String monthId = "";
    private HashMap<String, String> monthlyPlan;
    private DatabaseHelper dbHelper;
    private MonthlyReportCalculation calculation;
    private ArrayList<String> planValueList, reportValueList;
    private TextView tvTitle;


    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_statistical_analysis);
        dbHelper = new DatabaseHelper(this);
        month = Calendar.getInstance();
        currentMonth = Calendar.getInstance();

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Statistical Analysis");


        initialize();
        refreshCalendar();
        ibPrevious = (ImageButton) findViewById(R.id.ibPreviousSA);
        ibNext = (ImageButton) findViewById(R.id.ibNextSA);

        ibPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setPreviousMonth();
                refreshCalendar();
            }
        });


        ibNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (setNextMonth()) refreshCalendar();
            }
        });

    }


    public String getPercentage(String plan, String dairy) {
        double dReport = 0.0D;
        double dPlan = 0.0D;

        if (plan != null && !plan.equals("")) {
            dPlan = Double.valueOf(plan);
        } else dPlan = 0.0D;


        if (dairy != null && !dairy.equals("")) {
            dReport = Double.valueOf(dairy);
        } else dReport = 0.0D;

        if (dPlan <= 0.0D) {
            return "--";
        }

        if (dReport == 0.0D) {
            return "0%";
        }
        if (dReport >= dPlan) {
            return (new StringBuilder(String.valueOf((100D * dReport) / dPlan).substring(0, 5))).append("%").toString();

        } else {
            if (((100D * dReport) / dPlan) >= 10.0D) {
                return (new StringBuilder(String.valueOf((100D * dReport) / dPlan).substring(0, 4))).append("%").toString();

            } else {
                return (new StringBuilder(String.valueOf((100D * dReport) / dPlan).substring(0, 3))).append("%").toString();
            }
        }
    }



    private void initialize() {
        //plan 18
        tvPlanTotalDayQuranSA = ((TextView) findViewById(R.id.tvPlanTotalDayQuranSA));
        tvPlanAverageAyatQuranSA = ((TextView) findViewById(R.id.tvPlanAverageAyatQuranSA));

        tvPlanTotalDayHadithSA = ((TextView) findViewById(R.id.tvPlanTotalDayHadithSA));
        tvPlanAverageHadisHadithSA = ((TextView) findViewById(R.id.tvPlanAverageHadisHadithSA));

        tvPlanTotalPageLiteratureSA = ((TextView) findViewById(R.id.tvPlanTotalPageLiteratureSA));
        tvPlanIslamicPageLiteratureSA = ((TextView) findViewById(R.id.tvPlanIslamicPageLiteratureSA));
        tvPlanOtherPageLiteratureSA = ((TextView) findViewById(R.id.tvPlanOtherPageLiteratureSA));

        tvPlanTotalDayAcademicSA = ((TextView) findViewById(R.id.tvPlanTotalDayAcademicSA));
        tvPlanAverageHourAcademicSA = ((TextView) findViewById(R.id.tvPlanAverageHourAcademicSA));

        tvPlanPresentClassSA = ((TextView) findViewById(R.id.tvPlanPresentClassSA));
        tvPlanTotalJamaatNamazSA = ((TextView) findViewById(R.id.tvPlanTotalJamaatNamazSA));

        tvPlanTotalDayCallOrgDutySA = ((TextView) findViewById(R.id.tvPlanTotalDayCallOrgDutySA));
        tvPlanTotalHourCallOrgDutySA = ((TextView) findViewById(R.id.tvPlanTotalHourCallOrgDutySA));
        tvPlanTotalDayOtherOrgDutySA = ((TextView) findViewById(R.id.tvPlanTotalDayOtherOrgDutySA));
        tvPlanAverageHourOtherOrgDutySA = ((TextView) findViewById(R.id.tvPlanAverageHourOtherOrgDutySA));

        tvPlanCriticismMiscSA = ((TextView) findViewById(R.id.tvPlanCriticismMiscSA));
        tvPlanPhysicalExerciseMiscSA = ((TextView) findViewById(R.id.tvPlanPhysicalExerciseMiscSA));
        tvPlanNewspaperMiscSA = ((TextView) findViewById(R.id.tvPlanNewspaperMiscSA));


        //dairy 18
        tvReportTotalDayAcademicSA = ((TextView) findViewById(R.id.tvReportTotalDayAcademicSA));
        tvReportAverageHourAcademicSA = ((TextView) findViewById(R.id.tvReportAverageHourAcademicSA));

        tvReportTotalDayQuranSA = ((TextView) findViewById(R.id.tvReportTotalDayQuranSA));
        tvReportAverageAyatQuranSA = ((TextView) findViewById(R.id.tvReportAverageAyatQuranSA));

        tvReportTotalDayHadithSA = ((TextView) findViewById(R.id.tvReportTotalDayHadithSA));
        tvReportAverageHadisHadithSA = ((TextView) findViewById(R.id.tvReportAverageHadisHadithSA));

        tvReportTotalPageLiteratureSA = ((TextView) findViewById(R.id.tvReportTotalPageLiteratureSA));
        tvReportIslamicPageLiteratureSA = ((TextView) findViewById(R.id.tvReportIslamicPageLiteratureSA));
        tvReportOtherPageLiteratureSA = ((TextView) findViewById(R.id.tvReportOtherPageLiteratureSA));

        tvReportPresentClassSA = ((TextView) findViewById(R.id.tvReportPresentClassSA));
        tvReportTotalJamaatNamazSA = ((TextView) findViewById(R.id.tvReportTotalJamaatNamazSA));

        tvReporTotalDayCallOrgDutySA = ((TextView) findViewById(R.id.tvReporTotalDayCallOrgDutySA));
        tvReportTotalHourCallOrgDutySA = ((TextView) findViewById(R.id.tvReportTotalHourCallOrgDutySA));
        tvReportTotalDayOtherOrgDutySA = ((TextView) findViewById(R.id.tvReportTotalDayOtherOrgDutySA));
        tvReportAverageHourOtherOgrDutySA = ((TextView) findViewById(R.id.tvReportAverageHourOtherOgrDutySA));

        tvReportCriticismMiscSA = ((TextView) findViewById(R.id.tvReportCriticismMiscSA));
        tvReportPhysicalExerciseMiscSA = ((TextView) findViewById(R.id.tvReportPhysicalExerciseMiscSA));
        tvReportNewspaperMiscSA = ((TextView) findViewById(R.id.tvReportNewspaperMiscSA));


        //percentage 18
        tvPercentageTotalDayAcademicSA = ((TextView) findViewById(R.id.tvPercentageTotalDayAcademicSA));
        tvPercentageAverageHourAcademicSA = ((TextView) findViewById(R.id.tvPercentageAverageHourAcademicSA));

        tvPercentageTotalDayQuranSA = ((TextView) findViewById(R.id.tvPercentageTotalDayQuranSA));
        tvPercentageAverageAyatQuranSA = ((TextView) findViewById(R.id.tvPercentageAverageAyatQuranSA));

        tvPercentageTotalDayHadithSA = ((TextView) findViewById(R.id.tvPercentageTotalDayHadithSA));
        tvPercentageAverageHadisHadithSA = ((TextView) findViewById(R.id.tvPercentageAverageHadisHadithSA));

        tvPercentageTotalPageLiteratureSA = ((TextView) findViewById(R.id.tvPercentageTotalPageLiteratureSA));
        tvPercentageIslamicPageLiteratureSA = ((TextView) findViewById(R.id.tvPercentageIslamicPageLiteratureSA));
        tvPercentageOtherPageLiteratureSA = ((TextView) findViewById(R.id.tvPercentageOtherPageLiteratureSA));

        tvPercentagePresentClassSA = ((TextView) findViewById(R.id.tvPercentagePresentClassSA));
        tvPercentageTotalJamaatNamazSA = ((TextView) findViewById(R.id.tvPercentageTotalJamaatNamazSA));

        tvPercentageTotalDayCallOrgDutySA = ((TextView) findViewById(R.id.tvPercentageTotalDayCallOrgDutySA));
        tvPercentageTotalHourCallOrgDutySA = ((TextView) findViewById(R.id.tvPercentageTotalHourCallOrgDutySA));
        tvPercentageTotalDayOtherOrgDutySA = ((TextView) findViewById(R.id.tvPercentageTotalDayOtherOrgDutySA));
        tvPercentageAverageHourOtherOrgDutySA = ((TextView) findViewById(R.id.tvPercentageAverageHourOtherOrgDutySA));

        tvPercentageCriticismMiscSA = ((TextView) findViewById(R.id.tvPercentageCriticismMiscSA));
        tvPercentagePhysicalExerciseMiscSA = ((TextView) findViewById(R.id.tvPercentagePhysicalExerciseMiscSA));
        tvPercentageNewspaperMiscSA = ((TextView) findViewById(R.id.tvPercentageNewspaperMiscSA));
    }


    private void setValueInTextView() {
        //plan 18
        planValueList = new ArrayList<>();
        
        planValueList.add(monthlyPlan.get(AllData.MP_Q_Total_day));
        tvPlanTotalDayQuranSA.setText(planValueList.get(0));
        planValueList.add(monthlyPlan.get(AllData.MP_Q_Average_Ayat));
        tvPlanAverageAyatQuranSA.setText(planValueList.get(1));

        planValueList.add(monthlyPlan.get(AllData.MP_H_Total_day));
        tvPlanTotalDayHadithSA.setText(planValueList.get(2));
        planValueList.add(monthlyPlan.get(AllData.MP_H_Average_Hadis));
        tvPlanAverageHadisHadithSA.setText(planValueList.get(3));

        planValueList.add(monthlyPlan.get(AllData.MP_L_Total_page));
        tvPlanTotalPageLiteratureSA.setText(planValueList.get(4));
        planValueList.add(monthlyPlan.get(AllData.MP_L_Islamic_Page));
        tvPlanIslamicPageLiteratureSA.setText(planValueList.get(5));
        planValueList.add(monthlyPlan.get(AllData.MP_L_Other_Page));
        tvPlanOtherPageLiteratureSA.setText(planValueList.get(6));

        planValueList.add(monthlyPlan.get(AllData.MP_A_Total_Day));
        tvPlanTotalDayAcademicSA.setText(planValueList.get(7));
        planValueList.add(monthlyPlan.get(AllData.MP_A_Average_Hour));
        tvPlanAverageHourAcademicSA.setText(planValueList.get(8));

        planValueList.add(monthlyPlan.get(AllData.MP_A_Total_Present_Class));
        tvPlanPresentClassSA.setText(planValueList.get(9));


        if (monthlyPlan.get(AllData.MP_N_Jamaat) != null) {
            int cbState = Integer.parseInt(monthlyPlan.get(AllData.MP_N_Jamaat));
            if (cbState == 1) {
                planValueList.add((month.getActualMaximum(Calendar.DAY_OF_MONTH) * 5) + "");
                tvPlanTotalJamaatNamazSA.setText(planValueList.get(10));

            } else {
                planValueList.add("");
                tvPlanTotalJamaatNamazSA.setText(planValueList.get(10));
            }
        } else {
            planValueList.add("");
            tvPlanTotalJamaatNamazSA.setText(planValueList.get(10));
        }

        planValueList.add(monthlyPlan.get(AllData.MP_OD_C_Total_Day));
        tvPlanTotalDayCallOrgDutySA.setText(planValueList.get(11));
        planValueList.add(monthlyPlan.get(AllData.MP_OD_C_Total_Hour));
        tvPlanTotalHourCallOrgDutySA.setText(planValueList.get(12));
        planValueList.add(monthlyPlan.get(AllData.MP_OD_O_Total_Day));
        tvPlanTotalDayOtherOrgDutySA.setText(planValueList.get(13));
        planValueList.add(monthlyPlan.get(AllData.MP_OD_O_Average_Hour));
        tvPlanAverageHourOtherOrgDutySA.setText(planValueList.get(14));

        planValueList.add(monthlyPlan.get(AllData.MP_Misc_Criticism));
        tvPlanCriticismMiscSA.setText(planValueList.get(15));
        planValueList.add(monthlyPlan.get(AllData.MP_Misc_Physical));
        tvPlanPhysicalExerciseMiscSA.setText(planValueList.get(16));
        planValueList.add(monthlyPlan.get(AllData.MP_Misc_Newspaper));
        tvPlanNewspaperMiscSA.setText(planValueList.get(17));


        //dairy 18
        reportValueList = new ArrayList<>();

        reportValueList.add(calculation.getTotalTypeDay(monthId, "dr_study_quran"));
        tvReportTotalDayQuranSA.setText(reportValueList.get(0));
        reportValueList.add(calculation.getAverageType(monthId, "dr_study_quran"));
        tvReportAverageAyatQuranSA.setText(reportValueList.get(1));

        reportValueList.add(calculation.getTotalTypeDay(monthId, "dr_study_hadith"));
        tvReportTotalDayHadithSA.setText(reportValueList.get(2));
        reportValueList.add(calculation.getAverageType(monthId, "dr_study_hadith"));
        tvReportAverageHadisHadithSA.setText(reportValueList.get(3));

        reportValueList.add(calculation.getTotalPage(monthId, "dr_study_literature_is", "dr_study_literature_other"));
        tvReportTotalPageLiteratureSA.setText(reportValueList.get(4));
        reportValueList.add(calculation.getTotalType(monthId, "dr_study_literature_is"));
        tvReportIslamicPageLiteratureSA.setText(reportValueList.get(5));
        reportValueList.add(calculation.getTotalType(monthId, "dr_study_literature_other"));
        tvReportOtherPageLiteratureSA.setText(reportValueList.get(6));

        reportValueList.add(calculation.getTotalTypeDay(monthId, "dr_study_academic"));
        tvReportTotalDayAcademicSA.setText(reportValueList.get(7));
        reportValueList.add(calculation.getAverageType(monthId, "dr_study_academic"));
        tvReportAverageHourAcademicSA.setText(reportValueList.get(8));

        reportValueList.add(calculation.getTotalTypeDay(monthId, "dr_class"));
        tvReportPresentClassSA.setText(reportValueList.get(9));
        reportValueList.add(calculation.getTotalType(monthId, "dr_namaz_jamaat"));
        tvReportTotalJamaatNamazSA.setText(reportValueList.get(10));

        reportValueList.add(calculation.getTotalTypeDay(monthId, "dr_duty_call"));
        tvReporTotalDayCallOrgDutySA.setText(reportValueList.get(11));
        reportValueList.add(calculation.getTotalType(monthId, "dr_duty_call"));
        tvReportTotalHourCallOrgDutySA.setText(reportValueList.get(12));
        reportValueList.add(calculation.getTotalTypeDay(monthId, "dr_duty_other"));
        tvReportTotalDayOtherOrgDutySA.setText(reportValueList.get(13));
        reportValueList.add(calculation.getAverageType(monthId, "dr_duty_other"));
        tvReportAverageHourOtherOgrDutySA.setText(reportValueList.get(14));

        reportValueList.add(calculation.getAllOthers(monthId, "dr_criticism"));
        tvReportCriticismMiscSA.setText(reportValueList.get(15));
        reportValueList.add(calculation.getAllOthers(monthId, "dr_physical_exercise"));
        tvReportPhysicalExerciseMiscSA.setText(reportValueList.get(16));
        reportValueList.add(calculation.getAllOthers(monthId, "dr_newspaper"));
        tvReportNewspaperMiscSA.setText(reportValueList.get(17));

        //percentage 18
        tvPercentageTotalDayQuranSA.setText(getPercentage(planValueList.get(0), reportValueList.get(0)));
        tvPercentageAverageAyatQuranSA.setText(getPercentage(planValueList.get(1), reportValueList.get(1)));

        tvPercentageTotalDayHadithSA.setText(getPercentage(planValueList.get(2),  reportValueList.get(2)));
        tvPercentageAverageHadisHadithSA.setText(getPercentage(planValueList.get(3),  reportValueList.get(3)));

        tvPercentageTotalPageLiteratureSA.setText(getPercentage(planValueList.get(4),  reportValueList.get(4)));
        tvPercentageIslamicPageLiteratureSA.setText(getPercentage(planValueList.get(5), reportValueList.get(5)));
        tvPercentageOtherPageLiteratureSA.setText(getPercentage(planValueList.get(6),  reportValueList.get(6)));

        tvPercentageTotalDayAcademicSA.setText(getPercentage(planValueList.get(7),  reportValueList.get(7)));
        tvPercentageAverageHourAcademicSA.setText(getPercentage(planValueList.get(8),  reportValueList.get(8)));

        tvPercentagePresentClassSA.setText(getPercentage(planValueList.get(9),  reportValueList.get(9)));
        tvPercentageTotalJamaatNamazSA.setText(getPercentage(planValueList.get(10),  reportValueList.get(10)));

        tvPercentageTotalDayCallOrgDutySA.setText(getPercentage(planValueList.get(11), reportValueList.get(11)));
        tvPercentageTotalHourCallOrgDutySA.setText(getPercentage(planValueList.get(12), reportValueList.get(12)));
        tvPercentageTotalDayOtherOrgDutySA.setText(getPercentage(planValueList.get(13), reportValueList.get(13)));
        tvPercentageAverageHourOtherOrgDutySA.setText(getPercentage(planValueList.get(14), reportValueList.get(14)));
        
        tvPercentageCriticismMiscSA.setText(getPercentage(planValueList.get(15), reportValueList.get(15)));
        tvPercentagePhysicalExerciseMiscSA.setText(getPercentage(planValueList.get(16), reportValueList.get(16)));
        tvPercentageNewspaperMiscSA.setText(getPercentage(planValueList.get(17), reportValueList.get(17)));

    }


    private void refreshCalendar() {
        tvTitle = (TextView) findViewById(R.id.tvDateSA);
        monthId = DateFormat.format("MMMM yyyy", month).toString();
        tvTitle.setText(monthId);

        calculation = new MonthlyReportCalculation(this);
        monthlyPlan = dbHelper.getAllContentFromTableMonthlyPlan(monthId);
        setValueInTextView();
    }


    private boolean setNextMonth() {
        if (month.get(Calendar.YEAR) == currentMonth.get(Calendar.YEAR) &&
                month.get(Calendar.MONTH) == currentMonth.get(Calendar.MONTH)) {

            Toast.makeText(getApplicationContext(), "You can't see report later than current month !",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (month.get(Calendar.MONTH) == month.getActualMaximum(Calendar.MONTH)) {
            month.set(month.get(Calendar.YEAR) + 1, month.getActualMinimum(Calendar.MONTH), 1);

        } else {
            month.set(month.get(Calendar.YEAR), month.get(Calendar.MONTH) + 1, 1);
        }
        return true;
    }


    private void setPreviousMonth() {
        if (month.get(Calendar.MONTH) == month.getActualMinimum(Calendar.MONTH)) {
            month.set(month.get(Calendar.YEAR) - 1, month.getActualMaximum(Calendar.MONTH), 1);
            return;

        } else {
            month.add(Calendar.MONTH, -1);
            month.set(month.get(Calendar.YEAR), month.get(Calendar.MONTH), 1);
            return;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: {
                this.finish();
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
