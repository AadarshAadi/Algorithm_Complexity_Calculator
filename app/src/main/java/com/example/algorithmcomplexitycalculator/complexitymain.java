package com.example.algorithmcomplexitycalculator;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;

public class complexitymain extends AppCompatActivity {
    EditText algo;
    Button analyze;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_area);
        algo=findViewById(R.id.textView3);
        analyze=findViewById(R.id.button);
        analyze.setOnClickListener(view -> {
            LinkedList<String> afg = new LinkedList<>();
            String[] lines = algo.getText().toString().split("\n");

            for (String line : lines) {
                afg.push(line);
            }
            System.out.println(afg);
        });
    }
}
