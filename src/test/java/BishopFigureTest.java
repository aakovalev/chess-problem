import org.junit.Before;
import org.junit.Test;

public class BishopFigureTest extends CommonFigureTest {
    private Board board;
    private Position middle;

    @Before
    public void setUp() throws Exception {
        board = new Board(3, 3);
        middle = new Position(2, 2);
    }

    @Test
    public void shouldTellPositionsUnderThreat() throws Exception {
        verifyPositionsUnderThreat(getLeftUpperCorner(),
                new Position(2, 2), new Position(3, 3));
        verifyPositionsUnderThreat(getRightUpperCorner(),
                new Position(2, 2), new Position(3, 1));
        verifyPositionsUnderThreat(getRightDownCorner(),
                new Position(2, 2), new Position(1, 1));
        verifyPositionsUnderThreat(getLeftDownCorner(),
                new Position(2, 2), new Position(1, 3));
        verifyPositionsUnderThreat(middle,
                new Position(1, 1), new Position(1, 3),
                new Position(3, 1), new Position(3, 3));
    }

    @Override
    protected Figure createFigure() {
        return new BishopFigure();
    }

    @Override
    protected Board getBoard() {
        return board;
    }
}