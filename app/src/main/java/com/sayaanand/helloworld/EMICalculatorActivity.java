package com.sayaanand.helloworld;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sayaanand.helloworld.activities.fragments.EMIDetailsFragment;
import com.sayaanand.helloworld.activities.fragments.dummy.DummyContent;
import com.sayaanand.helloworld.emi.EMICalculator;
import com.sayaanand.helloworld.emi.vo.EMIDetails;
import com.sayaanand.helloworld.utils.Constants;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EMICalculatorActivity extends AppCompatActivity implements EMIDetailsFragment.OnListFragmentInteractionListener {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emicalculator1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Intent intent = getIntent();

        double p = intent.getDoubleExtra(Constants.PRINCIPAL, 0.0);
        double r = intent.getDoubleExtra(Constants.INTEREST, 0.0);
        double n = intent.getDoubleExtra(Constants.TENOR, 0.0);
        String emiDateStr = intent.getStringExtra(Constants.EMI_DATE);

        Date emitDate = null;
        try {
            emitDate = sdf.parse(emiDateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        EMICalculator emiCalculator = new EMICalculator();
        Double emi = emiCalculator.calculate(p, r, n);

        DecimalFormat fmt = new DecimalFormat("#,###.##");
        String val = fmt.format(emi);

        TextView textView = (TextView) findViewById(R.id.emiValue);
        textView.setText(val);
        textView.refreshDrawableState();

        List<EMIDetails> emiDetails = emiCalculator.getEMIDetails(p, r, n, emitDate);

        getBarChart(emiDetails);

        LineChart lineChart = (LineChart) findViewById(R.id.lineChart);
        LineData lineData = new LineData(getXAxisValues(emiDetails), getLineDataSet(emiDetails));
        lineChart.setData(lineData);
        lineChart.setDescription("EMI");
        lineChart.animateXY(2000, 2000);
        lineChart.invalidate();

    }

    private void getBarChart(List<EMIDetails> emiDetails) {
        // programmatically create a LineChart
        BarChart chart = (BarChart) findViewById(R.id.sampleBarChart);
        BarData data = new BarData(getXAxisValues(emiDetails), getDataSet(emiDetails));
        chart.setData(data);
        chart.setDescription("EMI");
        chart.animateXY(2000, 2000);
        chart.invalidate();
    }

    private ArrayList<LineDataSet> getLineDataSet(List<EMIDetails> emiDetails) {
        ArrayList<LineDataSet> dataSets = null;

        ArrayList<Entry> valueSet1 = new ArrayList<>();
        ArrayList<Entry> valueSet2 = new ArrayList<>();
        ArrayList<Entry> valueSet3 = new ArrayList<>();

        int counter = 0;
        float totalPrincipal = 0.0f, totalInterest = 0.0f, totalTotal = 0.0f;
        for (EMIDetails emiDetail : emiDetails) {
            totalPrincipal += emiDetail.getPrincipal().floatValue();
            totalInterest += emiDetail.getInterest().floatValue();
            totalTotal += emiDetail.getTotal().floatValue();

            Entry principal = new Entry(totalPrincipal, counter);
            valueSet1.add(principal);
            Entry interest = new Entry(totalInterest, counter); // Jan
            valueSet2.add(interest);
            Entry total = new Entry(totalTotal, counter);
            valueSet3.add(total);

            counter++;
        }

        LineDataSet lineDataSet1 = new LineDataSet(valueSet1, "Principal");
        lineDataSet1.setColor(Color.rgb(0, 155, 0));
        LineDataSet lineDataSet2 = new LineDataSet(valueSet2, "Interest");
        lineDataSet2.setColor(Color.rgb(155, 0, 0));
        LineDataSet lineDataSet3 = new LineDataSet(valueSet3, "Total");
        lineDataSet3.setColor(Color.rgb(0, 0, 155));

        dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);
        dataSets.add(lineDataSet2);
        dataSets.add(lineDataSet3);
        return dataSets;
    }

    private ArrayList<BarDataSet> getDataSet(List<EMIDetails> emiDetails) {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        ArrayList<BarEntry> valueSet2 = new ArrayList<>();

        int counter = 0;
        for (EMIDetails emiDetail : emiDetails) {
            BarEntry principal = new BarEntry(emiDetail.getPrincipal().floatValue(), counter);
            valueSet1.add(principal);
            BarEntry interest = new BarEntry(emiDetail.getInterest().floatValue(), counter++); // Jan
            valueSet2.add(interest);
        }

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Principal");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Interest");
        barDataSet2.setColor(Color.rgb(155, 0, 0));

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues(List<EMIDetails> emiDetails) {
        ArrayList<String> xAxis = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM-yy");
        for(EMIDetails emiDetail : emiDetails) {
            xAxis.add(sdf.format(emiDetail.getEmiDate()));
        }
        return xAxis;
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Log.i("EMIDetails", "callback"+item);
    }
}
