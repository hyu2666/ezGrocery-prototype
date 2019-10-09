package edu.cmu.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //get the button
        Button btn = findViewById(R.id.convertBtn);
        Button insertBtn = findViewById(R.id.insert);
        Button getBtn = findViewById(R.id.get);
        final TextView dbText = findViewById(R.id.dbText);
        final TextView textView = findViewById(R.id.restultTxt);
        final TextInputEditText input = findViewById(R.id.textInput);

        MongoClient mongoClient = new MongoClient();
        final MongoDatabase db = mongoClient.getDatabase("sm2019");

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
            }
        });


        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Retrieving...", Toast.LENGTH_SHORT)
                        .show();

                MongoCollection<Document> booksCollection = db.getCollection("Books");
                FindIterable<Document> booksIterDoc = booksCollection.find();
                StringBuilder sb = new StringBuilder();
                for (Document document : booksIterDoc) {
                    sb.append(document.get("bookName"));
                }
                dbText.setText(sb.toString());
            }
        });

    }
}
