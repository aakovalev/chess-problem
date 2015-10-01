import org.junit.Before;
import org.junit.Test;

public class RookFigureTest extends CommonFigureTest {
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
                Position.create(1, 2), Position.create(1, 3),
                Position.create(2, 1), Position.create(3, 1));

        verifyPositionsUnderThreat(getRightUpperCorner(),
                Position.create(1, 1), Position.create(1, 2),
                Position.create(2, 3), Position.create(3, 3));

        verifyPositionsUnderThreat(getRightDownCorner(),
                Position.create(3, 1), Position.create(3, 2),
                Position.create(1, 3), Position.create(2, 3));

        verifyPositionsUnderThreat(getLeftDownCorner(),
                Position.create(1, 1), Position.create(2, 1),
                Position.create(3, 2), Position.create(3, 3));

        verifyPositionsUnderThreat(middle,
                Position.create(1, 2), Position.create(3, 2),
                Position.create(2, 1), Position.create(2, 3));
    }

    @Override
    protected Figure getFigure() {
        return Figure.ROOK;
    }

    @Override
    protected Board getBoard() {
        return board;
    }
}