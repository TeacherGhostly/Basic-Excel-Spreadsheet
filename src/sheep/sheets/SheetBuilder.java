package sheep.sheets;

import sheep.expression.Expression;
import sheep.parsing.Parser;

import java.util.HashMap;
import java.util.Map;

/**
 * Builder pattern to construct Sheet instances.
 * A sheet builder maintains a collection of built-in expressions.
 * These map identifiers to expressions
 * such that any expression within the constructed sheet
 * can reference the identifier and evaluate to the expression.
 * For example, if the identifier 'hundred' was mapped to the number 100,
 * then any cell in the constructed sheet
 * could use 'hundred' in place of 100 in a formula.
 */
public class SheetBuilder {
    private Parser parser;
    private Expression defaultExpression;
    private Map<String, Expression> builtins = new HashMap<>();

    /**
     * Construct an instance of SheetBuilder
     * than will create Sheet instances using the given Parser and Expression instances.
     * @param parser A parser to use for parsing any updates to the sheet.
     * @param defaultExpression The default expression to render.
     */
    public SheetBuilder(Parser parser, Expression defaultExpression) {
        this.parser = parser;
        this.defaultExpression = defaultExpression;
    }

    /**
     * Include a new built-in expression for the given identifier within any sheet constructed by
     * this builder instance.
     * @param identifier A string identifier to be used in the constructed sheet.
     * @param expression The value that the identifier should resolve to within the constructed sheet.
     * @return The current instance of the SheetBuilder.
     */
    public SheetBuilder includeBuiltIn(String identifier, Expression expression) {
        if (CellLocation.maybeReference(identifier).isPresent()) {
            throw new IllegalArgumentException(
                    "Identifier cannot be a valid cell location reference");
        }
        builtins.put(identifier, expression);
        return this;
    }

    /**
     * Construct a new empty sheet with the given number of rows and columns.
     * If the built-ins are updated (i.e. includeBuiltIn(String, Expression) is called)
     * after a sheet has been constructed, this must not affect the constructed sheet.
     * @param rows Amount of rows for the new sheet.
     * @param columns Amount of columns for the sheet.
     * @return A new sheet with the appropriate built-ins and of the specified dimensions.
     */
    public Sheet empty(int rows, int columns) {
        Map<String, Expression> builtinsCopy = new HashMap<>(builtins);
        return new Sheet(this.parser, builtinsCopy, this.defaultExpression, rows, columns);
    }

}
