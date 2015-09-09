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
                new Position(2, 3), new Position(3, 2));

        verifyPositionsUnderThreat(getRightUpperCorner(),
                new Position(3, board.getMaxColumns() - 1),
                new Position(2, board.getMaxColumns() - 2));

        verifyPositionsUnderThreat(getRightDownCorner(),
                new Position(board.getMaxRows() - 2, board.getMaxColumns() - 1),
                new Position(board.getMaxRows() - 1, board.getMaxColumns() - 2));

        verifyPositionsUnderThreat(getLeftDownCorner(),
                new Position(board.getMaxRows() - 1, 3),
                new Position(board.getMaxRows() - 2, 2));
    }

    @Override
    protected Figure createFigure() {
        return new KnightFigure();
    }

    @Override
    protected Board getBoard() {
        return board;
    }
}