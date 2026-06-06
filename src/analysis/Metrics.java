package analysis;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Arrays;
import java.util.Map;

public class Metrics {

    public static double averageWordLength(List<String> words) {
        if (words.isEmpty()) return 0;

        int total = 0;
        for (String w : words)
            total += w.length();

        return (double) total / words.size();
    }

    public static double lexicalDiversity(List<String> words) {
        if (words.isEmpty()) return 0;

        HashSet<String> unique = new HashSet<>(words);
        return (double) unique.size() / words.size();
    }

    public static double averageSentenceLength(String[] sentences) {
        if (sentences.length == 0) return 0;

        int words = 0;
        for (String s : sentences) {
            words += s.trim().split("\\s+").length;
        }

        return (double) words / sentences.length;
    }

    public static List<Double> calculateSentiment(List<String> words) {

        if (words == null || words.isEmpty()) {
            return Arrays.asList(0.0, 0.0, 0.0, 0.0);
        }

        Map<String, Integer> afinnDictionary = new HashMap<>();

        try (InputStream is = Metrics.class.getResourceAsStream("AFINN-111.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");

                if (parts.length == 2) {
                    String word = parts[0].trim().toLowerCase();
                    int score = Integer.parseInt(parts[1].trim());
                    afinnDictionary.put(word, score);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Arrays.asList(0.0, 0.0, 0.0, 0.0);
        }

        double totalScore = 0;

        int sentimentWordCount = 0;
        int positiveCount = 0;
        int neutralCount = 0;
        int negativeCount = 0;

        for (String w : words) {

            if (w == null) {
                continue;
            }

            Integer score = afinnDictionary.get(w.toLowerCase());

            if (score == null) {
                continue; // Ignore words not in AFINN
            }

            totalScore += score;
            sentimentWordCount++;

            if (score >= 2) {
                positiveCount++;
            } else if (score <= -2) {
                negativeCount++;
            } else { // -1 to 1 inclusive
                neutralCount++;
            }
        }

        if (sentimentWordCount == 0) {
            return Arrays.asList(totalScore, 0.0, 0.0, 0.0);
        }

        double positivePercent =
                (double) positiveCount / sentimentWordCount;

        double neutralPercent =
                (double) neutralCount / sentimentWordCount;

        double negativePercent =
                (double) negativeCount / sentimentWordCount;

        return Arrays.asList(
                totalScore,
                positivePercent,
                neutralPercent,
                negativePercent
        );
    }

}
