package com.google.sample.cloudvision;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ItemForm extends AppCompatActivity {

    private String[] data = new String[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_form);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String definition = getIntent().getExtras().get("chosenIndex").toString();
        RoomItemCollections collections = new RoomItemCollections(definition);
        MongoDB database = MongoDB.instance();

        /**
         * index 0: room
         * index 1: price
         * index 2: purchase location
         */
        String[] formData = collections.getAutoFillData();

        TextView tbxName = findViewById(R.id.txtName);
        tbxName.setText(definition);
        TextView tbxRoom = findViewById(R.id.txtRoom);
        TextView tbxPrice = findViewById(R.id.txtPrice);
        TextView tbxModelNum = findViewById(R.id.txtModNum);
        TextView tbxSerialNum = findViewById(R.id.txtSerialNum);
        TextView tbxPurLoc = findViewById(R.id.txtPurchaseLoc);


        if(formData[0] != null && formData[1] != null && formData[2] != null){
            tbxRoom.setText(formData[0]);
            tbxPrice.setText(formData[1]);
            tbxPurLoc.setText(formData[2]);
        }

        Button btnUndoClear = findViewById(R.id.btnUndo);
        btnUndoClear.setVisibility(View.INVISIBLE);

        Button btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(view -> {
            tbxName.setText(null);
            tbxRoom.setText(null);
            tbxPrice.setText(null);
            tbxModelNum.setText(null);
            tbxSerialNum.setText(null);
            tbxPurLoc.setText(null);
            btnUndoClear.setVisibility(View.VISIBLE);
        });


        btnUndoClear.setOnClickListener(view -> {
            if(formData[0] != null && formData[1] != null && formData[2] != null) {
                tbxName.setText(definition);
                tbxRoom.setText(formData[0]);
                tbxPrice.setText(formData[1]);
                tbxPurLoc.setText(formData[2]);
            }
            btnUndoClear.setVisibility(View.INVISIBLE);
        });

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener((view) -> {

            //my key: b27e5237-7eac-4235-ae82-acf3493c9fa3

            // mongodb://_:b27e5237-7eac-4235-ae82-acf3493c9fa3@stitch.mongodb.com:27020/?authMechanism=PLAIN&authSource=%24external&ssl=true&appName=se341-sdubt:mongodb-atlas:api-key

            // mongodb://<USERNAME>:<PASSWORD>@stitch.mongodb.com:27020/?authMechanism=PLAIN&authSource=%24external&ssl=true&appName=se341-sdubt:mongodb-atlas:local-userpass

            // mongodb://drtillmann@gmail.com:<PASSWORD>@stitch.mongodb.com:27020/?authMechanism=PLAIN&authSource=%24external&ssl=true&appName=se341-sdubt:mongodb-atlas:local-userpass

            String name = tbxName.getText().toString().trim();
            String room = tbxRoom.getText().toString().trim();
            String price = tbxPrice.getText().toString().trim();
            String modNum = tbxModelNum.getText().toString().trim();
            String serNum = tbxSerialNum.getText().toString().trim();
            String purLoc = tbxPurLoc.getText().toString().trim();

            data[0] = (name.equals("")) ? "N/A" : name;
            data[1] = (room.equals("")) ? "N/A" : room;
            data[2] = (price.equals("")) ? "N/A" : price;
            data[3] = (modNum.equals("")) ? "N/A" : modNum;
            data[4] = (serNum.equals("")) ? "N/A" : serNum;
            data[5] = (purLoc.equals("")) ? "N/A" : purLoc;

            database.save(data);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
