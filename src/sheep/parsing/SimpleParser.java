package sheep.parsing;

import sheep.expression.CoreFactory;
import sheep.expression.Expression;
import sheep.expression.ExpressionFactory;
import sheep.expression.InvalidExpression;

import java.util.regex.Pattern;

/**
 * Parser of basic expressions and arithmetic expressions.
 */
public class SimpleParser implements Parser {
    private ExpressionFactory factory;

    /**
     * Construct a new parser. Parsed expressions are constructed using the expression factory.
     * @param factory Factory used to construct parsed expressions.
     */
    public SimpleParser(ExpressionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Expression parse(String input) throws ParseException {

        input = input.trim();

        if (input.isEmpty()) {
            return this.factory.createEmpty();
        }

        try {
            long num = Long.parseLong(input);
            return this.factory.createConstant(num);
        } catch (NumberFormatException e) {
            // Keep parsing because it's not a number
        }

        String[] operators = {"=", "<", "+", "-", "*", "/"};
        for (String operator : operators) {
            if (input.contains(operator)) {
                // Split input into array of strings
                String[] inputComponents = input.split(Pattern.quote(operator));
                // Parse each string in the array and add to an expression array
                Expression[] arguments = new Expression[inputComponents.length];
                for (int i = 0; i < inputComponents.length; i++) {
                    arguments[i] = parse(inputComponents[i]);
                }
                try {
                    return this.factory.createOperator(operator, arguments);
                } catch (InvalidExpression e) {
                    // Keep parsing because can't create operator
                }
            }
        }

        if (input.chars().allMatch(c -> Character.isAlphabetic(c) || Character.isDigit(c))) {
            return this.factory.createReference(input);
        }

        throw new ParseException();
    }
}
