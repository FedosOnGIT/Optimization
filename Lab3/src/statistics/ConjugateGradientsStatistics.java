package statistics;

public class ConjugateGradientsStatistics extends Statistics {
    private Double condA;

    @Override
    public void reset() {
        super.reset();
        condA = null;
    }

    public Double getCondA() {
        return condA;
    }

    public void setCondA(Double condA) {
        this.condA = condA;
    }

    public Statistics withCondA(Double condA) {
        setCondA(condA);
        return this;
    }
}
