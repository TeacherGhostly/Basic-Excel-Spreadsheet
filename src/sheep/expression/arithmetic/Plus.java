package sheep.expression.arithmetic;

import sheep.expression.Expression;

class Plus extends Arithmetic {
    private Expression[] arguments;

    public Plus(Expression[] arguments) {
        super("+", arguments);
    }

    @Override
    protected long perform(long[] arguments) {
        if (arguments == null || arguments.length == 0) {
            throw new IllegalArgumentException("Requires argument != null and not empty.");
        }

        long result = 0;

        for (long argument : arguments) {
            result += argument;
        }

        return result;
    }
}
