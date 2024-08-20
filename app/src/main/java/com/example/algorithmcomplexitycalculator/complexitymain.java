package com.example.algorithmcomplexitycalculator;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.LinkedList;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
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
            displayanalysis();
        });
    }
    public void displayanalysis() {
        setContentView(R.layout.table);
        TableLayout resultTable = findViewById(R.id.resultTable);
        for (String line : afg) {
            TableRow row = new TableRow(this);
            if (line.contains("Algorithm") || line.contains("algorithm")) {
                addTableRow(row, line, "0", "-", "-");
            } else if (line.contains("{") || line.contains("}")) {
                addTableRow(row,line,"0","-","-");
            } else if (var2.check1(line)) {
                addTableRow(row, line, "1", "1", "1");
            } else if (line.contains("return")) {
                if (line.contains("0.0")) {
                    addTableRow(row, line, "1", "1", "1");
                } else {
                    addTableRow(row, line, "1", "0", "0");
                }
            } else {
                addTableRow(row, line, "-", "-", "-");
            }
            resultTable.addView(row);
        }
    }
    private void addTableRow(TableRow row, String statement, String s_e, String frequency, String totalSteps) {
        TextView statementView = new TextView(this);
        statementView.setPadding(8, 8, 8, 8);
        statementView.setText(statement);
        TextView seView = new TextView(this);
        seView.setPadding(8, 8, 8, 8);
        seView.setText(s_e);
        TextView frequencyView = new TextView(this);
        frequencyView.setPadding(8, 8, 8, 8);
        frequencyView.setText(frequency);
        TextView totalStepsView = new TextView(this);
        totalStepsView.setPadding(8, 8, 8, 8);
        totalStepsView.setText(totalSteps);
        row.addView(statementView);
        row.addView(seView);
        row.addView(frequencyView);
        row.addView(totalStepsView);
    }


}
