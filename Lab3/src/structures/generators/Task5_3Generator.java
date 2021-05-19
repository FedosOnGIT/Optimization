package structures.generators;

import java.util.Random;

public class Task5_3Generator extends Task5_AbstractGenerator {

    public Task5_3Generator(int n) {
        super(n);
    }

    @Override
    protected double generateElement() {
        return random.nextInt(5);
    }
}
