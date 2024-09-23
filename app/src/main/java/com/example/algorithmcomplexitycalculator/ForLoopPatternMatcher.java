package com.example.algorithmcomplexitycalculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForLoopPatternMatcher {

    // Check if the line contains a valid "for" loop pattern
    public boolean isForLoop(String line) {
        return line.matches(".*for\\s*\\(.*;.*;.*\\).*");
    }

    public String getIterationCount(String forLoop) {
        String[] parts = forLoop.split(";");
        String initPart = parts[0].trim();
        String conditionPart = parts[1].trim();

        String n = extractInitialValue(initPart); // Initial value of i (n)
        String m = extractLimitValue(conditionPart); // Limit value (m)

        if (conditionPart.contains("<=")) {
            return "(" + m + "+1-" + n + ")";  // For loops like "i <= m", return (m + 1 - n)
        } else if (conditionPart.contains("<")) {
            return "(" + m + "-" + n + ")";  // For loops like "i < m", return (m - n)
        }

        return "0"; // Return "0" for unsupported loop formats
    }


    public String adjustFrequencyForLoop(int originalFrequency, String forLoop) {
        String[] parts = forLoop.split(";");
        String initPart = parts[0].trim();
        String conditionPart = parts[1].trim();

        String n = extractInitialValue(initPart); // Initial value of i (n)
        String m = extractLimitValue(conditionPart); // Limit value (m)

        if (conditionPart.contains("<=")) {
            return originalFrequency + "*(" + m + "-(" + n + "-1))";  // For loops like "i <= m", inside frequency becomes (m - (n - 1))
        } else if (conditionPart.contains("<")) {
            return originalFrequency + "*(" + m + "-" + n + ")";  // For loops like "i < m", inside frequency becomes (m - n)
        }

        return Integer.toString(originalFrequency); // For unsupported formats, return the original frequency
    }

    public String getNestedLoopIterationCount(String outerLoop, String innerLoop) {
        String outerIterationCount = getIterationCount(outerLoop);
        String innerIterationCount = getIterationCount(innerLoop);
        return "(" + outerIterationCount + ")*(" + innerIterationCount + ")";
    }

    private String extractInitialValue(String initPart) {
        String[] initSplit = initPart.split("=");
        return initSplit[1].trim();
    }

    private String extractLimitValue(String conditionPart) {
        String regex = "(<=|<)\\s*(\\w+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(conditionPart);
        if (matcher.find()) {
            return matcher.group(2);
        }
        throw new NumberFormatException("Invalid loop condition: " + conditionPart);
    }
}
