import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AbstractFigureTest {
    @Test
    public void shouldEqualsWhenTypeAndPositionAreTheSame() throws Exception {
        Figure kingOne = new KingFigure();
        kingOne.setPosition(new Position(1, 3));

        Figure kingTwo = new KingFigure();
        kingTwo.setPosition(new Position(1, 3));

        assertEquals(kingOne, kingTwo);
        assertEquals(kingTwo, kingOne);
    }

    @Test
    public void shouldNotEqualIfTypesAreNotTheSame() throws Exception {
        Figure king = new KingFigure();
        king.setPosition(new Position(2, 3));

        Figure bishop = new BishopFigure();
        bishop.setPosition(new Position(2, 3));

        assertNotEquals(king, bishop);
        assertNotEquals(bishop, king);
    }

    @Test
    public void shouldNotEqualIfPositionsAreNotTheSame() throws Exception {
        Figure kingOne = new KingFigure();
        kingOne.setPosition(new Position(3,4));

        Figure kingTwo = new KingFigure();
        kingTwo.setPosition(new Position(4, 3));

        assertNotEquals(kingOne, kingTwo);
        assertNotEquals(kingTwo, kingOne);
    }
}