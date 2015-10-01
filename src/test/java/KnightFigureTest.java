import org.junit.Before;
import org.junit.Test;

public class KnightFigureTest extends CommonFigureTest {
    private Board board;

    @Before
    public void setUp() throws Exception {
        board = new Board(5, 5);
    }

    @Test
    public void shouldTellPositionsUnderThreat() throws Exception {
        verifyPositionsUnderThreat(getLeftUpperCorner(),
                Position.create(2, 3), Position.create(3, 2));

        verifyPositionsUnderThreat(getRightUpperCorner(),
                Position.create(3, board.getMaxColumns() - 1),
                Position.create(2, board.getMaxColumns() - 2));

        verifyPositionsUnderThreat(getRightDownCorner(),
                Position.create(board.getMaxRows() - 2, board.getMaxColumns() - 1),
                Position.create(board.getMaxRows() - 1, board.getMaxColumns() - 2));

        verifyPositionsUnderThreat(getLeftDownCorner(),
                Position.create(board.getMaxRows() - 1, 3),
                Position.create(board.getMaxRows() - 2, 2));
    }

    @Override
    protected Figure getFigure() {
        return Figure.KNIGHT;
    }

    @Override
    protected Board getBoard() {
        return board;
    }
}