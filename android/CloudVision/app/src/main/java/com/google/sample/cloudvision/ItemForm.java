package com.google.sample.cloudvision;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.lang.NonNull;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.core.auth.StitchUser;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.auth.providers.anonymous.AnonymousCredential;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteUpdateOptions;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteUpdateResult;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ItemForm extends AppCompatActivity {

    //DatabaseConnection connection = DatabaseConnection.instance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_form);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //DataContainer data = DataContainer.instance();
        String definition = getIntent().getExtras().get("chosenIndex").toString();
        RoomItemCollections collections = new RoomItemCollections(definition);

        /**
         * index 0: room
         * index 1: price
         * index 2: purchase location
         */
        String[] formData = collections.getAutoFillData();

        //Toast.makeText(this, definition, Toast.LENGTH_SHORT).show();


        //test generated data to get which room its in




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

            System.out.println("Inside btnSave");
            //my key: b27e5237-7eac-4235-ae82-acf3493c9fa3

            // mongodb://_:b27e5237-7eac-4235-ae82-acf3493c9fa3@stitch.mongodb.com:27020/?authMechanism=PLAIN&authSource=%24external&ssl=true&appName=se341-sdubt:mongodb-atlas:api-key

            // mongodb://<USERNAME>:<PASSWORD>@stitch.mongodb.com:27020/?authMechanism=PLAIN&authSource=%24external&ssl=true&appName=se341-sdubt:mongodb-atlas:local-userpass

            // mongodb://drtillmann@gmail.com:<PASSWORD>@stitch.mongodb.com:27020/?authMechanism=PLAIN&authSource=%24external&ssl=true&appName=se341-sdubt:mongodb-atlas:local-userpass

            System.out.println("Before client");//printed
            final StitchAppClient client = Stitch.initializeDefaultAppClient("se341-sdubt");
            System.out.println("After client");//printed

            final RemoteMongoClient mongoClient = client.getServiceClient(RemoteMongoClient.factory, "mongodb-atlas");
            System.out.println("After mongoClient");

            final RemoteMongoCollection<Document> coll = mongoClient.getDatabase("SE341").getCollection("Users");
            System.out.println("After coll");

            client.getAuth().loginWithCredential(new AnonymousCredential()).continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    Log.e("STITCH", "Login failed!");
                    throw task.getException();
                }

                final Document updateDoc = new Document("owner_id", task.getResult().getId());

                updateDoc.put("number", 42);
                return coll.updateOne(
                        null, updateDoc, new RemoteUpdateOptions().upsert(true)
                );
            }
            ).continueWithTask(new Continuation<RemoteUpdateResult, Task<List<Document>>>() {
                @Override
                public Task<List<Document>> then(@NonNull Task<RemoteUpdateResult> task) throws Exception {
                    if (!task.isSuccessful()) {
                        Log.e("STITCH", "Update failed!");
                        throw task.getException();
                    }
                    List<Document> docs = new ArrayList<>();
                    return coll
                            .find(new Document("owner_id", client.getAuth().getUser().getId()))
                            .limit(100)
                            .into(docs);
                }
            }).addOnCompleteListener(new OnCompleteListener<List<Document>>() {
                @Override
                public void onComplete(@NonNull Task<List<Document>> task) {
                    if (task.isSuccessful()) {
                        Log.d("STITCH", "Found docs: " + task.getResult().toString());
                        Toast.makeText(ItemForm.this, "Saved to DB", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Log.e("STITCH", "Error: " + task.getException().toString());
                    task.getException().printStackTrace();
                }
            });



















        });

    }

}
