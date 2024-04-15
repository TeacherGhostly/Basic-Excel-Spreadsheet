package sheep.expression.basic;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sheep.expression.Expression;
import sheep.expression.TypeError;

import java.util.HashMap;
import java.util.Map;

public class ReferenceTest {
    private Reference reference;
    private Map<String, Expression> state;

    @Before
    public void setUp() {
        reference = new Reference("test");
        state = new HashMap<>();
        state.put("test", new Reference("testValue"));
    }

    @Test
    public void testGetIdentifier() {
        assertEquals("test", reference.getIdentifier());
    }

    @Test
    public void testToString() {
        assertEquals("REFERENCE(test)", reference.toString());
    }

    @Test
    public void testIsReference() {
        assertTrue(reference.isReference());
    }

    @Test
    public void testEquals() {
        Reference secondReference = new Reference("test");
        assertEquals(reference, secondReference);
    }

    @Test
    public void testHashCode() {
        Reference secondReference = new Reference("test");
        assertEquals(reference.hashCode(), secondReference.hashCode());
    }

    @Test
    public void testDependencies() {
        assertTrue(reference.dependencies().contains("test"));
    }

    @Test
    public void testValue() throws TypeError {
        assertEquals(new Reference("testValue"), reference.value(state));
    }

    @Test(expected = TypeError.class)
    public void testValueError() throws TypeError {
        reference.value();
    }

    @Test
    public void testRender() {
        assertEquals("test", reference.render());
    }
}
