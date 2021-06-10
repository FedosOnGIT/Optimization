package expressions;

import structures.matrices.Vector;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Variable implements Expression {
    private final static Pattern VARIABLE_PATTERN = Pattern.compile("\\D*(\\d+)");

    private final String variable;
    private final int index;

    public Variable(String variable) {
        this.variable = variable;
        final Matcher matcher = VARIABLE_PATTERN.matcher(variable);
        matcher.find();
        try {
            index = Integer.parseInt(matcher.group(1));
        } catch (IllegalStateException | IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(
                    "Incorrect variable: it should follow such regex = " + VARIABLE_PATTERN.toString()
            );
        }
    }

    @Override
    public Double evaluate(Vector v) {
        if (index > v.size()) {
            throw new IllegalArgumentException("Incorrect input");
        }
        return v.get(index - 1);
    }

    @Override
    public Expression diff(Variable v) {
        if (equals(v)) {
            return Const.ONE;
        }
        return Const.ZERO;
    }

    @Override
    public String toString() {
        return variable;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        return variable.equals(obj.toString());
    }

    @Override
    public int hashCode() {
        return variable.hashCode();
    }

    public static Variable createVariable(int number) {
        return new Variable("x" + number);
    }

}
