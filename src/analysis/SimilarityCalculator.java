package analysis;

public class SimilarityCalculator {

    public static double compare(
            double a,
            double b) {

        if(a == 0 && b == 0)
            return 100;

        double diff =
                Math.abs(a-b);

        double max =
                Math.max(a,b);

        return 100.0 *
                (1.0 - diff/max);
    }
}