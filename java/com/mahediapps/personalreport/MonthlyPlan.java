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

import java.util.Calendar;
import java.util.HashMap;

import com.mahediapps.model.AllData;
import com.mahediapps.model.DatabaseHelper;

public class MonthlyPlan extends ActionBarActivity implements View.OnClickListener {

    private EditText
            etSuraNameQuranMP, etTotalDayQuranMP, etAverageAyatQuranMP, etDarseQuranMP, etMemorizeQuranMP,
            etBookNameHadithMP, etTotalDayHadithMP, etAverageHadisHadithMP, etDarseHadithMP, etMemorizeHadithMP,
            etBookNameLiteratureMP, etTotalPageLiteratureMP, etIslamicPageLiteratureMP,
            etOtherPageLiteratureMP, etBookNoteLiteratureMP,

            etTotalDayAcademicMP, etAverageHourAcademicMP, etPresentInClassAcademicMP,
            etTotalDayCallOrgDutyMP, etTotalHourCallOrgDutyMP, etTotalDayOtherOrgDutyMP, etAverageHourOtherOrgDutyMP,

            etMemberContactMP, etAssociateContactMP, etWorkerContactMP, etSupporterContactMP, etSchoolStudentContactMP,
            etTalentStudentContactMP, etFriendContactMP, etTeacherContactMP, etWellWisherContactMP, etVIPContactMP,

            etIslamicLiteratureDistributionMP, etKishorPotrikaDistributionMP, etEnglishPaperDistributionMP,
            etChatrosongbadDistributionMP, etPerspectiveDistributionMP, etPoricitiDistributionMP,
            etClassRoutineDistributionMP, etStikarCardDistributionMP,

            etMemberIncrementMP, etMemberApplicantIncrementMP, etAssociateIncrementMP, etAssociateApplicantIncrementMP,
            etWorkerIncrementMP, etSupporterIncrementMP, etWellWisherIncrementMP, etFriendIncrementMP,

            etBaitulmalBMMP, etStudentWellfareBMMP, etJarBMMP, etTableBankBMMP,
            etCriticismOtherMP, etPhysicalMiscMP, etNewspaperOtherMP,
            etMuharramaContactMiscMP, etNonMuslimContactMiscMP, etFriendOrgMiscMP;

    private CheckBox cbNamazJamaatMP, cbNofolIbadotMP;
    private ImageButton ibNext, ibPrevious;
    private Button bSave;
    private TextView tvTitle;
    private Toolbar mToolbar;

    private int cbState = 0;
    private Calendar month;
    private String monthId = "";
    private DatabaseHelper dbHelper;
    private HashMap<String, String> monthlyPlan;

    
    
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_monthly_plan);

        month = Calendar.getInstance();
        dbHelper = new DatabaseHelper(this);
        initialize();
        refreshCalendar();


        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Monthly Plan");

        ibPrevious = (ImageButton) findViewById(R.id.ibPreviousMP);
        ibPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setPreviousMonth();
                refreshCalendar();
            }
        });

        ibNext = (ImageButton) findViewById(R.id.ibNextMP);
        ibNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setNextMonth();
                refreshCalendar();
            }
        });


        bSave.setOnClickListener(this);
    }


    private void initialize(){
        //Total item 60
        etSuraNameQuranMP = ((EditText) findViewById(R.id.etSuraNameQuranMP));
        etTotalDayQuranMP = ((EditText) findViewById(R.id.etTotalDayQuranMP));
        etAverageAyatQuranMP = ((EditText) findViewById(R.id.etAverageAyatQuranMP));
        etDarseQuranMP = ((EditText) findViewById(R.id.etDarseQuranMP));
        etMemorizeQuranMP = ((EditText) findViewById(R.id.etMemorizeQuranMP));


        etBookNameHadithMP = ((EditText) findViewById(R.id.etBookNameHadithMP));
        etTotalDayHadithMP = ((EditText) findViewById(R.id.etTotalDayHadithMP));
        etAverageHadisHadithMP = ((EditText) findViewById(R.id.etAverageHadisHadithMP));
        etDarseHadithMP = ((EditText) findViewById(R.id.etDarseHadithMP));
        etMemorizeHadithMP = ((EditText) findViewById(R.id.etMemorizeHadithMP));


        etBookNameLiteratureMP = ((EditText) findViewById(R.id.etBookNameLiteratureMP));
        etTotalPageLiteratureMP = ((EditText) findViewById(R.id.etTotalPageLiteratureMP));
        etIslamicPageLiteratureMP = ((EditText) findViewById(R.id.etIslamiPageLiteratureMP));
        etOtherPageLiteratureMP = ((EditText) findViewById(R.id.etOtherPageLiteratureMP));
        etBookNoteLiteratureMP = ((EditText) findViewById(R.id.etBookNoteLiteratureMP));

        etTotalDayAcademicMP = ((EditText) findViewById(R.id.etTotalDayAcademicMP));
        etAverageHourAcademicMP = ((EditText) findViewById(R.id.etAverageHourAcademicMP));
        etPresentInClassAcademicMP = ((EditText) findViewById(R.id.etPresentInClassAcademicMP));

        cbNamazJamaatMP = (CheckBox) findViewById(R.id.cbNamazJamaatMP);
        cbNofolIbadotMP = (CheckBox) findViewById(R.id.cbNofolIbadotMP);

        etMemberContactMP = ((EditText) findViewById(R.id.etMemberContactMP));
        etAssociateContactMP = ((EditText) findViewById(R.id.etAssociateContactMP));
        etWorkerContactMP = ((EditText) findViewById(R.id.etWorkerContactMP));
        etSupporterContactMP = ((EditText) findViewById(R.id.etSupporterContactMP));
        etFriendContactMP = ((EditText) findViewById(R.id.etFriendContactMP));
        etSchoolStudentContactMP = ((EditText) findViewById(R.id.etSchoolStudentContactMP));
        etTalentStudentContactMP = ((EditText) findViewById(R.id.etTalentStudentContactMP));
        etWellWisherContactMP = ((EditText) findViewById(R.id.etWellWisherContactMP));
        etTeacherContactMP = ((EditText) findViewById(R.id.etTeacherContactMP));
        etVIPContactMP = ((EditText) findViewById(R.id.etVIPContactMP));

        etTotalDayCallOrgDutyMP = ((EditText) findViewById(R.id.etTotalDayCallOrgDutyMP));
        etTotalHourCallOrgDutyMP = ((EditText) findViewById(R.id.etTotalHourCallOrgDutyMP));
        etTotalDayOtherOrgDutyMP = ((EditText) findViewById(R.id.etTotalDayOtherOrgDutyMP));
        etAverageHourOtherOrgDutyMP = ((EditText) findViewById(R.id.etAverageHourOtherOrgDutyMP));

        etIslamicLiteratureDistributionMP = ((EditText) findViewById(R.id.etIslamicLiteratureDistributionMP));
        etKishorPotrikaDistributionMP = ((EditText) findViewById(R.id.etKishorPotrikaDistributionMP));
        etEnglishPaperDistributionMP = ((EditText) findViewById(R.id.etEnglishPaperDistributionMP));
        etChatrosongbadDistributionMP = ((EditText) findViewById(R.id.etChatrosongbadDistributionMP));
        etPerspectiveDistributionMP = ((EditText) findViewById(R.id.etPerspectiveDistributionMP));
        etPoricitiDistributionMP = ((EditText) findViewById(R.id.etPoricitiDistributionMP));
        etClassRoutineDistributionMP = ((EditText) findViewById(R.id.etClassRoutineDistributionMP));
        etStikarCardDistributionMP = ((EditText) findViewById(R.id.etStikarCardDistributionMP));


        etMemberIncrementMP = ((EditText) findViewById(R.id.etMemberIncrementMP));
        etMemberApplicantIncrementMP = ((EditText) findViewById(R.id.etMemberApplicantIncrementMP));
        etAssociateIncrementMP = ((EditText) findViewById(R.id.etAssociateIncrementMP));
        etAssociateApplicantIncrementMP = ((EditText) findViewById(R.id.etAssociateApplicantIncrementMP));
        etWorkerIncrementMP = ((EditText) findViewById(R.id.etWorkerIncrementMP));
        etSupporterIncrementMP = ((EditText) findViewById(R.id.etSupporterIncrementMP));
        etFriendIncrementMP = ((EditText) findViewById(R.id.etFriendIncrementMP));
        etWellWisherIncrementMP = ((EditText) findViewById(R.id.etWellWisherIncrementMP));

        etBaitulmalBMMP = ((EditText) findViewById(R.id.etBaitulmalBMMP));
        etStudentWellfareBMMP = ((EditText) findViewById(R.id.etStudentWellfareBMMP));
        etJarBMMP = ((EditText) findViewById(R.id.etJarBMMP));
        etTableBankBMMP = (EditText) findViewById(R.id.etTableBankBMMP);

        etCriticismOtherMP = ((EditText) findViewById(R.id.etCriticismMiscMP));
        etPhysicalMiscMP = ((EditText) findViewById(R.id.etPhysicalMiscMP));
        etNewspaperOtherMP = ((EditText) findViewById(R.id.etNewspaperMiscMP));
        etMuharramaContactMiscMP = ((EditText) findViewById(R.id.etMuharramaContactMiscMP));
        etNonMuslimContactMiscMP = ((EditText) findViewById(R.id.etNonMuslimContactMiscMP));
        etFriendOrgMiscMP = ((EditText) findViewById(R.id.etFriendOrgMiscMP));

        bSave = (Button) findViewById(R.id.bSaveMP);
    }


    private void setValueInLayoutElement() {
        
        etSuraNameQuranMP.setText(monthlyPlan.get(AllData.MP_Q_Sura_Name));
        etTotalDayQuranMP.setText(monthlyPlan.get(AllData.MP_Q_Total_day));
        etAverageAyatQuranMP.setText(monthlyPlan.get(AllData.MP_Q_Average_Ayat));
        etDarseQuranMP.setText(monthlyPlan.get(AllData.MP_Q_Darse));
        etMemorizeQuranMP.setText(monthlyPlan.get(AllData.MP_Q_Memorize_Ayat));

        etBookNameHadithMP.setText(monthlyPlan.get(AllData.MP_H_Book_Name));
        etTotalDayHadithMP.setText(monthlyPlan.get(AllData.MP_H_Total_day));
        etAverageHadisHadithMP.setText(monthlyPlan.get(AllData.MP_H_Average_Hadis));
        etDarseHadithMP.setText(monthlyPlan.get(AllData.MP_H_Darse));
        etMemorizeHadithMP.setText(monthlyPlan.get(AllData.MP_H_Memorize_Hadis));

        etBookNameLiteratureMP.setText(monthlyPlan.get(AllData.MP_L_Book_Name));
        etTotalPageLiteratureMP.setText(monthlyPlan.get(AllData.MP_L_Total_page));
        etIslamicPageLiteratureMP.setText(monthlyPlan.get(AllData.MP_L_Islamic_Page));
        etOtherPageLiteratureMP.setText(monthlyPlan.get(AllData.MP_L_Other_Page));
        etBookNoteLiteratureMP.setText(monthlyPlan.get(AllData.MP_L_Book_Note));

        etTotalDayAcademicMP.setText(monthlyPlan.get(AllData.MP_A_Total_Day));
        etAverageHourAcademicMP.setText(monthlyPlan.get(AllData.MP_A_Average_Hour));
        etPresentInClassAcademicMP.setText(monthlyPlan.get(AllData.MP_A_Total_Present_Class));


        if (monthlyPlan.get(AllData.MP_N_Jamaat) != null) {
            cbState = Integer.parseInt(monthlyPlan.get(AllData.MP_N_Jamaat));
            if (cbState == 1) {
                cbNamazJamaatMP.setChecked(true);
            } else {
                cbNamazJamaatMP.setChecked(false);
            }
        }else{
            cbNamazJamaatMP.setChecked(false);
        }

        if (monthlyPlan.get(AllData.MP_N_Nofol) != null) {
            cbState = Integer.parseInt(monthlyPlan.get(AllData.MP_N_Nofol));
            if (cbState == 1) {
                cbNofolIbadotMP.setChecked(true);
            } else {
                cbNofolIbadotMP.setChecked(false);
            }
        }else{
            cbNofolIbadotMP.setChecked(false);
        }

        etTotalDayCallOrgDutyMP.setText(monthlyPlan.get(AllData.MP_OD_C_Total_Day));
        etTotalHourCallOrgDutyMP.setText(monthlyPlan.get(AllData.MP_OD_C_Total_Hour));
        etTotalDayOtherOrgDutyMP.setText(monthlyPlan.get(AllData.MP_OD_O_Total_Day));
        etAverageHourOtherOrgDutyMP.setText(monthlyPlan.get(AllData.MP_OD_O_Average_Hour));

        etMemberContactMP.setText(monthlyPlan.get(AllData.MP_C_Member));
        etAssociateContactMP.setText(monthlyPlan.get(AllData.MP_C_Associate));
        etWorkerContactMP.setText(monthlyPlan.get(AllData.MP_C_Worker));
        etSupporterContactMP.setText(monthlyPlan.get(AllData.MP_C_Supporter));
        etFriendContactMP.setText(monthlyPlan.get(AllData.MP_C_Friend));
        etSchoolStudentContactMP.setText(monthlyPlan.get(AllData.MP_C_School_Student));
        etTalentStudentContactMP.setText(monthlyPlan.get(AllData.MP_C_Talent_Student));
        etWellWisherContactMP.setText(monthlyPlan.get(AllData.MP_C_WellWisher));
        etTeacherContactMP.setText(monthlyPlan.get(AllData.MP_C_Teacher));
        etVIPContactMP.setText(monthlyPlan.get(AllData.MP_C_VIP));


        etIslamicLiteratureDistributionMP.setText(monthlyPlan.get(AllData.MP_D_Islamic_Literature));
        etKishorPotrikaDistributionMP.setText(monthlyPlan.get(AllData.MP_D_Kishor_Potrika));
        etEnglishPaperDistributionMP.setText(monthlyPlan.get(AllData.MP_D_English_Paper));
        etChatrosongbadDistributionMP.setText(monthlyPlan.get(AllData.MP_D_Chatrosongbad));
        etPerspectiveDistributionMP.setText(monthlyPlan.get(AllData.MP_D_Perspective));
        etPoricitiDistributionMP.setText(monthlyPlan.get(AllData.MP_D_Poriciti));
        etClassRoutineDistributionMP.setText(monthlyPlan.get(AllData.MP_D_Class_Routine));
        etStikarCardDistributionMP.setText(monthlyPlan.get(AllData.MP_D_Stikar_Card));
                
        etMemberIncrementMP.setText(monthlyPlan.get(AllData.MP_I_Member));
        etMemberApplicantIncrementMP.setText(monthlyPlan.get(AllData.MP_I_Member_Applicant));
        etAssociateIncrementMP.setText(monthlyPlan.get(AllData.MP_I_Associate));
        etAssociateApplicantIncrementMP.setText(monthlyPlan.get(AllData.MP_I_Associate_Applicant));
        etWorkerIncrementMP.setText(monthlyPlan.get(AllData.MP_I_Worker));
        etSupporterIncrementMP.setText(monthlyPlan.get(AllData.MP_I_Supporter));
        etFriendIncrementMP.setText(monthlyPlan.get(AllData.MP_I_Friend));
        etWellWisherIncrementMP.setText(monthlyPlan.get(AllData.MP_I_Wellwisher));

        etBaitulmalBMMP.setText(monthlyPlan.get(AllData.MP_BM_Baitulmal));
        etStudentWellfareBMMP.setText(monthlyPlan.get(AllData.MP_BM_Student_Wellfare));
        etJarBMMP.setText(monthlyPlan.get(AllData.MP_BM_Jar));
        etTableBankBMMP.setText(monthlyPlan.get(AllData.MP_BM_Table_Bank));
      
        etCriticismOtherMP.setText(monthlyPlan.get(AllData.MP_Misc_Criticism));
        etPhysicalMiscMP.setText(monthlyPlan.get(AllData.MP_Misc_Physical));
        etNewspaperOtherMP.setText(monthlyPlan.get(AllData.MP_Misc_Newspaper));
        etMuharramaContactMiscMP.setText(monthlyPlan.get(AllData.MP_Misc_Muharram));
        etNonMuslimContactMiscMP.setText(monthlyPlan.get(AllData.MP_Misc_Non_Muslim));
        etFriendOrgMiscMP.setText(monthlyPlan.get(AllData.MP_Misc_Friend_Organization));
    }



    private void refreshCalendar() {
        tvTitle = (TextView) findViewById(R.id.tvMonthOfPlanMP);
        monthId = DateFormat.format("MMMM yyyy", month).toString();
        tvTitle.setText(monthId);
        monthlyPlan = dbHelper.getAllContentFromTableMonthlyPlan(monthId);

        makeAllElementClear();
        setValueInLayoutElement();
        bSaveOrUpdate();
    }



    private void setNextMonth() {
        if (month.get(Calendar.MONTH) == month.getActualMaximum(Calendar.MONTH)) {
            month.set(month.get(Calendar.YEAR) + 1, month.getActualMinimum(Calendar.MONTH), 1);
            return;

        } else {
            month.set(month.get(Calendar.YEAR), month.get(Calendar.MONTH) + 1, 1);
            return;
        }
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


    public void bSaveOrUpdate(){
        if (dbHelper.checkMonthlyPlanExistOrNot(monthId) == 0) {
            bSave.setText("Save");

        }else{
            bSave.setText("Update");
        }
    }

    public void makeAllElementClear() {
        
        etSuraNameQuranMP.clearFocus(); etTotalDayQuranMP.clearFocus(); etAverageAyatQuranMP.clearFocus();
        etDarseQuranMP.clearFocus(); etMemorizeQuranMP.clearFocus(); etBookNameHadithMP.clearFocus();
        etTotalDayHadithMP.clearFocus(); etAverageHadisHadithMP.clearFocus(); etDarseHadithMP.clearFocus();
        etMemorizeHadithMP.clearFocus(); etBookNameLiteratureMP.clearFocus(); etTotalPageLiteratureMP.clearFocus();
        etIslamicPageLiteratureMP.clearFocus(); etOtherPageLiteratureMP.clearFocus(); etBookNoteLiteratureMP.clearFocus();

        etTotalDayAcademicMP.clearFocus(); etAverageHourAcademicMP.clearFocus(); etPresentInClassAcademicMP.clearFocus();
        cbNamazJamaatMP.clearFocus(); cbNofolIbadotMP.clearFocus(); etTotalDayCallOrgDutyMP.clearFocus();
        etTotalHourCallOrgDutyMP.clearFocus(); etTotalDayOtherOrgDutyMP.clearFocus(); etAverageHourOtherOrgDutyMP.clearFocus();

        etMemberContactMP.clearFocus(); etAssociateContactMP.clearFocus(); etWorkerContactMP.clearFocus();
        etFriendContactMP.clearFocus(); etSupporterContactMP.clearFocus(); etSchoolStudentContactMP.clearFocus();
        etTalentStudentContactMP.clearFocus(); etTeacherContactMP.clearFocus(); etVIPContactMP.clearFocus();
        etWellWisherContactMP.clearFocus();

        etIslamicLiteratureDistributionMP.clearFocus(); etKishorPotrikaDistributionMP.clearFocus(); etEnglishPaperDistributionMP.clearFocus();
        etChatrosongbadDistributionMP.clearFocus(); etPerspectiveDistributionMP.clearFocus(); etPoricitiDistributionMP.clearFocus();
        etClassRoutineDistributionMP.clearFocus(); etStikarCardDistributionMP.clearFocus(); etMemberIncrementMP.clearFocus();
        etMemberApplicantIncrementMP.clearFocus(); etAssociateIncrementMP.clearFocus(); etAssociateApplicantIncrementMP.clearFocus();
        etWorkerIncrementMP.clearFocus(); etSupporterIncrementMP.clearFocus(); etFriendIncrementMP.clearFocus();
        etWellWisherIncrementMP.clearFocus();

        etBaitulmalBMMP.clearFocus(); etStudentWellfareBMMP.clearFocus(); etJarBMMP.clearFocus();
        etTableBankBMMP.clearFocus(); etCriticismOtherMP.clearFocus(); etPhysicalMiscMP.clearFocus();
        etNewspaperOtherMP.clearFocus(); etMuharramaContactMiscMP.clearFocus(); etNonMuslimContactMiscMP.clearFocus();
        etFriendOrgMiscMP.clearFocus();
    }


    public void onClick(View view) {
                    HashMap<String , String> hashmap = new HashMap();

                    hashmap.put(AllData.MP_Month_Id,              monthId);
                    hashmap.put(AllData.MP_Q_Sura_Name,           etSuraNameQuranMP.getText().toString());
                    hashmap.put(AllData.MP_Q_Total_day,           etTotalDayQuranMP.getText().toString());
                    hashmap.put(AllData.MP_Q_Average_Ayat,        etAverageAyatQuranMP.getText().toString());
                    hashmap.put(AllData.MP_Q_Darse,               etDarseQuranMP.getText().toString());
                    hashmap.put(AllData.MP_Q_Memorize_Ayat,       etMemorizeQuranMP.getText().toString());

                    hashmap.put(AllData.MP_H_Book_Name,           etBookNameHadithMP.getText().toString());
                    hashmap.put(AllData.MP_H_Total_day,           etTotalDayHadithMP.getText().toString());
                    hashmap.put(AllData.MP_H_Average_Hadis,       etAverageHadisHadithMP.getText().toString());
                    hashmap.put(AllData.MP_H_Darse,               etDarseHadithMP.getText().toString());
                    hashmap.put(AllData.MP_H_Memorize_Hadis,      etMemorizeHadithMP.getText().toString());

                    hashmap.put(AllData.MP_L_Book_Name,           etBookNameLiteratureMP.getText().toString());
                    hashmap.put(AllData.MP_L_Total_page,          etTotalPageLiteratureMP.getText().toString());
                    hashmap.put(AllData.MP_L_Islamic_Page,        etIslamicPageLiteratureMP.getText().toString());
                    hashmap.put(AllData.MP_L_Other_Page,          etOtherPageLiteratureMP.getText().toString());
                    hashmap.put(AllData.MP_L_Book_Note,           etBookNoteLiteratureMP.getText().toString());

                    hashmap.put(AllData.MP_A_Total_Day,           etTotalDayAcademicMP.getText().toString());
                    hashmap.put(AllData.MP_A_Average_Hour,        etAverageHourAcademicMP.getText().toString());
                    hashmap.put(AllData.MP_A_Total_Present_Class, etPresentInClassAcademicMP.getText().toString());

                    if (cbNamazJamaatMP.isChecked()) {
                        hashmap.put(AllData.MP_N_Jamaat, "1");
                    } else {
                        hashmap.put(AllData.MP_N_Jamaat, "0");
                    }

                    if (cbNofolIbadotMP.isChecked()) {
                        hashmap.put(AllData.MP_N_Nofol, "1");
                    } else {
                        hashmap.put(AllData.MP_N_Nofol, "0");
                    }

                    hashmap.put(AllData.MP_OD_C_Total_Day,        etTotalDayCallOrgDutyMP.getText().toString());
                    hashmap.put(AllData.MP_OD_C_Total_Hour,       etTotalHourCallOrgDutyMP.getText().toString());
                    hashmap.put(AllData.MP_OD_O_Total_Day,        etTotalDayOtherOrgDutyMP.getText().toString());
                    hashmap.put(AllData.MP_OD_O_Average_Hour,     etAverageHourOtherOrgDutyMP.getText().toString());

                    hashmap.put(AllData.MP_C_Member,              etMemberContactMP.getText().toString());
                    hashmap.put(AllData.MP_C_Associate,           etAssociateContactMP.getText().toString());
                    hashmap.put(AllData.MP_C_Worker,              etWorkerContactMP.getText().toString());
                    hashmap.put(AllData.MP_C_Supporter,           etSupporterContactMP.getText().toString());
                    hashmap.put(AllData.MP_C_Friend,              etFriendContactMP.getText().toString());
                    hashmap.put(AllData.MP_C_School_Student,      etSchoolStudentContactMP.getText().toString());
                    hashmap.put(AllData.MP_C_Talent_Student,      etTalentStudentContactMP.getText().toString());
                    hashmap.put(AllData.MP_C_Teacher,             etTeacherContactMP.getText().toString());
                    hashmap.put(AllData.MP_C_VIP,                 etVIPContactMP.getText().toString());
                    hashmap.put(AllData.MP_C_WellWisher,          etWellWisherContactMP.getText().toString());

                    hashmap.put(AllData.MP_D_Islamic_Literature,  etIslamicLiteratureDistributionMP.getText().toString());
                    hashmap.put(AllData.MP_D_Kishor_Potrika,      etKishorPotrikaDistributionMP.getText().toString());
                    hashmap.put(AllData.MP_D_English_Paper,       etEnglishPaperDistributionMP.getText().toString());
                    hashmap.put(AllData.MP_D_Chatrosongbad,       etChatrosongbadDistributionMP.getText().toString());
                    hashmap.put(AllData.MP_D_Perspective,         etPerspectiveDistributionMP.getText().toString());
                    hashmap.put(AllData.MP_D_Poriciti,            etPoricitiDistributionMP.getText().toString());
                    hashmap.put(AllData.MP_D_Class_Routine,       etClassRoutineDistributionMP.getText().toString());
                    hashmap.put(AllData.MP_D_Stikar_Card,         etStikarCardDistributionMP.getText().toString());

                    hashmap.put(AllData.MP_I_Member,              etMemberIncrementMP.getText().toString());
                    hashmap.put(AllData.MP_I_Member_Applicant,    etMemberApplicantIncrementMP.getText().toString());
                    hashmap.put(AllData.MP_I_Associate,           etAssociateIncrementMP.getText().toString());
                    hashmap.put(AllData.MP_I_Associate_Applicant, etAssociateApplicantIncrementMP.getText().toString());
                    hashmap.put(AllData.MP_I_Worker,              etWorkerIncrementMP.getText().toString());
                    hashmap.put(AllData.MP_I_Supporter,           etSupporterIncrementMP.getText().toString());
                    hashmap.put(AllData.MP_I_Friend,              etFriendIncrementMP.getText().toString());
                    hashmap.put(AllData.MP_I_Wellwisher,          etWellWisherIncrementMP.getText().toString());


                    hashmap.put(AllData.MP_BM_Baitulmal,          etBaitulmalBMMP.getText().toString());
                    hashmap.put(AllData.MP_BM_Student_Wellfare,   etStudentWellfareBMMP.getText().toString());
                    hashmap.put(AllData.MP_BM_Jar,                etJarBMMP.getText().toString());
                    hashmap.put(AllData.MP_BM_Table_Bank,         etTableBankBMMP.getText().toString());

                    hashmap.put(AllData.MP_Misc_Criticism,        etCriticismOtherMP.getText().toString());
                    hashmap.put(AllData.MP_Misc_Physical,         etPhysicalMiscMP.getText().toString());
                    hashmap.put(AllData.MP_Misc_Newspaper,        etNewspaperOtherMP.getText().toString());
                    hashmap.put(AllData.MP_Misc_Muharram,         etMuharramaContactMiscMP.getText().toString());
                    hashmap.put(AllData.MP_Misc_Non_Muslim,       etNonMuslimContactMiscMP.getText().toString());
                    hashmap.put(AllData.MP_Misc_Friend_Organization, etFriendOrgMiscMP.getText().toString());

                    if (dbHelper.checkMonthlyPlanExistOrNot(monthId) == 0) {
                        Toast.makeText(this, "Your plan is saved successfully !!", Toast.LENGTH_SHORT).show();
                        dbHelper.insertRowInTableMonthlyPlan(hashmap);
                        return;

                    } else {
                        dbHelper.updateRowInTableMonthlyPlan(hashmap);
                        Toast.makeText(this, "Your plan is updated successfully !!", Toast.LENGTH_SHORT).show();
                        return;
                    }
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
}
