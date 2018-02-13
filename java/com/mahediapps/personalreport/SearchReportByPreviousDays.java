package com.mahediapps.personalreport;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import com.mahediapps.model.PreviousDaysReportCalculation;

public class SearchReportByPreviousDays extends ActionBarActivity {

    private TextView tvFromAndToPR, tvReportKeepingDaysPR,
            tvTotalDayQuranPR, tvAverageAyatQuranPR, tvTotalDayHadithPR, tvAverageHadisHadithPR,
            tvTotalPageLiteraturePR, tvIslamicPageLiteraturePR, tvOtherPageLiteraturePR,
            tvTotalDayAcademicPR, tvAverageHourAcademicPR, tvPresentClassPR,
            tvTotalJamaatNamazPR, tvAverageJamaatNamazPR, tvTotalKajjaNamazPR,
            tvTotalDayCallOrgDutyPR, tvTotalHourCallOrgDutyPR, tvTotalDayOtherOrgDutyPR, tvAverageHourOtherOrgDutyPR,
            tvCriticismMiscPR, tvPhysicalExercisePR, tvNewspaperMiscPR;


    private Toolbar mToolbar;
    private  Handler handler;
    private ProgressDialog pd;
    private String sDayNo;
    private ArrayList<String> list;
    private PreviousDaysReportCalculation calculation;


    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_previous_report);
        sDayNo = getIntent().getStringExtra("last_days");

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Previous " + sDayNo + " Days Report");

        initialize();
        pd = ProgressDialog.show(this, "", "Calculating Report. Please wait...");
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                setValueInTextView();
                pd.dismiss();
            }
        };

       backgroundWork.start();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home: {
                this.finish();
            }

        }
        return super.onOptionsItemSelected(item);
    }


    private void initialize() {

        tvFromAndToPR = ((TextView) findViewById(R.id.tvFromAndToPR));
        tvReportKeepingDaysPR = ((TextView) findViewById(R.id.tvReportKeepingDaysPR));

        tvTotalDayQuranPR = ((TextView) findViewById(R.id.tvTotalDayQuranPR));
        tvAverageAyatQuranPR = ((TextView) findViewById(R.id.tvAverageAyatQuranPR));

        tvTotalDayHadithPR = ((TextView) findViewById(R.id.tvTotalDayHadithPR));
        tvAverageHadisHadithPR = ((TextView) findViewById(R.id.tvAverageHadisHadithPR));

        tvTotalPageLiteraturePR = ((TextView) findViewById(R.id.tvTotalPageLiteraturePR));
        tvIslamicPageLiteraturePR = ((TextView) findViewById(R.id.tvIslamicPageLiteraturePR));
        tvOtherPageLiteraturePR = ((TextView) findViewById(R.id.tvOtherPageLiteraturePR));

        tvTotalDayAcademicPR = ((TextView) findViewById(R.id.tvTotalDayAcademicPR));
        tvAverageHourAcademicPR = ((TextView) findViewById(R.id.tvAverageHourAcademicPR));
        tvPresentClassPR = ((TextView) findViewById(R.id.tvPresentClassPR));

        tvTotalJamaatNamazPR = ((TextView) findViewById(R.id.tvTotalJamaatNamazPR));
        tvAverageJamaatNamazPR = ((TextView) findViewById(R.id.tvAverageJamaatNamazPR));
        tvTotalKajjaNamazPR = (TextView) findViewById(R.id.tvTotalKajjaNamazPR);

        tvTotalDayCallOrgDutyPR = ((TextView) findViewById(R.id.tvTotalDayCallOrgDutyPR));
        tvTotalHourCallOrgDutyPR = ((TextView) findViewById(R.id.tvTotalHourCallOrgDutyPR));
        tvTotalDayOtherOrgDutyPR = ((TextView) findViewById(R.id.tvTotalDayOtherOrgDutyPR));
        tvAverageHourOtherOrgDutyPR = ((TextView) findViewById(R.id.tvAverageHourOtherOrgDutyPR));

        tvNewspaperMiscPR = ((TextView) findViewById(R.id.tvNewspaperMiscPR));
        tvPhysicalExercisePR = ((TextView) findViewById(R.id.tvPhysicalExercisePR));
        tvCriticismMiscPR = ((TextView) findViewById(R.id.tvCriticismMiscPR));
    }


    private void setValueInTextView() {
        
        tvFromAndToPR.setText(list.get(0));
        tvReportKeepingDaysPR.setText(list.get(1));

        tvTotalDayQuranPR.setText(list.get(2));
        tvAverageAyatQuranPR.setText(list.get(3));

        tvTotalDayHadithPR.setText(list.get(4));
        tvAverageHadisHadithPR.setText(list.get(5));

        tvTotalPageLiteraturePR.setText(list.get(6));
        tvIslamicPageLiteraturePR.setText(list.get(7));
        tvOtherPageLiteraturePR.setText(list.get(8));

        tvTotalDayAcademicPR.setText(list.get(9));
        tvAverageHourAcademicPR.setText(list.get(10));
        tvPresentClassPR.setText(list.get(11));

        tvTotalJamaatNamazPR.setText(list.get(12));
        tvAverageJamaatNamazPR.setText(list.get(13));
        tvTotalKajjaNamazPR.setText(list.get(14));

        tvTotalDayCallOrgDutyPR.setText(list.get(15));
        tvTotalHourCallOrgDutyPR.setText(list.get(16));
        tvTotalDayOtherOrgDutyPR.setText(list.get(17));
        tvAverageHourOtherOrgDutyPR.setText(list.get(18));

        tvNewspaperMiscPR.setText(list.get(19));
        tvPhysicalExercisePR.setText(list.get(20));
        tvCriticismMiscPR.setText(list.get(21));
    }


    Thread backgroundWork = new Thread(new Runnable() {
        @Override
        public void run() {

            try {
                calculation = new PreviousDaysReportCalculation(SearchReportByPreviousDays.this, Integer.parseInt(sDayNo));
                list = new ArrayList<>();
                list.add(calculation.getFromAndTo());
                list.add(calculation.getPreviousReportKeepingDays());

                list.add(calculation.getTotalTypeDay("dr_study_quran"));
                list.add(calculation.getAverageType("dr_study_quran"));
                list.add(calculation.getTotalTypeDay("dr_study_hadith"));
                list.add(calculation.getAverageType("dr_study_hadith"));

                list.add(calculation.getTotalPage("dr_study_literature_is", "dr_study_literature_other"));
                list.add(calculation.getTotalType("dr_study_literature_is"));
                list.add(calculation.getTotalType("dr_study_literature_other"));

                list.add(calculation.getTotalTypeDay("dr_study_academic"));
                list.add(calculation.getAverageType("dr_study_academic"));
                list.add(calculation.getTotalTypeDay("dr_class"));

                list.add(calculation.getTotalType("dr_namaz_jamaat"));
                list.add(calculation.getAverageType("dr_namaz_jamaat"));
                list.add(calculation.getTotalType("dr_namaz_kajja"));

                list.add(calculation.getTotalTypeDay("dr_duty_call"));
                list.add(calculation.getTotalType("dr_duty_call"));
                list.add(calculation.getTotalTypeDay("dr_duty_other"));
                list.add(calculation.getAverageType("dr_duty_other"));

                list.add(calculation.getAllOthers("dr_newspaper"));
                list.add(calculation.getAllOthers("dr_physical_exercise"));
                list.add(calculation.getAllOthers("dr_criticism"));

                threadMessage(list.size());
            }catch (Throwable t){
                Log.e("Allah help me" , "waiting for finish");
            }
        }

        private void threadMessage(int size){
            if(list.size() == size){
                Message msg = handler.obtainMessage();
                handler.sendMessage(msg);
            }
        }
    });
}
