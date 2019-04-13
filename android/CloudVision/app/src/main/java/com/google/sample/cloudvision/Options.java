package com.google.sample.cloudvision;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Iterator;

public class Options extends AppCompatActivity {

    private static String sChosenDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.title_activity_options);
        DataContainer data = DataContainer.instance();
        RadioGroup rgp = findViewById(R.id.radiogroup);
        RadioGroup.LayoutParams rprms;
        Toast.makeText(this, "Data Size: " + data.size(), Toast.LENGTH_SHORT).show();
        Iterator it = data.getData();
        while(it.hasNext()) {
            GoogleVisionItemInfo info = (GoogleVisionItemInfo) it.next();
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(info.getProbability() + " " + info.getDefinition());
            radioButton.setId(View.generateViewId());
            rprms = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            rgp.addView(radioButton, rprms);
        }
        rgp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton chosen = (RadioButton) findViewById(checkedId);
                sChosenDescription = chosen.getText().toString().substring(6).trim();
                Toast.makeText(Options.this, sChosenDescription, Toast.LENGTH_SHORT).show();
            }
        });

        Button btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener((view) -> {
            Intent intent = new Intent(this, ItemForm.class);
            //intent.putExtra(String key, Object data)
            GoogleVisionItemInfo info = data.get(data.getIndex(sChosenDescription));
            intent.putExtra("chosenIndex", info.getDefinition());
            startActivity(intent);
        });
    }
}
