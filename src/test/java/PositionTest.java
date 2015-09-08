import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PositionTest {
    @Test
    public void shouldBeEqualIfRowsAndNumbersAreEqual() throws Exception {
        Position p1 = new Position(1, 2);
        Position p2 = new Position(1, 2);
        assertEquals(p1, p2);
    }

    @Test
    public void shouldNotBeEqualIfRowsOrNumbersAreDifferent() throws Exception {
        Position p1 = new Position(1, 2);
        Position p2 = new Position(1, 3);
        Position p3 = new Position(2, 2);

        assertNotEquals(p1, p2);
        assertNotEquals(p1, p3);
    }
}