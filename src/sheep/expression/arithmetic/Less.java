package sheep.expression.arithmetic;

import sheep.expression.Expression;

class Less extends Arithmetic {
    private Expression[] arguments;

    public Less(Expression[] arguments) {
        super("<", arguments);
    }

    @Override
    protected long perform(long[] arguments) {
        if (arguments == null || arguments.length < 2) {
            throw new IllegalArgumentException("Requires argument != null and not empty");
        }

        for (int i = 0; i < arguments.length - 1; i++) {
            if (arguments[i] >= arguments[i + 1]) {
                return 0;
            }
        }

        return 1;
    }
}

