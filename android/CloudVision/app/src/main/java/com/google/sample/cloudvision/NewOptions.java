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

public class NewOptions extends AppCompatActivity {

    private static String sChosenDescription;
    private static final int NONE = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_options);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.title_activity_options);
        DataContainer data = DataContainer.instance();
        RadioGroup rgp = findViewById(R.id.radiogroup);
        RadioGroup.LayoutParams rprms = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        Toast.makeText(this, "Data Size: " + data.size(), Toast.LENGTH_SHORT).show();
        Iterator it = data.getData();
        while(it.hasNext()) {
            GoogleVisionItemInfo info = (GoogleVisionItemInfo) it.next();
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(info.getProbability() + " " + info.getDefinition());
            radioButton.setId(View.generateViewId());
            rgp.addView(radioButton, rprms);
        }
        RadioButton none = new RadioButton(this);
        none.setText("None");
        none.setId(View.generateViewId());
        rgp.addView(none, rprms);

        rgp.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton chosen = findViewById(checkedId);
            if(!chosen.getText().toString().toUpperCase().equals("NONE")){
                sChosenDescription = chosen.getText().toString().substring(5).trim();
            }else{
                sChosenDescription = "NONE";
            }
            Toast.makeText(NewOptions.this, sChosenDescription, Toast.LENGTH_SHORT).show();

        });

        Button btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener((view) -> {
            Intent intent = new Intent(this, ItemForm.class);
            String desc = "";
            if(!sChosenDescription.toUpperCase().equals("NONE")){
                GoogleVisionItemInfo info = data.get(data.getIndex(sChosenDescription));
                desc = info.getDefinition();
            }else{
                desc = sChosenDescription;
            }
            //intent.putExtra(String key, Object data)
            intent.putExtra("chosenIndex", desc);
            sChosenDescription = null;
            startActivity(intent);
        });
    }

}
