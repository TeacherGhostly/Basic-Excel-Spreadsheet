package sheep.expression.arithmetic;

import sheep.expression.Expression;

class Minus extends Arithmetic {
    private Expression[] arguments;

    public Minus(Expression[] arguments) {
        super("-", arguments);
    }

    @Override
    protected long perform(long[] arguments) {
        if (arguments == null || arguments.length == 0) {
            throw new IllegalArgumentException("Requires argument != null and not empty");
        }

        long result = arguments[0];

        for (int i = 1; i < arguments.length; i++) {
            result -= arguments[i];
        }

        return result;
    }
}
