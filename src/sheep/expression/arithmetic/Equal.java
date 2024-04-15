package sheep.expression.arithmetic;

import sheep.expression.Expression;

class Equal extends Arithmetic {
    private Expression[] arguments;

    public Equal(Expression[] arguments) {
        super("=", arguments);
    }

    @Override
    protected long perform(long[] arguments) {
        long value = arguments[0];

        for (int i = 1; i < arguments.length; i++) {
            if (arguments[i] != value) {
                return 0;
            }
        }

        return 1;
    }


}
