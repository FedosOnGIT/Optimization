package statistics;

public class Statistics {
    private Integer n;
    private Long iterations;
    private Double absoluteError;
    private Double ratioError;

    public void reset() {
        n = null;
        iterations = null;
        absoluteError = null;
        ratioError = null;
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
        ++this.iterations;
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
}
