package com.mahediapps.personalreport;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.Calendar;

import com.mahediapps.model.AdapterForSearch;
import com.mahediapps.model.AllData;

public class SearchReport extends ActionBarActivity implements AdapterView.OnItemClickListener {


    private String dayNumber, monthId;
    private Toolbar mToolbar;
    private static Calendar selectedDate, today;


    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_search);


        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Search Report");

        selectedDate = Calendar.getInstance();
        today = Calendar.getInstance();
        ListView lvSearchList = (ListView) findViewById(R.id.lvSearchList);
        lvSearchList.setAdapter(new AdapterForSearch(this, AllData.searchList));
        lvSearchList.setOnItemClickListener(this);
    }


    public void onItemClick(final AdapterView adapterView, View view, int i, long l) {
        if (i == 0) {
            showDatePickerDialog();
        }
        if (i == 1) {
            showDatePickerDialogWithoutDay();
        }
        if (i == 2) {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dl_enter_last_days);
            dialog.setTitle("Enter Total Days..!!");

            final EditText etLastDaysSearch = (EditText) dialog.findViewById(R.id.etLastDaysSearch);
            final Button bCancel = (Button) dialog.findViewById(R.id.dl_bCancelEnterLastDaysSearch);
            final Button bSave = (Button) dialog.findViewById(R.id.dl_bSaveEnterLastDaysSearch);

            bSave.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    dayNumber = etLastDaysSearch.getText().toString();

                    if (dayNumber.equals("") || dayNumber.equals("0")) {
                        Toast.makeText(SearchReport.this, "Please Enter a valid number", Toast.LENGTH_SHORT).show();
                        return;

                    } else {
                        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(bSave.getWindowToken(), 0);
                        dialog.dismiss();

                        Intent intent = new Intent(SearchReport.this, SearchReportByPreviousDays.class);
                        intent.putExtra("last_days", dayNumber);
                        startActivity(intent);
                        return;
                    }
                }
            });
            bCancel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view1) {
                    dialog.dismiss();
                }

            });
            dialog.show();
        }
    }


    public void showDatePickerDialog() {
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getFragmentManager(), "datePicker");
    }

    public void showDatePickerDialogWithoutDay() {
        DialogFragment monthFragment = new MonthYearPickerDialog();
        monthFragment.show(getFragmentManager(), "mDatePicker");
    }


    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            selectedDate.set(year, month, day);

            if (selectedDate.before(today)) {

                Intent intent = new Intent(getActivity(), MainActivity.class);
                String dayId = DateFormat.format("EE, dd MMMM yyyy", selectedDate).toString();
                intent.putExtra("day_id", dayId);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();

            } else {
                Toast.makeText(getActivity(), "Only previous report can be seen !!", Toast.LENGTH_SHORT).show();

            }


        }
    }


    //custom date picker dialog
    public static class MonthYearPickerDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            Calendar cal = Calendar.getInstance();

            View dialog = getActivity().getLayoutInflater().inflate(R.layout.dl_date_picker, null);
            final NumberPicker monthPicker = (NumberPicker) dialog.findViewById(R.id.picker_month);
            final NumberPicker yearPicker = (NumberPicker) dialog.findViewById(R.id.picker_year);

            monthPicker.setMinValue(1);
            monthPicker.setMaxValue(12);
            monthPicker.setValue(cal.get(Calendar.MONTH) + 1);

            int year = cal.get(Calendar.YEAR);
            yearPicker.setMinValue(2000);
            yearPicker.setMaxValue(2099);
            yearPicker.setValue(year);

            builder.setTitle("Search By Month");
            builder.setView(dialog)
                    // Add action buttons
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            selectedDate.set(Calendar.YEAR, yearPicker.getValue());
                            selectedDate.set(Calendar.MONTH, monthPicker.getValue() - 1);
                            selectedDate.set(Calendar.DAY_OF_MONTH, 1);

                            if (selectedDate.before(today)) {
                                Intent intent = new Intent(getActivity(), MonthlyReport.class);
                                String monthId = DateFormat.format("MMMM yyyy", selectedDate).toString();
                                intent.putExtra("month_id", monthId);
                                startActivity(intent);
                                return;
                            }else{
                                Toast.makeText(getActivity(), "Only previous report can be seen !!",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MonthYearPickerDialog.this.getDialog().cancel();
                        }
                    });
            return builder.create();
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
