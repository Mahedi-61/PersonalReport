package com.mahediapps.personalreport;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mahediapps.model.DatabaseHelper;


public class LoginActivity extends Activity implements View.OnClickListener {

    private Button bLogin;
    private DatabaseHelper dbHelper;
    private CheckBox cbRememberPassword;
    private EditText etPassword;
    private String givenPassword, savePassword;
    private String mobileNumber;
    private Intent intent;
    private SharedPreferences profile;
    private TextView tvForgotPassword;


    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_login);
        initialize();

        profile = getSharedPreferences("profile", 0);
        savePassword = profile.getString("login_password", "");
        intent = new Intent(this, MainActivity.class);

        Typeface myTypeface = Typeface.createFromAsset(this.getAssets(), "fonts/CARDIF__.TTF");
        TextView title = (TextView) findViewById(R.id.tvAppNameLA);
        title.setTypeface(myTypeface);
        title.setText("Personal Report");

        if (profile.getInt("first_time", 0) == 0) {
            startActivity(new Intent(this, CreateAccount.class));
        }

        LongOperation l = new LongOperation();
        (l).execute();

        bLogin.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);

        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginButtonFunction();
                }
                return false;
            }
        });

        if (profile.getInt("password_checked", 0) == 1) {
            cbRememberPassword.setChecked(true);
            etPassword.setText(savePassword);
        }
    }


    private void initialize() {
        etPassword = (EditText) findViewById(R.id.etPasswordLA);
        etPassword.setGravity(Gravity.CENTER);
        bLogin = (Button) findViewById(R.id.bLoginLA);
        tvForgotPassword = (TextView) findViewById(R.id.tvForgotPasswordLA);
        cbRememberPassword = (CheckBox) findViewById(R.id.cbRemeberPasswordLA);
    }


    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.bLoginLA: {
                loginButtonFunction();
                break;
            }

            case R.id.tvForgotPasswordLA: {
                mobileNumber = profile.getString("mobile_number", "");
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                builder.setTitle("Sms backup password");
                builder.setMessage("Are you sure to proceed ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialoginterface, int i) {
                        try {
                            SmsManager.getDefault().sendTextMessage(mobileNumber, null,
                                    (new StringBuilder("Your Personal Report App Login Password: "))
                                            .append(savePassword).toString(), null, null);

                            Toast.makeText(getApplicationContext(), "SMS send successfully !!", Toast.LENGTH_SHORT).show();

                            return;

                        } catch (Exception ex) {
                            Toast.makeText(getApplicationContext(), "SMS faild to send !!", Toast.LENGTH_SHORT).show();
                        }


                    }

                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {

                    }

                });
                builder.show();
                return;
            }
        }
    }


    protected void onPause() {
        super.onPause();
        finish();
    }


    private class LongOperation extends AsyncTask<Void, Void, Integer> {

        protected Integer doInBackground(Void... v) {
            dbHelper = new DatabaseHelper(getApplicationContext());
            try {
                dbHelper.createDataBase();
            } catch (Exception ex) {
                Log.e("Allah help me", "database copy hoi ni ? ");
                throw new Error("Unable to create database");
            }
            dbHelper.openDataBase();
            return 1;
        }


        protected void onPostExecute(Integer result) {
            dbHelper.close();
        }

    }


    private void loginButtonFunction() {
        givenPassword = etPassword.getText().toString();

        if (savePassword.equals(givenPassword)) {
            SharedPreferences.Editor edit = profile.edit();

            if (cbRememberPassword.isChecked()) {
                edit.putInt("password_checked", 1);
            } else {
                edit.putInt("password_checked", 0);
            }
            edit.commit();
            startActivity(intent);
        } else {
            Toast.makeText(this, "Wrong Password. Try Again !!", Toast.LENGTH_SHORT).show();
        }
    }
}
