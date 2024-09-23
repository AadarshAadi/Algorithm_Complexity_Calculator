package com.example.algorithmcomplexitycalculator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class arithpatternmatcher {
    public static boolean checkk(String text) {
        String patternString = "([a-zA-Z_][a-zA-Z0-9_]*\\s*(\\[\\s*[0-9]+\\s*])?)\\s*=\\s*([a-zA-Z_][a-zA-Z0-9_]*\\s*(\\[\\s*[0-9]+\\s*])?|[0-9]+)\\s*([+\\-*/^])\\s*([a-zA-Z_][a-zA-Z0-9_]*\\s*(\\[\\s*[0-9]+\\s*])?|[0-9]+)";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }
}
