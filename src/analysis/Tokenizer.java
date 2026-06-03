package analysis;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;

public class Tokenizer {

    public static List<String> words(String text) {

        text = text.toLowerCase();

        String[] split =
                text.replaceAll("[^a-z ]"," ")
                        .split("\\s+");

        List<String> words =
                new ArrayList<>();

        for(String s : split) {

            if(!s.isBlank()) {
                words.add(s);
            }
        }

        return words;
    }

    public static String[] sentences(
            String text) {

        return text.split(
                "[.!?]+");
    }
}