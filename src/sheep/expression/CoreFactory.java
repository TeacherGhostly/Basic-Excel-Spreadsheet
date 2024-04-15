package sheep.expression;

import sheep.expression.arithmetic.*;
import sheep.expression.basic.Constant;
import sheep.expression.basic.Nothing;
import sheep.expression.basic.Reference;

/**
 * An expression factory for the core expressions.
 * The core expressions are those which will be a part of assignment one.
 */
public class CoreFactory implements ExpressionFactory {
    @Override
    public Expression createReference(String identifier) {
        if (identifier.isEmpty()) {
            throw new IllegalArgumentException("Requires: identifier != \"\"");
        }
        return new Reference(identifier);
    }

    @Override
    public Expression createConstant(long value) {
        return new Constant(value);
    }

    @Override
    public Expression createEmpty() {
        return new Nothing();
    }


    @Override
    public Expression createOperator(String name, Object[] args) throws InvalidExpression {

        if (args == null || args.length == 0) {
            throw new InvalidExpression("Didn't give any arguments");
        }

        // Convert args to an array of Expressions
        Expression[] arguments = new Expression[args.length];
        for (int i = 0; i < args.length; i++) {
            if (!(args[i] instanceof Expression)) {
                throw new InvalidExpression();
            }
            arguments[i] = (Expression) args[i];
        }

        // Create the appropriate Arithmetic subclass based on the operator name
        return switch (name) {
            case "+" -> Arithmetic.plus(arguments);
            case "-" -> Arithmetic.minus(arguments);
            case "*" -> Arithmetic.times(arguments);
            case "/" -> Arithmetic.divide(arguments);
            case "<" -> Arithmetic.less(arguments);
            case "=" -> Arithmetic.equal(arguments);
            default -> throw new InvalidExpression("Unknown operator name: " + name);
        };
    }


}
