package analysis;

public class AnalysisResult {

    private final double similarity;

    private final TextProfile profileA;

    private final TextProfile profileB;

    public AnalysisResult(
            double similarity,
            TextProfile profileA,
            TextProfile profileB) {

        this.similarity = similarity;
        this.profileA = profileA;
        this.profileB = profileB;
    }

    public double getSimilarity() {
        return similarity;
    }

    public TextProfile getProfileA() {
        return profileA;
    }

    public TextProfile getProfileB() {
        return profileB;
    }
}