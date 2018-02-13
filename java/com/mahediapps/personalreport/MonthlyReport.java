package com.mahediapps.personalreport;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.mahediapps.model.DatabaseHelper;
import com.mahediapps.model.MonthlyReportCalculation;

public class MonthlyReport extends ActionBarActivity implements android.view.View.OnClickListener {

    private Button bSave;

    private EditText etSuraNameQuranMR, etMemorizeQuranMR, etDarseQuranMR,
            etBookNameHadithMR, etMemorizeHadithMR, etDarseHadithMR,
            etBookNameLiteratureMR, etBookNoteLiteratureMR,
            etSchoolStudentContactMR, etTeacherContactMR, etVIPContactMR,
            etTotalMenCallOrgDutyMR,
            etMemberIncrementMR, etMemberApplicantIncrementMR, etAssociateIncrementMR, etAssociateApplicantIncrementMR,
            etWorkerIncrementMR, etSupporterIncrementMR, etWellWisherIncrementMR, etFriendIncrementMR,
            etPoricitiDistributionMR, etEnglishPaperDistributionMR, etChatrosongbadDistributionMR,
            etKishorPotrikaDistributionMR, etPerspectiveDistributionMR, etClassRoutineDistributionMR, etStikarCardDistributionMR,
            etBaitulmalBMMR, etStudentWellfareBMMR, etJarBMMR, etTableBankBMMR,
            etNonMuslimMiscMR, etFriendOrgMiscMR, etMuharramaContactMiscMR,
            etSuggestionMR;

    private TextView tvTotalDayQuranMR, tvAverageAyatQuranMR, tvTotalDayHadithMR, tvAverageHadisHadithMR,
            tvTotalPageLiteratureMR, tvIslamiPageLiteratureMR, tvOtherPageLiteratureMR,
            tvTotalDayAcademicMR, tvAverageHourAcademicMR, tvPresentClassAcademicMR,
            tvJamaatNamazMR, tvAverageJamaatNamazMR, tvKajjaNamazMR,
            tvTotalDayCallOrgDutyMR, tvTotalHourCallOrgDutyMR, tvTotalDayOtherOrgDutyMR, tvAverageHourOtherOrgDutyMR,
            tvMemberContactMR, tvAssociateContactMR, tvWorkerContactMR, tvSupporterContactMR,
            tvFriendContactMR, tvTalentStudentContactMD, tvWellWisherContactMR, tvIslamicLiteratureDistributionMR,
            tvCriticismMiscMR, tvPhysicalExerciseMiscMR, tvNewspaperMiscMR;

    private DatabaseHelper dbHelper;
    private CheckBox cbPlanExistMD;
    private Toolbar mToolbar;
    private MonthlyReportCalculation calculation;
    private ImageButton ibNext, ibPrevious;
    private Calendar currentMonth, month;
    private String monthId = "";
    private HashMap<String, String> monthlyReport;


    private TextView tvTitle, tvReportKeepingDays;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_monthly_report);
        dbHelper = new DatabaseHelper(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Monthly Report");

        month = Calendar.getInstance();
        currentMonth = Calendar.getInstance();
        calculation = new MonthlyReportCalculation(this);

        ibPrevious = (ImageButton) findViewById(R.id.ibPreviousMR);
        ibNext = (ImageButton) findViewById(R.id.ibNextMR);

        ibPrevious.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View view) {
                setPreviousMonth();
                refreshCalendar();
            }
        });

        ibNext.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View view) {
                if (setNextMonth()) {
                    refreshCalendar();
                }
            }
        });

        bSave = (Button) findViewById(R.id.bSaveMR);
        initializeEditText();
        initializeTextView();
        refreshCalendar();

        bSave.setOnClickListener(this);
    }


    private void setValueInLayoutElement() {

        //EditText -- 35
        etSuraNameQuranMR.setText(monthlyReport.get("mr_quran_sura_name"));
        etDarseQuranMR.setText(monthlyReport.get("mr_quran_darse"));
        etMemorizeQuranMR.setText(monthlyReport.get("mr_quran_memorize_ayat"));

        etBookNameHadithMR.setText(monthlyReport.get("mr_hadith_book_name"));
        etDarseHadithMR.setText(monthlyReport.get("mr_hadith_darse"));
        etMemorizeHadithMR.setText(monthlyReport.get("mr_hadith_memorize_hadis"));

        etBookNameLiteratureMR.setText(monthlyReport.get("mr_literature_book_name"));
        etBookNoteLiteratureMR.setText(monthlyReport.get("mr_literature_book_note"));

        etSchoolStudentContactMR.setText(monthlyReport.get("mr_contact_school_student"));
        etTeacherContactMR.setText(monthlyReport.get("mr_contact_teacher"));
        etVIPContactMR.setText(monthlyReport.get("mr_contact_vip"));

        etTotalMenCallOrgDutyMR.setText(monthlyReport.get("mr_orgduty_total_men"));

        etPoricitiDistributionMR.setText(monthlyReport.get("mr_distribution_poriciti"));
        etEnglishPaperDistributionMR.setText(monthlyReport.get("mr_distribution_english_paper"));
        etChatrosongbadDistributionMR.setText(monthlyReport.get("mr_distribution_chatrosongbad"));
        etKishorPotrikaDistributionMR.setText(monthlyReport.get("mr_distribution_kishor_potrika"));
        etPerspectiveDistributionMR.setText(monthlyReport.get("mr_distribution_perspective"));
        etClassRoutineDistributionMR.setText(monthlyReport.get("mr_distribution_class_routine"));
        etStikarCardDistributionMR.setText(monthlyReport.get("mr_distribution_stikar"));

        etMemberIncrementMR.setText(monthlyReport.get("mr_increment_member"));
        etMemberApplicantIncrementMR.setText(monthlyReport.get("mr_increment_member_applicant"));
        etAssociateIncrementMR.setText(monthlyReport.get("mr_increment_associate"));
        etAssociateApplicantIncrementMR.setText(monthlyReport.get("mr_increment_associate_applicant"));
        etWorkerIncrementMR.setText(monthlyReport.get("mr_increment_worker"));
        etSupporterIncrementMR.setText(monthlyReport.get("mr_increment_supporter"));
        etFriendIncrementMR.setText(monthlyReport.get("mr_increment_friend"));
        etWellWisherIncrementMR.setText(monthlyReport.get("mr_increment_wellwisher"));

        etBaitulmalBMMR.setText(monthlyReport.get("mr_baitulmal_baitulmal"));
        etStudentWellfareBMMR.setText(monthlyReport.get("mr_baitulmal_student_wellfare"));
        etJarBMMR.setText(monthlyReport.get("mr_baitulmal_jar"));
        etTableBankBMMR.setText(monthlyReport.get("mr_baitulmal_table_bank"));

        etNonMuslimMiscMR.setText(monthlyReport.get("mr_misc_nonmuslim"));
        etFriendOrgMiscMR.setText(monthlyReport.get("mr_misc_friend_org"));
        etMuharramaContactMiscMR.setText(monthlyReport.get("mr_misc_muharrama_contact"));

        etSuggestionMR.setText(monthlyReport.get("mr_suggestion"));

        //TextView -- 28
        tvTotalDayQuranMR.setText(calculation.getTotalTypeDay(monthId, "dr_study_quran"));
        tvTotalDayHadithMR.setText(calculation.getTotalTypeDay(monthId, "dr_study_hadith"));
        tvTotalDayAcademicMR.setText(calculation.getTotalTypeDay(monthId, "dr_study_academic"));
        tvTotalDayCallOrgDutyMR.setText(calculation.getTotalTypeDay(monthId, "dr_duty_call"));
        tvTotalDayOtherOrgDutyMR.setText(calculation.getTotalTypeDay(monthId, "dr_duty_other"));

        tvJamaatNamazMR.setText(calculation.getTotalType(monthId, "dr_namaz_jamaat"));
        tvKajjaNamazMR.setText(calculation.getTotalType(monthId, "dr_namaz_kajja"));
        tvIslamiPageLiteratureMR.setText(calculation.getTotalType(monthId, "dr_study_literature_is"));
        tvOtherPageLiteratureMR.setText(calculation.getTotalType(monthId, "dr_study_literature_other"));
        tvTotalHourCallOrgDutyMR.setText(calculation.getTotalType(monthId, "dr_duty_call"));
        tvIslamicLiteratureDistributionMR.setText(calculation.getTotalType(monthId, "dr_contact_book"));

        tvAverageAyatQuranMR.setText(calculation.getAverageType(monthId, "dr_study_quran"));
        tvAverageHadisHadithMR.setText(calculation.getAverageType(monthId, "dr_study_hadith"));
        tvTotalPageLiteratureMR.setText(calculation.getTotalPage(monthId, "dr_study_literature_is", "dr_study_literature_other"));
        tvAverageHourAcademicMR.setText(calculation.getAverageType(monthId, "dr_study_academic"));
        tvAverageJamaatNamazMR.setText(calculation.getAverageType(monthId, "dr_namaz_jamaat"));
        tvAverageHourOtherOrgDutyMR.setText(calculation.getAverageType(monthId, "dr_duty_other"));

        tvMemberContactMR.setText(calculation.getAllContact(monthId, "dr_contact_member"));
        tvAssociateContactMR.setText(calculation.getAllContact(monthId, "dr_contact_associate"));
        tvWorkerContactMR.setText(calculation.getAllContact(monthId, "dr_contact_worker"));
        tvSupporterContactMR.setText(calculation.getAllContact(monthId, "dr_contact_supporter"));
        tvFriendContactMR.setText(calculation.getAllContact(monthId, "dr_contact_friend"));
        tvTalentStudentContactMD.setText(calculation.getAllContact(monthId, "dr_contact_talent_student"));
        tvWellWisherContactMR.setText(calculation.getAllContact(monthId, "dr_contact_wellwisher"));

        tvPresentClassAcademicMR.setText(calculation.getAllOthers(monthId, "dr_class"));
        tvCriticismMiscMR.setText(calculation.getAllOthers(monthId, "dr_criticism"));
        tvPhysicalExerciseMiscMR.setText(calculation.getAllOthers(monthId, "dr_physical_exercise"));
        tvNewspaperMiscMR.setText(calculation.getAllOthers(monthId, "dr_newspaper"));

    }


    private void refreshCalendar() {
        monthId = DateFormat.format("MMMM yyyy", month).toString();
        monthlyReport = dbHelper.getAllContentFromTableEtMR(monthId);

        tvTitle = (TextView) findViewById(R.id.tvMonthOfReportMR);
        tvReportKeepingDays = (TextView) findViewById(R.id.tvReportKeepingDaysMR);
        cbPlanExistMD = (CheckBox) findViewById(R.id.cbPlanExistMR);

        tvTitle.setText(monthId);
        int cbState = dbHelper.checkMonthlyPlanExistOrNot(monthId);
        if (cbState == 1) {
            cbPlanExistMD.setChecked(true);
        } else {
            cbPlanExistMD.setChecked(false);
        }
        tvReportKeepingDays.setText("Total Dairy Keeping Days:  " + getMonthlyDairyKeepingDays(monthId));

        clearFocusFromEditText();
        setValueInLayoutElement();
        bSaveOrUpdate();

    }


    public void bSaveOrUpdate(){
        if (dbHelper.checkMonthlyPlanExistOrNot(monthId) == 0) {
            bSave.setText("Save");

        }else{
            bSave.setText("Update");
        }
    }


    private boolean setNextMonth() {
        if (month.get(Calendar.YEAR) == currentMonth.get(Calendar.YEAR) &&
                month.get(Calendar.MONTH) == currentMonth.get(Calendar.MONTH)) {

            Toast.makeText(getApplicationContext(), "You can't see dairy later than current month !",
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


    public void onClick(View view) {

        monthlyReport = new HashMap();

        monthlyReport.put("md_month", monthId);
        monthlyReport.put("mr_quran_sura_name", etSuraNameQuranMR.getText().toString());
        monthlyReport.put("mr_quran_darse", etDarseQuranMR.getText().toString());
        monthlyReport.put("mr_quran_memorize_ayat", etMemorizeQuranMR.getText().toString());


        monthlyReport.put("mr_hadith_book_name", etBookNameHadithMR.getText().toString());
        monthlyReport.put("mr_hadith_darse", etDarseHadithMR.getText().toString());
        monthlyReport.put("mr_hadith_memorize_hadis", etMemorizeHadithMR.getText().toString());


        monthlyReport.put("mr_literature_book_name", etBookNameLiteratureMR.getText().toString());
        monthlyReport.put("mr_literature_book_note", etBookNoteLiteratureMR.getText().toString());

        monthlyReport.put("mr_contact_school_student", etSchoolStudentContactMR.getText().toString());
        monthlyReport.put("mr_contact_teacher", etTeacherContactMR.getText().toString());
        monthlyReport.put("mr_contact_vip", etVIPContactMR.getText().toString());

        monthlyReport.put("mr_orgduty_total_men", etTotalMenCallOrgDutyMR.getText().toString());

        monthlyReport.put("mr_distribution_poriciti", etPoricitiDistributionMR.getText().toString());
        monthlyReport.put("mr_distribution_english_paper", etEnglishPaperDistributionMR.getText().toString());
        monthlyReport.put("mr_distribution_chatrosongbad", etChatrosongbadDistributionMR.getText().toString());
        monthlyReport.put("mr_distribution_kishor_potrika", etKishorPotrikaDistributionMR.getText().toString());
        monthlyReport.put("mr_distribution_perspective", etPerspectiveDistributionMR.getText().toString());
        monthlyReport.put("mr_distribution_class_routine", etClassRoutineDistributionMR.getText().toString());
        monthlyReport.put("mr_distribution_stikar", etStikarCardDistributionMR.getText().toString());

        monthlyReport.put("mr_increment_member", etMemberIncrementMR.getText().toString());
        monthlyReport.put("mr_increment_member_applicant", etMemberApplicantIncrementMR.getText().toString());
        monthlyReport.put("mr_increment_associate", etAssociateIncrementMR.getText().toString());
        monthlyReport.put("mr_increment_associate_applicant", etAssociateApplicantIncrementMR.getText().toString());
        monthlyReport.put("mr_increment_worker", etWorkerIncrementMR.getText().toString());
        monthlyReport.put("mr_increment_supporter", etSupporterIncrementMR.getText().toString());
        monthlyReport.put("mr_increment_friend", etFriendIncrementMR.getText().toString());
        monthlyReport.put("mr_increment_wellwisher", etWellWisherIncrementMR.getText().toString());


        monthlyReport.put("mr_baitulmal_baitulmal", etBaitulmalBMMR.getText().toString());
        monthlyReport.put("mr_baitulmal_student_wellfare", etStudentWellfareBMMR.getText().toString());
        monthlyReport.put("mr_baitulmal_jar", etJarBMMR.getText().toString());
        monthlyReport.put("mr_baitulmal_table_bank", etTableBankBMMR.getText().toString());

        monthlyReport.put("mr_misc_nonmuslim", etNonMuslimMiscMR.getText().toString());
        monthlyReport.put("mr_misc_friend_org", etFriendOrgMiscMR.getText().toString());
        monthlyReport.put("mr_misc_muharrama_contact", etMuharramaContactMiscMR.getText().toString());
        monthlyReport.put("mr_suggestion", etSuggestionMR.getText().toString());

        if (dbHelper.checkThisMonthExistOrNot(monthId) == 0) {
            Toast.makeText(this, "Your report is saved successfully !!", Toast.LENGTH_SHORT).show();
            dbHelper.insertRowInTableEtOfMR(monthlyReport);
            return;

        } else {
            dbHelper.updateRowInTableEtMR(monthlyReport);
            Toast.makeText(this, "Your report is updated successfully !!", Toast.LENGTH_SHORT).show();
            return;
        }
    }


    private void initializeTextView() {

        // TextView -- 28
        tvTotalDayQuranMR = ((TextView) findViewById(R.id.tvTotalDayQuranMR));
        tvAverageAyatQuranMR = ((TextView) findViewById(R.id.tvAverageAyatQuranMR));

        tvTotalDayHadithMR = ((TextView) findViewById(R.id.tvTotalDayHadithMR));
        tvAverageHadisHadithMR = ((TextView) findViewById(R.id.tvAverageHadisHadithMR));

        tvTotalPageLiteratureMR = ((TextView) findViewById(R.id.tvTotalPageLiteratureMR));
        tvIslamiPageLiteratureMR = ((TextView) findViewById(R.id.tvIslamiPageLiteratureMR));
        tvOtherPageLiteratureMR = ((TextView) findViewById(R.id.tvOtherPageLiteratureMR));

        tvTotalDayAcademicMR = ((TextView) findViewById(R.id.tvTotalDayAcademicMR));
        tvAverageHourAcademicMR = ((TextView) findViewById(R.id.tvAverageHourAcademicMR));
        tvPresentClassAcademicMR = ((TextView) findViewById(R.id.tvPresentClassAcademicMR));

        tvJamaatNamazMR = ((TextView) findViewById(R.id.tvJamaatNamazMR));
        tvAverageJamaatNamazMR = ((TextView) findViewById(R.id.tvAverageJamaatNamazMR));
        tvKajjaNamazMR = ((TextView) findViewById(R.id.tvKajjaNamazMR));

        tvMemberContactMR = ((TextView) findViewById(R.id.tvMemberContactMR));
        tvAssociateContactMR = ((TextView) findViewById(R.id.tvAssociateContactMR));
        tvWorkerContactMR = ((TextView) findViewById(R.id.tvWorkerContactMR));
        tvSupporterContactMR = ((TextView) findViewById(R.id.tvSupporterContactMR));
        tvFriendContactMR = ((TextView) findViewById(R.id.tvFriendContactMR));
        tvTalentStudentContactMD = ((TextView) findViewById(R.id.tvTalentStudentContactMR));
        tvWellWisherContactMR = ((TextView) findViewById(R.id.tvWellWisherContactMR));

        tvTotalDayCallOrgDutyMR = ((TextView) findViewById(R.id.tvTotalDayCallOrgDutyMR));
        tvTotalHourCallOrgDutyMR = ((TextView) findViewById(R.id.tvTotalHourCallOrgDutyMR));
        tvTotalDayOtherOrgDutyMR = ((TextView) findViewById(R.id.tvTotalDayOtherOrgDutyMR));
        tvAverageHourOtherOrgDutyMR = ((TextView) findViewById(R.id.tvAverageHourOtherOrgDutyMR));

        tvIslamicLiteratureDistributionMR = (TextView) findViewById(R.id.tvIslamicLiteratureDistributionMR);
        tvCriticismMiscMR = ((TextView) findViewById(R.id.tvCriticismMiscMR));
        tvPhysicalExerciseMiscMR = ((TextView) findViewById(R.id.tvPhysicalExerciseMiscMR));
        tvNewspaperMiscMR = ((TextView) findViewById(R.id.tvNewspaperMiscMR));
    }


    private void initializeEditText() {

        //EditText -- 35
        etSuraNameQuranMR = ((EditText) findViewById(R.id.etSuraNameQuranMR));
        etMemorizeQuranMR = ((EditText) findViewById(R.id.etMemorizeQuranMR));
        etDarseQuranMR = (EditText) findViewById(R.id.etDarseQuranMR);

        etBookNameHadithMR = ((EditText) findViewById(R.id.etBookNameHadithMR));
        etMemorizeHadithMR = ((EditText) findViewById(R.id.etMemorizeHadithMR));
        etDarseHadithMR = (EditText) findViewById(R.id.etDarseHadithMR);

        etBookNameLiteratureMR = ((EditText) findViewById(R.id.etBookNameLiteratureMR));
        etBookNoteLiteratureMR = (EditText) findViewById(R.id.etBookNoteLiteratureMR);

        etSchoolStudentContactMR = ((EditText) findViewById(R.id.etSchoolStudentContactMR));
        etTeacherContactMR = ((EditText) findViewById(R.id.etTeacherContactMR));
        etVIPContactMR = ((EditText) findViewById(R.id.etVIPContactMR));

        etTotalMenCallOrgDutyMR = (EditText) findViewById(R.id.etTotalMenCallOrgDutyMR);

        etPoricitiDistributionMR = ((EditText) findViewById(R.id.etPoricitiDistributionMR));
        etEnglishPaperDistributionMR = ((EditText) findViewById(R.id.etEnglishPaperDistributionMR));
        etChatrosongbadDistributionMR = ((EditText) findViewById(R.id.etChatrosongbadDistributionMR));
        etKishorPotrikaDistributionMR = ((EditText) findViewById(R.id.etKishorPotrikaDistributionMR));
        etPerspectiveDistributionMR = ((EditText) findViewById(R.id.etPerspectiveDistributionMR));
        etClassRoutineDistributionMR = ((EditText) findViewById(R.id.etClassRoutineDistributionMR));
        etStikarCardDistributionMR = ((EditText) findViewById(R.id.etStikarCardDistributionMR));

        etMemberIncrementMR = ((EditText) findViewById(R.id.etMemberIncrementMR));
        etMemberApplicantIncrementMR = ((EditText) findViewById(R.id.etMemberApplicantIncrementMR));
        etAssociateIncrementMR = ((EditText) findViewById(R.id.etAssociateIncrementMR));
        etAssociateApplicantIncrementMR = ((EditText) findViewById(R.id.etAssociateApplicantIncrementMR));
        etWorkerIncrementMR = ((EditText) findViewById(R.id.etWorkerIncrementMR));
        etSupporterIncrementMR = ((EditText) findViewById(R.id.etSupporterIncrementMR));
        etFriendIncrementMR = ((EditText) findViewById(R.id.etFriendIncrementMR));
        etWellWisherIncrementMR = ((EditText) findViewById(R.id.etWellWisherIncrementMR));


        etBaitulmalBMMR = ((EditText) findViewById(R.id.etBaitulmalBMMR));
        etStudentWellfareBMMR = ((EditText) findViewById(R.id.etStudentWellfareBMMR));
        etJarBMMR = ((EditText) findViewById(R.id.etJarBMMR));
        etTableBankBMMR = (EditText) findViewById(R.id.etTableBankBMMR);

        etNonMuslimMiscMR = ((EditText) findViewById(R.id.etNonMuslimMiscMR));
        etFriendOrgMiscMR = ((EditText) findViewById(R.id.etFriendOrgMiscMR));
        etMuharramaContactMiscMR = ((EditText) findViewById(R.id.etMuharramaContactMiscMR));

        etSuggestionMR = (EditText) findViewById(R.id.etSuggestionMR);
    }


    public String getMonthlyDairyKeepingDays(String monthId) {
        ArrayList<Integer> list = dbHelper.getTotalNumberOfReportKeepingInfoForMonth(monthId);
        return (list.size() + "");
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


    @Override
    protected void onResume() {
        super.onResume();

        if (getIntent().hasExtra("month_id")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
            try {
                Date date = dateFormat.parse(getIntent().getStringExtra("month_id"));
                month.setTime(date);
                refreshCalendar();

            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
        }

    }


    private void clearFocusFromEditText() {

        etMemorizeQuranMR.clearFocus();
        etDarseQuranMR.clearFocus();
        etBookNameHadithMR.clearFocus();
        etMemorizeHadithMR.clearFocus();
        etDarseHadithMR.clearFocus();
        etBookNameLiteratureMR.clearFocus();
        etBookNoteLiteratureMR.clearFocus();
        etSchoolStudentContactMR.clearFocus();
        etTeacherContactMR.clearFocus();
        etVIPContactMR.clearFocus();
        etTotalMenCallOrgDutyMR.clearFocus();
        etMemberIncrementMR.clearFocus();
        etMemberApplicantIncrementMR.clearFocus();
        etAssociateIncrementMR.clearFocus();
        etAssociateApplicantIncrementMR.clearFocus();
        etWorkerIncrementMR.clearFocus();
        etSupporterIncrementMR.clearFocus();
        etWellWisherIncrementMR.clearFocus();
        etFriendIncrementMR.clearFocus();
        etPoricitiDistributionMR.clearFocus();
        etEnglishPaperDistributionMR.clearFocus();
        etChatrosongbadDistributionMR.clearFocus();
        etKishorPotrikaDistributionMR.clearFocus();
        etPerspectiveDistributionMR.clearFocus();
        etClassRoutineDistributionMR.clearFocus();
        etStikarCardDistributionMR.clearFocus();
        etBaitulmalBMMR.clearFocus();
        etStudentWellfareBMMR.clearFocus();
        etJarBMMR.clearFocus();
        etTableBankBMMR.clearFocus();
        etNonMuslimMiscMR.clearFocus();
        etFriendOrgMiscMR.clearFocus();
        etMuharramaContactMiscMR.clearFocus();
        etSuggestionMR.clearFocus();
    }
}

