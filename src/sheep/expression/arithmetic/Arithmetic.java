package sheep.expression.arithmetic;

import sheep.expression.Expression;
import sheep.expression.TypeError;
import sheep.expression.basic.Constant;

import java.util.*;

/**
 * An arithmetic expression.
 * Performs arithmetic operations on a sequence of sub-expressions.
 */

public abstract class Arithmetic extends Expression {

    private String operator;

    private Expression[] arguments;

    protected Arithmetic(String operator, Expression[] arguments) {
        if (arguments.length > 0) {
            this.operator = operator;
            this.arguments = arguments;
        } else {
            throw new IllegalArgumentException("Argument length must > 0");
        }
    }

    /**
     * Construct a new addition (plus) operation.
     * Requires:
     * arguments.length > 0
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @return A plus expression.
     */
    public static Arithmetic plus(Expression[] arguments) {
        return new Plus(arguments);
    }

    /**
     * Construct a new subtraction (minus) operation.
     * Requires:
     * arguments.length > 0
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @return A minus expression.
     */
    public static Arithmetic minus(Expression[] arguments) {
        return new Minus(arguments);
    }

    /**
     * Construct a new multiplication (times) operation.
     * Requires:
     * arguments.length > 0
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @return A times expression.
     */
    public static Arithmetic times(Expression[] arguments) {
        return new Times(arguments);
    }

    /**
     * Construct a new division (divide) operation.
     * Requires:
     * arguments.length > 0
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @return A divide expression.
     */
    public static Arithmetic divide(Expression[] arguments) {
        return new Divide(arguments);
    }

    /**
     * Construct a new less than (less) operation.
     * Requires:
     * arguments.length > 0
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @return A less expression.
     */
    public static Arithmetic less(Expression[] arguments) {
        return new Less(arguments);
    }

    /**
     * Construct a new equal to (equal) operation.
     * Requires:
     * arguments.length > 0
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @return A equal expression.
     */
    public static Arithmetic equal(Expression[] arguments) {
        return new Equal(arguments);
    }

    @Override
    public Set<String> dependencies() {
        Set<String> dependencies = new HashSet<>();
        for (Expression arg : this.arguments) {
            dependencies.addAll(arg.dependencies());
        }
        return dependencies;
    }

    @Override
    public Expression value(Map<String, Expression> state) throws TypeError {

        int argumentLength = this.arguments.length;
        long[] argumentLongs = new long[argumentLength];

        for (int i = 0; i < argumentLength; i++) {

            Expression content = this.arguments[i].value(state);

            if (!(content instanceof Constant)) {
                throw new TypeError();
            }

            argumentLongs[i] = ((Constant) content).getValue();
        }
        long result = perform(argumentLongs);
        return new Constant(result);
    }

    @Override
    public long value() throws TypeError {
        throw new TypeError();
    }

    protected abstract long perform(long[] arguments);

    @Override
    public String render() {

        List<String> subExpressions = new ArrayList<>();

        for (Expression argument : arguments) {
            subExpressions.add(argument.render());
        }

        return String.join(" " + this.operator + " ", subExpressions);
    }

    @Override
    public String toString() {

        List<String> subExpressions = new ArrayList<>();

        for (Expression argument : arguments) {
            subExpressions.add(argument.render());
        }

        return String.join(" " + this.operator + " ", subExpressions);
    }

}
