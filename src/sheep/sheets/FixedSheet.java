package sheep.sheets;

import sheep.core.SheetUpdate;
import sheep.core.SheetView;
import sheep.core.UpdateResponse;
import sheep.core.ViewElement;

/**
 * Spreadsheet that has fixed values in every cell. Useful for initial development.
 */
public class FixedSheet implements SheetView, SheetUpdate {
    /**
     * Attempt to update a cell in the position.
     * Whenever the update method is called on a fixed sheet, it must report that the update failed because the sheet is view only.
     * The returned UpdateResponse must fail because "Sheet is view only.".
     * @param row The row index to update.
     * @param column The column index to update.
     * @param input The value as a string to replace within the sheet.
     * @return A failed update as the sheet is view only.
     */
    public UpdateResponse update(int row, int column, String input) {
        return UpdateResponse.fail("Sheet is view only.");
    }

    /**
     * The number of columns in the sheet. A fixed sheet will always have exactly 6 columns.
     * @return 6
     */
    public int getColumns() {
        return 6;
    }

    /**
     * The number of rows in the sheet. A fixed sheet will always have exactly 6 rows.
     * @return 6
     */
    public int getRows() {
        return 6;
    }

    /**
     * Determine the value to display at this cell.
     * A fixed sheet has two types of cells, highlighted and unhighlighted.
     * Highlighted cells should contain the string "W" and have a green background colour. Unhighlighted
     * cells should have no content and a white background colour. Both cells types should have a black foreground.
     * Highlighted cells are between rows 2 and 3 (inclusive) and columns 2 and 3 (inclusive), that is,
     * there are 4 highlighted cells total.
     * @param row The row index of the cell.
     * @param column The column index of the cell.
     * @return An appropriately formatted cell based on whether it is highlighted or not.
     */
    public ViewElement valueAt(int row,
                               int column) {
        if ((row >= 2 && row <= 3) && (column >= 2 && column <= 3)) {
            return new ViewElement("W", "green", "black");
        } else {
            return new ViewElement("", "white", "black");
        }
    }

    /**
     * Determine the formula to display at this cell.
     * A fixed sheet has two types of cells, highlighted and unhighlighted,
     * as per the description of valueAt(int, int).
     * In contrast to the values, formulas of highlighted cells must have the text "GREEN" rather than "W".
     * @param row The row index of the cell.
     * @param column The column index of the cell.
     * @return An appropriately formatted cell based on whether it is highlighted or not.
     */
    public ViewElement formulaAt(int row,
                                 int column) {
        if ((row >= 2 && row <= 3) && (column >= 2 && column <= 3)) {
            return new ViewElement("GREEN", "green", "black");
        } else {
            return new ViewElement("", "white", "black");
        }
    }

}
