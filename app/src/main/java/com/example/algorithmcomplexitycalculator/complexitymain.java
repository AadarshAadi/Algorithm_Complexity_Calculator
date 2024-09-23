package com.example.algorithmcomplexitycalculator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Collections;
import java.util.LinkedList;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
public class complexitymain extends AppCompatActivity {
    EditText algo;
    Button analyze;
    Button fin;
    Button back;
    Button fg;
    LinkedList<String> afg = new LinkedList<>();
    VariableAssignmentPattern var2;
    IfStatementPattern var3;
    arithpatternmatcher var4;
    ForLoopPatternMatcher forLoopMatcher;
    String outerLoop = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_area);
        algo = findViewById(R.id.textView3);
        algo.setText("");
        analyze = findViewById(R.id.button);
        var2 = new VariableAssignmentPattern();
        var3 = new IfStatementPattern();
        var4 = new arithpatternmatcher();
        forLoopMatcher = new ForLoopPatternMatcher();

        analyze.setOnClickListener(view -> {
            String input = algo.getText().toString();
            if (!input.isEmpty()) {
                String[] lines = input.split("\n");
                Collections.addAll(afg, lines);
                displayanalysis();
            }
        });
    }
    public void displayanalysis() {
        setContentView(R.layout.table);
        analyze=findViewById(R.id.button3);
        analyze.setOnClickListener(view -> {setContentView(R.layout.main_area);});
        TableLayout resultTable = findViewById(R.id.resultTable);
        fin=findViewById(R.id.findButton);
        back=findViewById(R.id.button3);
        for (String line : afg) {
            TableRow row = new TableRow(this);

            if (forLoopMatcher.isForLoop(line)) {
                if (outerLoop == null) {
                    outerLoop = line;
                    String iterationCount = forLoopMatcher.getIterationCount(line);
                    addTableRow(row, line, "1", iterationCount, iterationCount);
                } else {
                    String nestedIterationCount = forLoopMatcher.getNestedLoopIterationCount(outerLoop, line);
                    addTableRow(row, line, "1", nestedIterationCount, nestedIterationCount);
                    outerLoop = null;
                }
            } else if (VariableAssignmentPattern.check1(line)) {
                if (outerLoop != null) {
                    String adjustedFreq = forLoopMatcher.adjustFrequencyForLoop(1, outerLoop);
                    addTableRow(row, line, "1", adjustedFreq, adjustedFreq);
                } else {
                    addTableRow(row, line, "1", "1", "1");
                }
            } else if (IfStatementPattern.checkIfStatement(line)) {
                if (outerLoop != null) {
                    String adjustedFreq = forLoopMatcher.adjustFrequencyForLoop(var3.getComparisonCount(line), outerLoop);
                    addTableRow(row, line, "1", adjustedFreq, adjustedFreq);
                } else {
                    addTableRow(row, line, "1", Integer.toString(var3.getComparisonCount(line)), Integer.toString(var3.getComparisonCount(line)));
                }
            } else if (line.contains("return")) {
                if (outerLoop != null) {
                    String adjustedFreq = forLoopMatcher.adjustFrequencyForLoop(1, outerLoop);
                    addTableRow(row, line, "1", adjustedFreq, adjustedFreq);
                } else {
                    addTableRow(row, line, "1", "1", "1");
                }
            } else if (var4.checkk(line)) {
                if (outerLoop != null) {
                    String adjustedFreq = forLoopMatcher.adjustFrequencyForLoop(1, outerLoop);
                    addTableRow(row, line, "1", adjustedFreq, adjustedFreq);
                } else {
                    addTableRow(row, line, "1", "1", "1");
                }
            } else if (line.contains("else")) {
                if (outerLoop != null) {
                    String adjustedFreq = forLoopMatcher.adjustFrequencyForLoop(1, outerLoop);
                    addTableRow(row, line, "1", adjustedFreq, adjustedFreq);
                } else {
                    addTableRow(row, line, "1", "1", "1");
                }
            } else {
                addTableRow(row, line, "-", "-", "-");
            }

            resultTable.addView(row);
        }
        fin.setOnClickListener(view -> {
            setContentView(R.layout.dfg);
            fg=findViewById(R.id.button2);
            fg.setOnClickListener(view1 -> {
                String input = algo.getText().toString();
                if (!input.isEmpty()) {
                    afg.clear();
                    String[] lines = input.split("\n");
                    Collections.addAll(afg, lines);
                    displayanalysis();
                }
            });
            calculateComplexity();
        });
        back.setOnClickListener(view -> {
            setContentView(R.layout.main_area);
        });

    }
    private void addTableRow(TableRow row, String statement, String s_e, String frequency, String totalSteps) {
        TextView statementView = new TextView(this);
        statementView.setPadding(8, 8, 8, 8);
        statementView.setText(statement);
        statementView.setTextColor(Color.BLACK); // Set text color to black

        TextView seView = new TextView(this);
        seView.setPadding(8, 8, 8, 8);
        seView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        seView.setText(s_e);
        seView.setTextColor(Color.BLACK); // Set text color to black

        TextView frequencyView = new TextView(this);
        frequencyView.setPadding(8, 8, 8, 8);
        frequencyView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        frequencyView.setText(frequency);
        frequencyView.setTextColor(Color.BLACK); // Set text color to black

        TextView totalStepsView = new TextView(this);
        totalStepsView.setPadding(8, 8, 8, 8);
        totalStepsView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        totalStepsView.setText(totalSteps);
        totalStepsView.setTextColor(Color.BLACK); // Set text color to black

        row.addView(statementView);
        row.addView(seView);
        row.addView(frequencyView);
        row.addView(totalStepsView);
    }
    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setPadding(8, 8, 8, 8);
        textView.setText(text);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setMaxWidth(200);
        textView.setSingleLine(false);
        textView.setEllipsize(null); // Disable ellipsizing (three dots)
        textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        return textView;
    }
    public void calculateComplexity() {
        int loopDepth = 0;
        int maxDepth = 0;
        boolean insideLoop = false;
        String complexity = "O(1)";

        for (String line : afg) {
            if (forLoopMatcher.isForLoop(line)) {
                loopDepth++;
                maxDepth = Math.max(maxDepth, loopDepth);
                insideLoop = true;
            } else if (line.contains("}")) {
                if (insideLoop && loopDepth > 0) {
                    loopDepth--;
                }
            }
        }

        if (maxDepth > 0) {
            StringBuilder complexityBuilder = new StringBuilder("O(");
            String variableString = "";

            for (int i = 0; i < maxDepth; i++) {
                if (i == 0) {
                    variableString = "n";
                } else {
                    variableString += "n";
                }
            }

            complexityBuilder.append(variableString).append(")");
            complexity = complexityBuilder.toString();
        }
        TextView complexityView = findViewById(R.id.dkff);
        complexityView.setText(complexity);
    }
    private String extractVariable(String loopLine) {
        // This method extracts the loop variable from a loop line
        // For example, from "for(i=1;i<=n;i++)", it should return "i"
        String[] parts = loopLine.split("\\s*");
        String initPart = parts[1]; // Assuming the loop starts with "for (var=initial; ...)"
        return initPart.split("=")[0].trim(); // Return the variable before the '='
    }

}
