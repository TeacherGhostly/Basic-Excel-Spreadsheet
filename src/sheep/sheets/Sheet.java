package sheep.sheets;

import sheep.core.SheetUpdate;
import sheep.core.SheetView;
import sheep.core.UpdateResponse;
import sheep.core.ViewElement;
import sheep.expression.Expression;
import sheep.expression.TypeError;
import sheep.expression.arithmetic.Arithmetic;
import sheep.parsing.ParseException;
import sheep.parsing.Parser;

import java.lang.reflect.Type;
import java.util.*;

/**
 * Spreadsheet that evaluates its expressions and updates dependant cells.
 * Sheet is an implementation of a spreadsheet
 * capable of evaluating its expressions.
 * A sheet consists of cells in a fixed number of rows and columns.
 * Each cell location of a spreadsheet has a formula and a value.
 * The formula is what has been written in the cell by a
 * user whereas the value is what value the cell contains.
 */
public class Sheet implements SheetView, SheetUpdate {
    private Parser parser;
    private Map<String, Expression> builtins;
    private Expression defaultExpression;
    private CellLocation[][] cells;
    private int rows;
    private int columns;

    private Map<CellLocation, Expression> updatedCells = new HashMap<>();
    private Map<String, Expression> state = new HashMap<>();

    Sheet(
            Parser parser, Map<String, Expression> builtins,
            Expression defaultExpression, int rows, int columns) {
        this.parser = parser;
        this.builtins = builtins;
        this.defaultExpression = defaultExpression;
        this.cells = new CellLocation[rows][columns];
        this.rows = rows;
        this.columns = columns;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                CellLocation location = new CellLocation(i, j);
                Expression oldCell = updatedCells.get(location);
                try {
                    update(location, this.defaultExpression);
                } catch (TypeError e) {
                    updatedCells.put(location, oldCell);
                }
            }
        }
    }

    /**
     * The number of rows for this spreadsheet.
     * @return The number of rows for this spreadsheet.
     */
    public int getRows() {
        return this.rows;
    }

    /**
     * The number of columns for this spreadsheet.
     * @return The number of columns for this spreadsheet.
     */
    public int getColumns() {
        return this.columns;
    }

    /**
     * Insert an expression into a cell location, updating the sheet as required.
     * After calling this function, the spreadsheet should update such that
     * The result of calling formulaAt(CellLocation)
     * for the given cell location returns the given expression.
     * The result of calling valueAt(CellLocation) for the given cell location returns the
     * value of the given expression.
     * Any cell that directly, or indirectly,
     * utilizes the value of the given cell is updated such that calling
     * valueAt(CellLocation) will return an appropriate result for the new value at this cell.
     * If a TypeError is thrown at any point during the update of this cell or any dependant cells,
     * the sheet should return to the same state as before this method was called.
     * @param location A cell location to insert the expression into the sheet.
     * @param cell cell An expression to insert at the given location.
     * @throws TypeError If the evaluation of the inserted cell or any of its usages results
     * in a TypeError being thrown.
     */
    public void update(CellLocation location, Expression cell) throws TypeError {
        updatedCells.put(location, cell);
        String coordinates = location.toString();
        state.put(coordinates, cell);
    }

    /**
     * Attempt to update the cell at row and column within the sheet with the given input.
     * The input string will be parsed using the sheet's Parser. If the string cannot be parsed,
     * then the update response must fail with "Unable to parse: [input]".
     * Once parsed, the method should function the same as update(CellLocation, Expression).
     * If a TypeError occurs, then the update response must fail with "Type error: [e]" where e
     * is the result of calling Throwable.toString() on the thrown exception.
     * Otherwise, the spreadsheet should update as per update(CellLocation, Expression)
     * and return a successful UpdateResponse.
     * @param row The row index to update.
     * @param column The column index to update.
     * @param input The value as a string to replace within the sheet.
     * @return Information about the status of performing the update.
     */
    public UpdateResponse update(int row, int column, String input) {
        if (row < 0 || row >= getRows() || column < 0 || column >= getColumns()) {
            throw new IllegalArgumentException(
                    "Row and column indices must be within the valid range");
        }

        CellLocation location = new CellLocation(row, column);
        Expression oldCell = updatedCells.get(location);

        try {
            Expression content = this.parser.parse(input);

            update(location, content);
            return UpdateResponse.success();

        } catch (ParseException e) {
            return UpdateResponse.fail("Unable to parse: " + input);
        } catch (TypeError e) {
            updatedCells.put(location, oldCell);
            return UpdateResponse.fail("Type error: [e]");
        }
    }

    /**
     * The value expression currently stored at the location in the spreadsheet.
     * The value expression is the result of calling
     * Expression.value(Map) on the corresponding formula.
     * The Expression.value(Map) must not be called in this method,
     * it should be called when a formula is updated in update(CellLocation, Expression).
     * @param location A cell location within the spreadsheet.
     * @return The value expression at the given cell location.
     */
    public Expression valueAt(CellLocation location) {
        return updatedCells.get(location);
    }

    /**
     * The value to render at this location.
     * The content of the ViewElement should correspond to the result of the Expression.render()
     * method on valueAt(CellLocation).
     * The foreground and background colours may be any compatible colours.
     * @param row The row index of the cell.
     * @param column The column index of the cell.
     * @return The value to render at this location.
     */
    public ViewElement valueAt(int row, int column) {
        CellLocation cell = new CellLocation(row, column);
        Expression expression = valueAt(cell);
        String renderedValue = expression.render();
        try {
            return new ViewElement(
                    expression.value(state).render(), "white", "black");
        }   catch (TypeError e) {
            return new ViewElement(
                    expression.render(), "white", "black");
        }

    }

    /**
     * The formula expression currently stored at the location in the spreadsheet.
     * @param location A cell location within the spreadsheet.
     * @return The formula expression at the given cell location.
     */
    public Expression formulaAt(CellLocation location) {
        return updatedCells.get(location);
    }

    /**
     * The formula to render at this location. The content of the ViewElement
     * should correspond to
     * the result of the Expression.render() method on formulaAt(CellLocation).
     * The foreground and background colours may be any compatible colours.
     * @param row The row index of the cell.
     * @param column The column index of the cell.
     * @return The formula to render at this location.
     */
    public ViewElement formulaAt(int row, int column) {
        CellLocation cell = new CellLocation(row, column);
        Expression expression = formulaAt(cell);
        return new ViewElement(expression.render(), "white", "black");
    }

    /**
     * Determine which cells use the formula at the given cell location
     * That is, for a given location, find all the cells where the given location
     * is a transitive dependency for that cell.
     * For example,
     * if the expressions at A1 and A2 have A3 as a dependency then the result of this method for
     * A3 should be a set containing A1 and A2. If A3 has a dependency on A4, then A4 is used by A1, A2,
     * and A3 because A4 is used by A3 to determine its value which is transitively used by A1 and A2
     * to determine their values.
     * @param location A cell location within the spreadsheet.
     * @return All the cells which use the given cell as a dependency.
     */
    public Set<CellLocation> usedBy(CellLocation location) {
        Set<CellLocation> usedByCells = new HashSet<>();
        Queue<CellLocation> queue = new LinkedList<>();

        // Add initial location to the queue
        queue.add(location);

        while (!queue.isEmpty()) {
            CellLocation current = queue.poll();

            for (int i = 0; i < getRows(); i++) {
                for (int j = 0; j < getColumns(); j++) {
                    CellLocation cell = new CellLocation(i, j);
                    Expression formula = formulaAt(cell);
                    Set<String> dependencies = formula.dependencies();

                    if (dependencies.contains(current.toString())) {
                        usedByCells.add(cell);

                        // If the cell is not already in the queue, add it
                        if (!queue.contains(cell)) {
                            queue.add(cell);
                        }
                    }
                }
            }
        }

        return usedByCells;
    }


}
