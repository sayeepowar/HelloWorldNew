package com.sayaanand.helloworld;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.sayaanand.helloworld.activities.fragments.DatePickerFragment;
import com.sayaanand.helloworld.emi.EMICalculator;
import com.sayaanand.helloworld.utils.Constants;
import com.sayaanand.helloworld.utils.LoggerUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends FragmentActivity implements DatePickerFragment.OnFragmentInteractionListener {

    private int year;
    private int month;
    private int day;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");

    static final int DATE_DIALOG_ID = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        setCurrentDateOnView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void setCurrentDateOnView() {

        EditText tvDisplayDate = (EditText) findViewById(R.id.emidate_ip_val);
        DatePicker dpResult = (DatePicker) findViewById(R.id.dpResult);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        String date = sdf.format(c.getTime());

        // set current date into textview
        tvDisplayDate.setText(date);

        // set current date into datepicker
        dpResult.init(year, month, day, null);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void sendMessage(View view) {
        Intent intent = new Intent(this, EMICalculatorActivity.class);
        EditText principal = (EditText)findViewById(R.id.principal_ip_num);
        EditText interest = (EditText)findViewById(R.id.interst_ip_num);
        EditText tenor = (EditText)findViewById(R.id.tenor_ip_num);
        EditText emiDate = (EditText) findViewById(R.id.emidate_ip_val);

        Double p = Double.parseDouble(principal.getText().toString());
        Double n = Double.parseDouble(tenor.getText().toString());
        Double r = Double.parseDouble(interest.getText().toString());


        intent.putExtra(Constants.PRINCIPAL, p);
        intent.putExtra(Constants.TENOR, n);
        intent.putExtra(Constants.INTEREST, r);
        intent.putExtra(Constants.EMI_DATE, emiDate.getText().toString());

        startActivity(intent);
    }

    public void showDatePickerDialog(View v) {
        /*DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");*/
        showDialog(DATE_DIALOG_ID);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener, year, month,
                        day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            EditText tvDisplayDate = (EditText) findViewById(R.id.emidate_ip_val);

            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);
            // set selected date into textview
            tvDisplayDate.setText(sdf.format(c.getTime()));

            // set selected date into datepicker also
            DatePicker dpResult = (DatePicker) findViewById(R.id.dpResult);
            dpResult.init(year, month, day, null);

        }
    };


    @Override
    public void onFragmentInteraction(Uri uri) {
        LoggerUtils.logInfo("OnFragmentInteractionListener called..");
    }
}
