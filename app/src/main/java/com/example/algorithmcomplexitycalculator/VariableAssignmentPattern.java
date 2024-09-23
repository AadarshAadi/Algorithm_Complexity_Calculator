package com.example.algorithmcomplexitycalculator;
import java.util.regex.*;
public class VariableAssignmentPattern {
    public static boolean check1(String text) {
        String patternString = "\\b([a-zA-Z_][a-zA-Z0-9_]*\\s*(\\[\\s*[0-9]+\\s*])?)\\s*=\\s*([a-zA-Z_][a-zA-Z0-9_]*\\s*(\\[\\s*[0-9]+\\s*])?|[0-9]+)\\b";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }
}