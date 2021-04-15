package structures;

public class AlphaPair {
    private final double alpha;
    private final int iteration;

    public AlphaPair(final double alpha, final int iteration) {
        this.alpha = alpha;
        this.iteration = iteration;
    }

    public double getAlpha() {
        return alpha;
    }

    public int getIterations() {
        return iteration;
    }
}
