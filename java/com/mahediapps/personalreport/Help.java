package com.mahediapps.personalreport;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;


public class Help extends ActionBarActivity {

    private Toolbar mToolbar;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_individual_about);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView tvAboutContent = (TextView) findViewById(R.id.tvAboutContents);

        getSupportActionBar().setTitle("Help");
        tvAboutContent.setText(Html.fromHtml(getResources().getString(R.string.about_this_app)));

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
