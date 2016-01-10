package com.sayaanand.helloworld;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sayaanand.helloworld.utils.Constants;

/**
 * A placeholder fragment containing a simple view.
 */
public class EMICalculatorActivity1Fragment extends Fragment {

    public EMICalculatorActivity1Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_emicalculator1, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /*Intent intent = getActivity().getIntent();
        String message = intent.getStringExtra(Constants.EMI_AMOUNT_MESSAGE);

        TextView textView = new TextView(getActivity());
        textView.setTextSize(40);
        textView.setText(message);

        RelativeLayout layout = (RelativeLayout) getActivity().findViewById(R.id.emi_fragment_content1);
        layout.addView(textView);*/
    }
}
