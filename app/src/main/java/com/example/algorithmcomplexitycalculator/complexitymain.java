package com.example.algorithmcomplexitycalculator;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.LinkedList;
public class complexitymain extends AppCompatActivity {
    EditText algo;
    Button analyze;
    LinkedList<String> afg = new LinkedList<>();
    VariableAssignmentPattern var2;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_area);
        algo=findViewById(R.id.textView3);
        analyze=findViewById(R.id.button);
        var2 = new VariableAssignmentPattern();
        analyze.setOnClickListener(view -> {
            String[] lines = algo.getText().toString().split("\n");
            for (String line : lines) {
                afg.add(line);
            }
        });
        for(String line : afg)
        {
            if(line.contains("Algorithm")||line.contains("algorithm"))
            {}
            else if (line.contains("{")||line.contains("}"))
            {}
            else if (var2.check1(line))
            {}
            else {

            }
        }
    }
}
