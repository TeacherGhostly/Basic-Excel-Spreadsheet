package sheep.expression.basic;

import sheep.expression.Expression;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * A constant numeric value.
 */
public class Constant extends Expression {
    private long number;

    /**
     * Construct a new constant to represent the given number.
     * @param number The number to represent as an expression.
     */
    public Constant(long number) {
        this.number = number;
    }

    /**
     * Get the numeric value stored within the constant expression.
     * @return Value stored within the expression.
     */
    public long getValue() {
        return this.number;
    }

    @Override
    public String toString() {
        return "CONSTANT(" + this.number + ")";
    }

    @Override
    public boolean equals(Object object) {
        Constant constantObject = (Constant) object;
        return this == object && this.number == constantObject.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.number);
    }

    @Override
    public Set<String> dependencies() {
        return Collections.emptySet();
    }

    @Override
    public Expression value(Map<String, Expression> state) {
        return this;
    }

    @Override
    public long value() {
        return this.number;
    }

    @Override
    public String render() {
        return String.valueOf(this.number);
    }

}

