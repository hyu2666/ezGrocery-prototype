package edu.cmu.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //get the button
        Button btn = findViewById(R.id.convertBtn);
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
    }
}
