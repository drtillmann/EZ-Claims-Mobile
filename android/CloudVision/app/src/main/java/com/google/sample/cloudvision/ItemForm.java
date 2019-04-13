package com.google.sample.cloudvision;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ItemForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_form);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DataContainer data = DataContainer.instance();
        String definition = getIntent().getExtras().get("chosenIndex").toString();
        String[] formData = new String[3];

        //Toast.makeText(this, definition, Toast.LENGTH_SHORT).show();


        TextView name = findViewById(R.id.txtName);
        name.setText(definition);


    }

}
