package analysis;

import java.util.*;


public class TextProfile {

    public int wordCount;
    public int sentenceCount;
    public int charCount;

    public Map<String, Integer> wordFreq = new HashMap<>();

    public int uniqueWords;

    public int commas;

    public int colons;

    public int semicolons;

    public int exclamations;

    public int questions;

    public double avgSentenceLength;
    public double avgWordLength;

    public double lexicalDiversity;

    public double punctuationDensity;

    public double functionWordDensity;

    public List<Integer> sentenceLengths = new ArrayList<>();

    public double Sentiment;




}