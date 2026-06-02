package analysis;

import java.util.List;

public class AnalysisEngine {

    public AnalysisResult analyze(
            String textA,
            String textB) {

        TextProfile profileA =
                buildProfile(textA);

        TextProfile profileB =
                buildProfile(textB);

        double sentenceScore =
                SimilarityCalculator.compare(
                        profileA.avgSentenceLength,
                        profileB.avgSentenceLength);

        double vocabScore =
                SimilarityCalculator.compare(
                        profileA.lexicalDiversity,
                        profileB.lexicalDiversity);

        double punctuationScore =
                SimilarityCalculator.compare(
                        profileA.punctuationDensity,
                        profileB.punctuationDensity);

        double similarity =
                sentenceScore * 0.35 +
                        vocabScore * 0.35 +
                        punctuationScore * 0.30;

        return new AnalysisResult(
                similarity,
                profileA,
                profileB);
    }

    private TextProfile buildProfile(
            String text) {

        TextProfile profile =
                new TextProfile();

        List<String> words =
                Tokenizer.words(text);

        String[] sentences =
                Tokenizer.sentences(text);

        profile.totalWords =
                words.size();

        profile.uniqueWords =
                (int)(Metrics.lexicalDiversity(
                        words)
                        * words.size());

        profile.avgWordLength =
                Metrics.averageWordLength(
                        words);

        profile.avgSentenceLength =
                Metrics.averageSentenceLength(
                        sentences);

        profile.lexicalDiversity =
                Metrics.lexicalDiversity(
                        words);

        profile.commas =
                count(text, ',');

        profile.semicolons =
                count(text, ';');

        profile.questions =
                count(text, '?');

        profile.exclamations =
                count(text, '!');

        profile.punctuationDensity =
                (profile.commas +
                        profile.semicolons +
                        profile.questions +
                        profile.exclamations)
                        /
                        Math.max(
                                1.0,
                                profile.totalWords);

        return profile;
    }

    private int count(
            String text,
            char c) {

        int count = 0;

        for(char x :
                text.toCharArray()) {

            if(x == c)
                count++;
        }

        return count;
    }
}