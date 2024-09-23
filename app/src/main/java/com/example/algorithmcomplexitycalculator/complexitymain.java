package com.example.algorithmcomplexitycalculator;
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
    LinkedList<String> afg = new LinkedList<>();
    VariableAssignmentPattern var2;
    IfStatementPattern var3;
    arithpatternmatcher var4;
    ForLoopPatternMatcher forLoopMatcher;
    String outerLoop = null;  // To track outer for loop for nesting

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
            calculateComplexity();

        });

    }
    private void addTableRow(TableRow row, String statement, String s_e, String frequency, String totalSteps) {
        TextView statementView = createTextView(statement);
        TextView seView = createTextView(s_e);
        TextView frequencyView = createTextView(frequency);
        TextView totalStepsView = createTextView(totalSteps);

        row.addView(statementView);
        row.addView(seView);
        row.addView(frequencyView);
        row.addView(totalStepsView);
    }

    // Helper method to create a TextView with text wrapping enabled
    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setPadding(8, 8, 8, 8);
        textView.setText(text);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setMaxWidth(200); // Set a max width to ensure wrapping occurs (adjust as needed)
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
            // Create the complexity string based on variables used in the nested loops
            StringBuilder complexityBuilder = new StringBuilder("O(");
            String variableString = "";

            for (int i = 0; i < maxDepth; i++) {
                // Assuming 'n' is the variable used in each loop level
                if (i == 0) {
                    variableString = "n";
                } else {
                    variableString += "n";
                }
            }

            complexityBuilder.append(variableString).append(")");
            complexity = complexityBuilder.toString();
        }

        // Display the calculated complexity
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
