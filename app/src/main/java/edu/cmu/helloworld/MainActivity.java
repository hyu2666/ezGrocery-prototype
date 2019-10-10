package edu.cmu.helloworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.mongodb.client.FindIterable;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.core.auth.StitchUser;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.auth.providers.anonymous.AnonymousCredential;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteUpdateOptions;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteUpdateResult;

// Base Stitch Packages
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;

// Packages needed to interact with MongoDB and Stitch
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;

// Necessary component for working with MongoDB Mobile
import com.mongodb.stitch.android.services.mongodb.local.LocalMongoDbService;


import org.bson.BsonString;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
//import com.mongodb.MongoClient;
//import com.mongodb.client.FindIterable;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//
//import org.bson.Document;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        final StitchAppClient client =
//                Stitch.initializeDefaultAppClient("testapplication-rssxk");
//
//        final RemoteMongoClient mongoClient =
//                client.getServiceClient(RemoteMongoClient.factory, "mongodb-atlas");
//
//        final RemoteMongoCollection<Document> coll =
//                mongoClient.getDatabase("sm").getCollection("users");
//
//        client.getAuth().loginWithCredential(new AnonymousCredential()).continueWithTask(
//                new Continuation<StitchUser, Task<RemoteUpdateResult>>() {
//
//                    @Override
//                    public Task<RemoteUpdateResult> then(@NonNull Task<StitchUser> task) throws Exception {
//                        if (!task.isSuccessful()) {
//                            Log.e("STITCH", "Login failed!");
//                            throw task.getException();
//                        }
//
//                        final Document updateDoc = new Document(
//                                "owner_id",
//                                task.getResult().getId()
//                        );
//
//                        updateDoc.put("number", 42);
//                        return coll.updateOne(
//                                null, updateDoc, new RemoteUpdateOptions().upsert(true)
//                        );
//                    }
//                }
//        ).continueWithTask(new Continuation<RemoteUpdateResult, Task<List<Document>>>() {
//            @Override
//            public Task<List<Document>> then(@NonNull Task<RemoteUpdateResult> task) throws Exception {
//                if (!task.isSuccessful()) {
//                    Log.e("STITCH", "Update failed!");
//                    throw task.getException();
//                }
//                List<Document> docs = new ArrayList<>();
//                return coll
//                        .find(new Document("owner_id", client.getAuth().getUser().getId()))
//                        .limit(100)
//                        .into(docs);
//            }
//        }).addOnCompleteListener(new OnCompleteListener<List<Document>>() {
//            @Override
//            public void onComplete(@NonNull Task<List<Document>> task) {
//                if (task.isSuccessful()) {
//                    Log.d("STITCH", "Found docs: " + task.getResult().toString());
//                    return;
//                }
//                Log.e("STITCH", "Error: " + task.getException().toString());
//                task.getException().printStackTrace();
//            }
//        });

        // Create the default Stitch Client
        final StitchAppClient client =
                Stitch.initializeDefaultAppClient("testapplication-rssxk");

        // Create a Client for MongoDB Mobile (initializing MongoDB Mobile)
        final MongoClient mobileClient =
                client.getServiceClient(LocalMongoDbService.clientFactory);




        //get the button
        Button btn = findViewById(R.id.convertBtn);
        Button insertBtn = findViewById(R.id.insert);
        Button getBtn = findViewById(R.id.get);
        final TextView dbText = findViewById(R.id.dbText);
        final TextView textView = findViewById(R.id.restultTxt);
        final TextInputEditText input = findViewById(R.id.textInput);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "This is a test", Toast.LENGTH_SHORT)
                .show();
                Editable inputNumber = input.getText();
                String str_num = inputNumber.toString();
                int num = Integer.parseInt(str_num) * 7;
                String out = String.valueOf(num);
                textView.setText(out);
            }
        });

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Inserting...", Toast.LENGTH_SHORT)
                        .show();

                // Point to the target collection and insert a document
                MongoCollection<Document> localCollection =
                        mobileClient.getDatabase("my_db").getCollection("my_collection");

                Document document = new Document("id", "hello").append("name", "world");
                localCollection.insertOne(document);
                document = new Document("id", "second").append("name", "veirs");
                localCollection.insertOne(document);

                // Find the first document
                Document doc = localCollection.find().first();

                //Find all documents that match the find criteria
                Document query = new Document();
                query.put("name", new BsonString("veirs"));

                FindIterable<Document> cursor = localCollection.find(query);
                ArrayList<Document> results =
                        (ArrayList<Document>) cursor.into(new ArrayList<Document>());

            }
        });


        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Retrieving...", Toast.LENGTH_SHORT)
                        .show();

                MongoCollection<Document> booksCollection =
                        mobileClient.getDatabase("my_db").getCollection("my_collection");

                FindIterable<Document> booksIterDoc = booksCollection.find();
                StringBuilder sb = new StringBuilder();
                for (Document document : booksIterDoc) {
                    sb.append(document.get("name"));
                }
                dbText.setText(sb.toString());
            }
        });

    }
}
