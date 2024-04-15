package sheep.sheets;

import org.junit.Before;
import org.junit.Test;
import java.util.Optional;
import static org.junit.Assert.*;

public class CellLocationTest {
    private CellLocation cellLocation;

    @Before
    public void setUp() {
        cellLocation = new CellLocation(5, 'B');
    }

    @Test
    public void testMaybeReference() {
        Optional<CellLocation> cellLocationTest = CellLocation.maybeReference("B5");
        assertTrue(cellLocationTest.isPresent());
        assertEquals(cellLocation, cellLocationTest.get());
    }

    @Test
    public void testGetRow() {
        assertEquals(5, cellLocation.getRow());
    }

    @Test
    public void testGetColumn() {
        assertEquals(1, cellLocation.getColumn());
    }

    @Test
    public void testEquals() {
        CellLocation secondCellLocation = new CellLocation(5, 'B');
        assertEquals(cellLocation, secondCellLocation);
    }

    @Test
    public void testHashCode() {
        CellLocation otherCellLocation = new CellLocation(5, 'B');
        assertEquals(cellLocation.hashCode(), otherCellLocation.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("B5", cellLocation.toString());
    }
}
