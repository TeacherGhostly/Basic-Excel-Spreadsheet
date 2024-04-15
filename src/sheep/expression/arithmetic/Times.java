package sheep.expression.arithmetic;

import sheep.expression.Expression;

class Times extends Arithmetic {
    private Expression[] arguments;

    public Times(Expression[] arguments) {
        super("*", arguments);
    }

    @Override
    protected long perform(long[] arguments) {
        if (arguments == null || arguments.length == 0) {
            throw new IllegalArgumentException("Requires argument != null and not empty");
        }

        long result = 1;

        for (long argument : arguments) {
            result *= argument;
        }

        return result;
    }
}
