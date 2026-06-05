package analysis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AnalysisResult {

    private final double similarity;
    private final TextProfile profileA;
    private final TextProfile profileB;

    public AnalysisResult(double similarity, TextProfile profileA, TextProfile profileB) {
        this.similarity = similarity;
        this.profileA = profileA;
        this.profileB = profileB;
    }

    public double getSimilarity() { return similarity; }
    public TextProfile getProfileA() { return profileA; }
    public TextProfile getProfileB() { return profileB; }
    public String getConclusion() { return "Hi"; }

    /**
     * Extracts the top 10 most frequent words across both text profiles
     * and formats them into a 2D matrix for the UI table.
     */
    public Object[][] getTopWords() {
        // Direct access to public wordFreq fields
        Map<String, Integer> freqA = (profileA != null) ? profileA.wordFreq : null;
        Map<String, Integer> freqB = (profileB != null) ? profileB.wordFreq : null;

        // 1. Gather all unique words across both profiles
        Set<String> allWords = new HashSet<>();
        if (freqA != null) allWords.addAll(freqA.keySet());
        if (freqB != null) allWords.addAll(freqB.keySet());

        // 2. Build full list of comparisons and calculate individual metrics
        List<WordComparison> fullList = new ArrayList<>();
        for (String word : allWords) {
            int countA = (freqA != null) ? freqA.getOrDefault(word, 0) : 0;
            int countB = (freqB != null) ? freqB.getOrDefault(word, 0) : 0;

            // Simple proportional similarity (closer counts = higher similarity score)
            double wordSim = 0.0;
            if (countA > 0 || countB > 0) {
                wordSim = (double) Math.min(countA, countB) / Math.max(countA, countB);
            }

            fullList.add(new WordComparison(word, countA, countB, wordSim));
        }

        // 3. Sort by total frequency descending and limit to the top 10 elements
        List<WordComparison> top10List = fullList.stream()
                .sorted((w1, w2) -> Integer.compare(w2.getTotalCount(), w1.getTotalCount()))
                .limit(10)
                .collect(Collectors.toList());

        // 4. Map the top 10 elements into the 2D Object array
        Object[][] dataMatrix = new Object[top10List.size()][4];
        for (int i = 0; i < top10List.size(); i++) {
            WordComparison item = top10List.get(i);
            dataMatrix[i][0] = item.getWord();
            dataMatrix[i][1] = item.getCountA();
            dataMatrix[i][2] = item.getCountB();
            dataMatrix[i][3] = item.getSimilarity();
        }

        return dataMatrix;
    }

    // Static inner helper class encapsulating row data
    private static class WordComparison {
        private final String word;
        private final int countA;
        private final int countB;
        private final double similarity;

        public WordComparison(String word, int countA, int countB, double similarity) {
            this.word = word;
            this.countA = countA;
            this.countB = countB;
            this.similarity = similarity;
        }

        public String getWord() { return word; }
        public int getCountA() { return countA; }
        public int getCountB() { return countB; }
        public double getSimilarity() { return similarity; }

        // Helper to find the highest occurring word across both texts combined
        public int getTotalCount() { return countA + countB; }
    }
}
