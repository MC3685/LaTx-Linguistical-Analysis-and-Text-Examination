package analysis;

import java.util.HashSet;
import java.util.List;

public class Metrics {

    public static double averageWordLength(
            List<String> words) {

        if(words.isEmpty())
            return 0;

        int total = 0;

        for(String w : words)
            total += w.length();

        return (double) total /
                words.size();
    }

    public static double lexicalDiversity(
            List<String> words) {

        if(words.isEmpty())
            return 0;

        HashSet<String> unique =
                new HashSet<>(words);

        return (double) unique.size()
                / words.size();
    }

    public static double averageSentenceLength(
            String[] sentences) {

        if(sentences.length == 0)
            return 0;

        int words = 0;

        for(String s : sentences) {

            words +=
                    s.trim()
                            .split("\\s+")
                            .length;
        }

        return (double) words /
                sentences.length;
    }
}