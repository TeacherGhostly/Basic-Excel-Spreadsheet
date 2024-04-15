package sheep.sheets;

import sheep.expression.CoreFactory;

import java.util.Optional;

/**
 * A location of a cell within a grid. This class represents a location via a row, column coordinate system.
 * Notably columns are represented as character, e.g. in cell A1, the column is 0 and the row is 1.
 */
public class CellLocation {
    private int row;
    private int column;

    /**
     * Construct a new cell location at the given row and column.
     * @param row A number representing the row number.
     * @param column column - A character representing the column.
     */
    public CellLocation(int row, char column) {
        this.row = row;
        this.column = column - 'A';
    }

    /**
     * Construct a new cell location at the given row and column.
     * @param row A number representing the row number.
     * @param column A number representing the column
     */
    public CellLocation(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Attempt to parse a string as a reference to a cell location.
     * If the string is not a reference to a cell location, returns Optional.empty().
     * The format of the reference is a single uppercase character followed by an integer
     * without spaces and without extraneous characters after the integer or before the character.
     * @param ref A string that may represent a cell location.
     * @return  An optional containing a cell reference if the string is a reference, otherwise the empty optional.
     */
    public static Optional<CellLocation> maybeReference(String ref) {
        if (ref == null || ref.length() < 2) {
            return Optional.empty();
        }

        char columnChar = ref.charAt(0);
        if (columnChar < 'A' || columnChar > 'Z') {
            return Optional.empty();
        }

        try {
            int row = Integer.parseInt(ref.substring(1));
            return Optional.of(new CellLocation(row, columnChar));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    /**
     * The row number of this cell location
     * @return The row number of this cell location
     */
    public int getRow() {
        return this.row;
    }

    /**
     * The column number of this cell location.
     * @return The column number of this cell location.
     */
    public int getColumn() {
        return this.column;
    }

    @Override
    public boolean equals(Object obj) {
        CellLocation objCell = (CellLocation) obj;
        return this.row == objCell.getRow() && this.column == objCell.getColumn();
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(row);
        result = 13 * result + Integer.hashCode(column);
        return result;
    }

    @Override
    public String toString() {
        char columnChar = (char) ('A' + column);
        return "" + columnChar + row;
    }

}
