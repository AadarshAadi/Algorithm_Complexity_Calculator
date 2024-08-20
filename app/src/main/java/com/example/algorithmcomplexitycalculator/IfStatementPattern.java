package com.example.algorithmcomplexitycalculator;

import java.util.regex.*;

public class IfStatementPattern {
    public static boolean checkIfStatement(String text) {
        String patternString = "\\bif\\s*\\(.*?\\)\\s*\\{?";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }
    public static int getComparisonCount(String text) {
        String patternString = "([a-zA-Z_][a-zA-Z0-9_]*)\\s*(==|!=|<=|>=|<|>)\\s*([0-9]+)";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            String operator = matcher.group(2);
            int value = Integer.parseInt(matcher.group(3));
            switch (operator) {
                case "==":
                case "!=":
                    return 1;
                case "<=":
                case ">=":
                case "<":
                case ">":
                    return 2;
                default:
                    return 0;
            }
        }
        return 0;
    }
}
