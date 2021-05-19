public class Statistics {
    private Integer n;
    private Integer k;
    private Long iterations;
    private Double absoluteError;
    private Double ratioError;
    private Double condA;

    public void reset() {
        n = null;
        k = null;
        iterations = 0L;
        absoluteError = null;
        ratioError = null;
        condA = null;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public Statistics withN(Integer n) {
        setN(n);
        return this;
    }

    public Integer getK() {
        return k;
    }

    public void setK(Integer k) {
        this.k = k;
    }

    public Statistics withK(Integer k) {
        setK(k);
        return this;
    }

    public Long getIterations() {
        return iterations;
    }

    public void setIterations(Long iterations) {
        this.iterations = iterations;
    }

    public Statistics withIterations(Long iterations) {
        setIterations(iterations);
        return this;
    }

    public void incIterations() {
        ++iterations;
    }

    public Double getAbsoluteError() {
        return absoluteError;
    }

    public void setAbsoluteError(Double absoluteError) {
        this.absoluteError = absoluteError;
    }

    public Statistics withAbsoluteError(Double absoluteError) {
        setAbsoluteError(absoluteError);
        return this;
    }

    public Double getRatioError() {
        return ratioError;
    }

    public void setRatioError(Double ratioError) {
        this.ratioError = ratioError;
    }

    public Statistics withRatioError(Double ratioError) {
        setRatioError(ratioError);
        return this;
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
