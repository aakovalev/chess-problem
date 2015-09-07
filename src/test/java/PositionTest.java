import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PositionTest {
    @Test
    public void shouldBeEqualIfRowsAndNumbersAreEqual() throws Exception {
        Position p1 = new Position(1, 2);
        Position p2 = new Position(1, 2);
        assertEquals(p1, p2);
    }
}