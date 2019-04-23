package com.google.sample.cloudvision;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.lang.NonNull;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.auth.providers.anonymous.AnonymousCredential;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteUpdateOptions;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteUpdateResult;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class MongoDB {

    private static MongoDB instance;
    final StitchAppClient client;// = Stitch.initializeDefaultAppClient("se341-sdubt");
    final RemoteMongoClient mongoClient;// = client.getServiceClient(RemoteMongoClient.factory, "mongodb-atlas");
    final RemoteMongoCollection<Document> coll;// = mongoClient.getDatabase("SE341").getCollection("Users");

    private final String STITCH_APP_ID = /*ANAS'*/ "ezclaim-mobile-tmego";      /*MINE*///"se341-sdubt";
    private final String SERVICE_NAME = /*ANAS'*/ /*"mongodb1";*/                    /*MINE*/ "mongodb-atlas";
    private final String DATABASE = /*ANAS'*/ "EZ-Claim";                       /*MINE*/ //"SE341";
    private final String COLLECTION = /*ANAS'*/ "Inventory";                    /*MINE*/ //"Products";

    private MongoDB(){
        client = Stitch.initializeDefaultAppClient(STITCH_APP_ID);
        System.out.println("After client");
        mongoClient = client.getServiceClient(RemoteMongoClient.factory, SERVICE_NAME);
        coll = mongoClient.getDatabase(DATABASE).getCollection(COLLECTION);
    }

    public static MongoDB instance(){
        if(instance == null){
            return instance = new MongoDB();
        }else{
            return instance;
        }
    }

    public boolean save(List data){
        List<String> dataToSave = data;
        client.getAuth().loginWithCredential(new AnonymousCredential()).continueWithTask(task -> {
            if (!task.isSuccessful()) {
                Log.e("STITCH", "Login failed!");
                throw task.getException();
            }

            final Document newDoc = new /*Document();*/Document("_id", ObjectId.get());

            //Document: Name, Room, Price, Mod Num, Ser Num, Purchase Loc
            newDoc.put("Name", dataToSave.get(0));
            newDoc.put("Room", dataToSave.get(1));
            newDoc.put("Price", dataToSave.get(2));
            newDoc.put("ModelNum", dataToSave.get(3));
            newDoc.put("SerialNum", dataToSave.get(4));
            newDoc.put("Purchase_Location", dataToSave.get(5));
            newDoc.put("Product_URL", "");
            newDoc.put("UserID", new ObjectId("5cbcff74ecc67b00173cafe6")); // id for un: scsu@gmail.com, pw: scsu

            /**
             * remove if this breaks everything
             */
            //newDoc.put("Image_Bitmap", dataToSave.get(6));

            return coll.insertOne(newDoc);//coll.updateOne(null, newDoc, new RemoteUpdateOptions().upsert(true));


        });
        return true;
    }
}
