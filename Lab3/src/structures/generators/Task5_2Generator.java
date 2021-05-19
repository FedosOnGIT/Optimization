package structures.generators;

public class Task5_2Generator extends Task5_AbstractGenerator {

    public Task5_2Generator(int n) {
        super(n);
    }

    @Override
    protected double generateElement() {
        return -random.nextInt(5);
    }
}
