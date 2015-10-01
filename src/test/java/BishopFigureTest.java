import org.junit.Before;
import org.junit.Test;

public class BishopFigureTest extends CommonFigureTest {
    private Board board;
    private Position middle;

    @Before
    public void setUp() throws Exception {
        board = new Board(3, 3);
        middle = Position.create(2, 2);
    }

    @Test
    public void shouldTellPositionsUnderThreat() throws Exception {
        verifyPositionsUnderThreat(getLeftUpperCorner(),
                Position.create(2, 2), Position.create(3, 3));
        verifyPositionsUnderThreat(getRightUpperCorner(),
                Position.create(2, 2), Position.create(3, 1));
        verifyPositionsUnderThreat(getRightDownCorner(),
                Position.create(2, 2), Position.create(1, 1));
        verifyPositionsUnderThreat(getLeftDownCorner(),
                Position.create(2, 2), Position.create(1, 3));
        verifyPositionsUnderThreat(middle,
                Position.create(1, 1), Position.create(1, 3),
                Position.create(3, 1), Position.create(3, 3));
    }

    @Override
    protected Figure getFigure() {
        return Figure.BISHOP;
    }

    @Override
    protected Board getBoard() {
        return board;
    }
}