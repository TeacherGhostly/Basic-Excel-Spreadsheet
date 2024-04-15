package sheep.sheets;

import sheep.core.SheetUpdate;
import sheep.core.SheetView;
import sheep.core.UpdateResponse;
import sheep.core.ViewElement;
import sheep.expression.Expression;
import sheep.parsing.ParseException;
import sheep.parsing.Parser;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Spreadsheet that displays the expressions it holds without evaluating the expressions.
 * A display sheet can hold expressions, but it must not attempt to evaluate the expression to a value.
 * For example, if the expression A1 is inserted, then A1 must be displayed verbatim rather than displaying
 * the value stored in A1.
 */
public class DisplaySheet implements SheetView, SheetUpdate {
    private Parser parser;
    private Expression defaultExpression;
    private int rows;
    private int columns;

    private Map<String, Expression> updatedCells = new HashMap<>();

    /**
     * Construct a new display spreadsheet of the specified size.
     * @param parser A parser to use for parsing any updates to the sheet.
     * @param defaultExpression The default expression with which to populate the empty sheet.
     * @param rows Amount of rows for the new sheet.
     * @param columns Amount of columns for the new sheet.
     */
    public DisplaySheet(Parser parser,
                        Expression defaultExpression,
                        int rows,
                        int columns) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("Requires: rows > 0, columns > 0");
        }
        this.parser = parser;
        this.defaultExpression = defaultExpression;
        this.rows = rows;
        this.columns = columns;
    }

    /**
     * Attempt to update a cell in the position.
     * The input string will attempt to be parsed using the sheet's parser. If the string cannot be parsed,
     * then the update response must fail with "Unable to parse: [input]".
     * otherwise the sheet must be updated to render the parsed expression.
     * Display sheets must not attempt to evaluate the parsed expressions.
     * @param row The row index to update.
     * @param column The column index to update.
     * @param input The value as a string to replace within the sheet.
     * @return  Whether the update was successful or not with error details.
     */
    public UpdateResponse update(int row, int column, String input) {
        if (row < 0 || row >= getRows() || column < 0 || column >= getColumns()) {
            throw new IllegalArgumentException(
                    "Requires: 0 ≤ row < getRows(), 0 ≤ column < getColumns()");
        }

        try {
            Expression content = this.parser.parse(input);
            String position = "" + row + column;
            updatedCells.put(position, content);
            return UpdateResponse.success();
        } catch (ParseException e) {
            return UpdateResponse.fail("Unable to parse: " + input);
        }
    }

    /**
     * The number of rows in the sheet.
     * @return The number of rows in the sheet.
     */
    public int getRows() {
        return this.rows;
    }

    /**
     * The number of columns in the sheet.
     * @return The number of columns in the sheet.
     */
    public int getColumns() {
        return this.columns;
    }

    /**
     * Determine the value to display at this cell.
     * The value to display is either
     * the default expression passed to the constructor,
     * or, if a cell has been successfully updated by
     * update(int, int, String), the expression constructed by the input string.
     * The content must match the Expression.render() of the corresponding expression.
     * @param row The row index of the cell.
     * @param column The column index of the cell.
     * @return A ViewElement that details how to render the cell's formula.
     */
    public ViewElement valueAt(int row, int column) {
        if (row < 0 || row >= getRows() || column < 0 || column >= getColumns()) {
            throw new IllegalArgumentException(
                    "Requires: 0 ≤ row < getRows(), 0 ≤ column < getColumns()");
        }

        String position = "" + row + column;
        Expression defaultContent = this.defaultExpression;

        if (updatedCells.containsKey(position)) {
            Expression content = updatedCells.get(position);
            return new ViewElement(content.render(), "white", "black");
        }

        return new ViewElement(defaultContent.render(), "white", "black");
    }

    /**
     * Determine the formula to display at this cell.
     * For any cell,
     * a display sheet will render the same formula and value as formulae are not evaluated
     * to a value (see valueAt(int, int)).
     * @param row The row index of the cell.
     * @param column The column index of the cell.
     * @return A ViewElement that details how to render the cell's formula.
     */
    public ViewElement formulaAt(int row, int column) {
        if (row < 0 || row >= getRows() || column < 0 || column >= getColumns()) {
            throw new IllegalArgumentException(
                    "Requires: 0 ≤ row < getRows(), 0 ≤ column < getColumns()");
        }

        String position = "" + row + column;
        Expression expression = this.defaultExpression;

        if (updatedCells.containsKey(position)) {
            Expression content = updatedCells.get(position);
            return new ViewElement(content.render(), "white", "black");
        }

        String content = expression.render();
        return new ViewElement(content, "white", "black");
    }

}
