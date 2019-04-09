package com.google.sample.cloudvision;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Iterator;

public class Options extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DataContainer data = DataContainer.instance();
        RadioGroup rgp = findViewById(R.id.radiogroup);
        RadioGroup.LayoutParams rprms;

        Iterator it = data.getData();

        while(it.hasNext()){
            GoogleVisionItemInfo info = (GoogleVisionItemInfo) it.next();
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(info.getProbability() + ": " + info.getDefinition());
            radioButton.setId(View.generateViewId());
            rprms= new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            rgp.addView(radioButton, rprms);
        }
/*
        for(int i=0;i<3;i++){
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText("new"+i);
            radioButton.setId(View.generateViewId());
            rprms= new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            rgp.addView(radioButton, rprms);
        }
        */

    }
}
