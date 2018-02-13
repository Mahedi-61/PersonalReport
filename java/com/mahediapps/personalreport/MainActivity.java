package com.mahediapps.personalreport;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.mahediapps.adapter.NavigationDrawerCallbacks;
import com.mahediapps.model.DatabaseHelper;


public class MainActivity extends ActionBarActivity implements NavigationDrawerCallbacks, View.OnClickListener {

    private EditText  etQuranStudyDR, etHadithStudyDR, etIslalmiLiteratureDR, etOtherLiteratureDR,
            etAcademicStudyDR, etJamaatNamazDR, etKajjaNamazDR, etCallOrgDutyDR, etOtherWorkOrgDutyDR,
            etMemberContactDR, etAssociateContactDR, etFriendContactDR,
            etSupporterContactDR, etWorkerContactDR, etBookDistributionDR,
            etTalentStudentContactDR, etWellWisherContactDR;

    private CheckBox cbPresentClassDR, cbCriticismMiscDR, cbPhysicalExerciseMiscDR, cbNewspaperMiscDR;

    private TextView tvTitle;
    private ImageButton ibNext, ibPrevious;
    private Button bSave;

    private NavigationDrawerFragment mNavigationDrawerFragment;

    private SharedPreferences profile;
    private Toolbar mToolbar;
    private DatabaseHelper dbHelper;

    private Calendar today, month;
    private String dayId = "", monthId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        profile = getSharedPreferences("profile", 0);
        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        mNavigationDrawerFragment.setUserData(profile.getString("user_name", ""), profile.getString("email_address", ""),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));


        dbHelper = new DatabaseHelper(this);
        today = Calendar.getInstance();
        month = Calendar.getInstance();

        initialize();
        refreshCalendar();

        ibPrevious = (ImageButton) findViewById(R.id.ibPreviousDR);
        ibNext = (ImageButton) findViewById(R.id.ibNextDR);
        bSave = (Button) findViewById(R.id.bSaveDD);

        ibPrevious.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View view) {
                setPreviousDay();
                refreshCalendar();
            }

        });
        ibNext.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View view) {
                if (setNextDay()) {
                    refreshCalendar();
                }
            }

        });

        bSave.setOnClickListener(this);
    }


    private void initialize() {


        etQuranStudyDR = (EditText) findViewById(R.id.etQuranStudyDR);
        etHadithStudyDR = (EditText) findViewById(R.id.etHadithStudyDR);
        etIslalmiLiteratureDR = (EditText) findViewById(R.id.etIslalmiLiteratureDR);
        etOtherLiteratureDR = (EditText) findViewById(R.id.etOtherLiteratureDR);
        etAcademicStudyDR = (EditText) findViewById(R.id.etAcademicStudyDR);
        cbPresentClassDR = (CheckBox) findViewById(R.id.cbPresentClassDR);

        etJamaatNamazDR = (EditText) findViewById(R.id.etJamaatNamazDR);
        etKajjaNamazDR = (EditText) findViewById(R.id.etKajjaNamazDR);

        etMemberContactDR = (EditText) findViewById(R.id.etMemberContactDR);
        etAssociateContactDR = (EditText) findViewById(R.id.etAssociateContactDR);
        etWorkerContactDR = (EditText) findViewById(R.id.etWorkerContactDR);
        etSupporterContactDR = (EditText) findViewById(R.id.etSupporterContactDR);
        etFriendContactDR = (EditText) findViewById(R.id.etFriendContactDR);
        etBookDistributionDR = (EditText) findViewById(R.id.etBookDistributionDR);
        etTalentStudentContactDR = (EditText) findViewById(R.id.etTalentStudentContactDR);
        etWellWisherContactDR = (EditText) findViewById(R.id.etWellWisherContactDR);

        etCallOrgDutyDR = ((EditText) findViewById(R.id.etCallOrgDutyDR));
        etOtherWorkOrgDutyDR = ((EditText) findViewById(R.id.etOtherWorkOrgDutyDR));

        cbCriticismMiscDR = ((CheckBox) findViewById(R.id.cbCriticismMiscDR));
        cbPhysicalExerciseMiscDR = ((CheckBox) findViewById(R.id.cbPhysicalExerciseMiscDR));
        cbNewspaperMiscDR = ((CheckBox) findViewById(R.id.cbNewspaperMiscDR));
    }


    private void setValueInLayoutElement() {
        HashMap<String, String> hashmap = dbHelper.getDailyReportFromDatabase(dayId);
        int cbState = 0;

        
        etQuranStudyDR.setText(hashmap.get("dr_study_quran"));
        etHadithStudyDR.setText(hashmap.get("dr_study_hadith"));
        etIslalmiLiteratureDR.setText(hashmap.get("dr_study_literature_is"));
        etOtherLiteratureDR.setText(hashmap.get("dr_study_literature_other"));
        etAcademicStudyDR.setText(hashmap.get("dr_study_academic"));

        etJamaatNamazDR.setText(hashmap.get("dr_namaz_jamaat"));
        etKajjaNamazDR.setText(hashmap.get("dr_namaz_kajja"));

        etCallOrgDutyDR.setText(hashmap.get("dr_duty_call"));
        etOtherWorkOrgDutyDR.setText(hashmap.get("dr_duty_other"));

        etMemberContactDR.setText(hashmap.get("dr_contact_member"));
        etAssociateContactDR.setText(hashmap.get("dr_contact_associate"));
        etWorkerContactDR.setText(hashmap.get("dr_contact_worker"));
        etSupporterContactDR.setText(hashmap.get("dr_contact_supporter"));
        etFriendContactDR.setText(hashmap.get("dr_contact_friend"));
        etBookDistributionDR.setText(hashmap.get("dr_contact_book"));
        etTalentStudentContactDR.setText(hashmap.get("dr_contact_talent_student"));
        etWellWisherContactDR.setText(hashmap.get("dr_contact_wellwisher"));

        if (hashmap.get("dr_class") != null) {
            cbState = Integer.parseInt(hashmap.get("dr_class"));
            if (cbState == 1) {
                cbPresentClassDR.setChecked(true);
            } else {
                cbPresentClassDR.setChecked(false);
            }
        } else {
            cbPresentClassDR.setChecked(false);
        }
        

        if (hashmap.get("dr_criticism") != null) {
            cbState = Integer.parseInt(hashmap.get("dr_criticism"));
            if (cbState == 1) {
                cbCriticismMiscDR.setChecked(true);
            } else {
                cbCriticismMiscDR.setChecked(false);
            }
        } else {
            cbCriticismMiscDR.setChecked(false);
        }

        if (hashmap.get("dr_physical_exercise") != null) {
            cbState = Integer.parseInt(hashmap.get("dr_physical_exercise"));
            if (cbState == 1) {
                cbPhysicalExerciseMiscDR.setChecked(true);
            } else {
                cbPhysicalExerciseMiscDR.setChecked(false);
            }
        } else {
            cbPhysicalExerciseMiscDR.setChecked(false);
        }


        if (hashmap.get("dr_newspaper") != null) {
            cbState = Integer.parseInt(hashmap.get("dr_newspaper"));
            if (cbState == 1) {
                cbNewspaperMiscDR.setChecked(true);
            } else {
                cbNewspaperMiscDR.setChecked(false);
            }
        } else {
            cbNewspaperMiscDR.setChecked(false);
        }
    }
    
    
    private void clearFocusFromAllEditext(){

        etQuranStudyDR.clearFocus();
        etHadithStudyDR.clearFocus();
        etIslalmiLiteratureDR.clearFocus();
        etOtherLiteratureDR.clearFocus();
        
        etJamaatNamazDR.clearFocus();
        etKajjaNamazDR.clearFocus();
        etCallOrgDutyDR.clearFocus();
        etOtherWorkOrgDutyDR.clearFocus();
        
        etMemberContactDR.clearFocus();
        etAssociateContactDR.clearFocus();
        etWorkerContactDR.clearFocus();
        etSupporterContactDR.clearFocus();
        etFriendContactDR.clearFocus();
        etBookDistributionDR.clearFocus();
        etTalentStudentContactDR.clearFocus();
        etWellWisherContactDR.clearFocus();
    }


    private void refreshCalendar() {
        tvTitle = (TextView) findViewById(R.id.tvTitleDR);
        dayId = DateFormat.format("EE, dd MMMM yyyy", month).toString();
        monthId = DateFormat.format("MMMM yyyy", month).toString();
        tvTitle.setText(dayId);
        setValueInLayoutElement();
        clearFocusFromAllEditext();
    }


    private boolean setNextDay() {
        if (month.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                month.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                month.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {

            Toast.makeText(this, "You can't keep your report later than today !", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (month.get(Calendar.DAY_OF_MONTH) == month.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            if (month.get(Calendar.MONTH) == month.getActualMaximum(Calendar.MONTH)) {
                month.set(month.get(Calendar.YEAR) + 1, month.getActualMinimum(Calendar.MONTH), 1);

            } else {
                month.set(month.get(Calendar.YEAR), month.get(Calendar.MONTH) + 1, 1);
            }
        } else {
            month.add(Calendar.DAY_OF_MONTH, 1);
        }
        return true;
    }


    private void setPreviousDay() {
        if (month.get(Calendar.DAY_OF_MONTH) == month.getActualMinimum(Calendar.DAY_OF_MONTH)) {
            if (month.get(Calendar.MONTH) == month.getActualMinimum(Calendar.MONTH)) {
                month.set(month.get(Calendar.YEAR) - 1, month.getActualMaximum(Calendar.MONTH), 31);
                return;

            } else {
                month.add(Calendar.MONTH, -1);
                month.set(month.get(Calendar.YEAR), month.get(Calendar.MONTH), month.getActualMaximum(Calendar.DAY_OF_MONTH));
                return;
            }
        } else {
            month.add(Calendar.DAY_OF_MONTH, -1);
            return;
        }
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {

        switch (position) {
            case 0: {
                startActivity(new Intent(this, DailyReport.class));
                break;
            }
            case 1: {
                startActivity(new Intent(this, MonthlyReport.class));
                break;
            }
            case 2: {
                startActivity(new Intent(this, MonthlyPlan.class));
                break;
            }
            case 3: {
                startActivity(new Intent(this, StatisticalAnalysis.class));
                break;
            }
            case 4: {
                startActivity(new Intent(this, SearchReport.class));
                break;
            }
            case 5: {
                startActivity(new Intent(this, Settings.class));
                break;
            }

            case 6: {
                startActivity(new Intent(this, Help.class));
                break;
            }

            case 7: {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,  Uri.parse("market://search?q=pub:Shibir")));

                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/search?q=pub:Shibir")));
                }
                break;
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else {

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle("Exit Personal Report");
            builder.setMessage("Are you sure to exit ?");
            builder.setPositiveButton("Yes", new android.content.DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialoginterface, int i) {
                    dbHelper.close();
                    finish();
                }

            });

            builder.setNegativeButton("No", new android.content.DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialoginterface, int i) {
                }
            });
            builder.show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       /* if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }*/
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        HashMap<String, String> dailyDairy = new HashMap<>();
        dailyDairy.put("day_id", dayId);
        dailyDairy.put("month_id", monthId);

        dailyDairy.put("dr_study_quran", etQuranStudyDR.getText().toString());
        dailyDairy.put("dr_study_hadith", etHadithStudyDR.getText().toString());
        dailyDairy.put("dr_study_literature_is", etIslalmiLiteratureDR.getText().toString());
        dailyDairy.put("dr_study_literature_other", etOtherLiteratureDR.getText().toString());
        dailyDairy.put("dr_study_academic", etAcademicStudyDR.getText().toString());

        dailyDairy.put("dr_namaz_jamaat", etJamaatNamazDR.getText().toString());
        dailyDairy.put("dr_namaz_kajja", etKajjaNamazDR.getText().toString());

        dailyDairy.put("dr_duty_call", etCallOrgDutyDR.getText().toString());
        dailyDairy.put("dr_duty_other", etOtherWorkOrgDutyDR.getText().toString());
        

        dailyDairy.put("dr_contact_member", etMemberContactDR.getText().toString());
        dailyDairy.put("dr_contact_associate", etAssociateContactDR.getText().toString());
        dailyDairy.put("dr_contact_worker", etWorkerContactDR.getText().toString());
        dailyDairy.put("dr_contact_supporter", etSupporterContactDR.getText().toString());
        dailyDairy.put("dr_contact_friend", etFriendContactDR.getText().toString());
        dailyDairy.put("dr_contact_book", etBookDistributionDR.getText().toString());
        dailyDairy.put("dr_contact_talent_student", etTalentStudentContactDR.getText().toString());
        dailyDairy.put("dr_contact_wellwisher", etWellWisherContactDR.getText().toString());

        if (cbPresentClassDR.isChecked()) {
            dailyDairy.put("dr_class", "1");
        } else {
            dailyDairy.put("dr_class", "0");
        }
        

        if (cbCriticismMiscDR.isChecked()) {
            dailyDairy.put("dr_criticism", "1");

        } else {
            dailyDairy.put("dr_criticism", "0");
        }

        if (cbPhysicalExerciseMiscDR.isChecked()) {
            dailyDairy.put("dr_physical_exercise", "1");

        } else {
            dailyDairy.put("dr_physical_exercise", "0");
        }

        if (cbNewspaperMiscDR.isChecked()) {
            dailyDairy.put("dr_newspaper", "1");
        } else {
            dailyDairy.put("dr_newspaper", "0");
        }

        if (dbHelper.getIdFromDayId(dayId) == 0) {
            Toast.makeText(this, "Your report is saved", Toast.LENGTH_SHORT).show();
            dbHelper.insertRowInTableDailyReport(dailyDairy);
            return;

        } else {
            dailyDairy.put("_id", String.valueOf(dbHelper.getIdFromDayId(dayId)));
            Toast.makeText(this, "Your report is updated", Toast.LENGTH_SHORT).show();
            dbHelper.updateRowInTableDailyReport(dailyDairy);
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (getIntent().hasExtra("day_id")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EE, dd MMMM yyyy");
            try {
                Date date = dateFormat.parse(getIntent().getStringExtra("day_id"));
                month.setTime(date);
                refreshCalendar();

            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
        }

    }

}
