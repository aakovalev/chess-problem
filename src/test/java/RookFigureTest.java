import org.junit.Before;
import org.junit.Test;

public class RookFigureTest extends CommonFigureTest {
    private Board board;
    private Position leftUpperCorner;
    private Position rightUpperCorner;
    private Position rightDownCorner;
    private Position leftDownCorner;
    private Position middle;

    @Before
    public void setUp() throws Exception {
        board = new Board(3, 3);
        leftUpperCorner = new Position(1, 1);
        rightUpperCorner = new Position(1, board.getMaxColumns());
        rightDownCorner = new Position(board.getMaxRows(), board.getMaxColumns());
        leftDownCorner = new Position(board.getMaxRows(), 1);
        middle = new Position(2, 2);
    }

    @Test
    public void shouldTellPositionsUnderThreat() throws Exception {
        verifyPositionsUnderThreat(leftUpperCorner,
                new Position(1, 2), new Position(1, 3),
                new Position(2, 1), new Position(3, 1));

        verifyPositionsUnderThreat(rightUpperCorner,
                new Position(1, 1), new Position(1, 2),
                new Position(2, 3), new Position(3, 3));

        verifyPositionsUnderThreat(rightDownCorner,
                new Position(3, 1), new Position(3, 2),
                new Position(1, 3), new Position(2, 3));

        verifyPositionsUnderThreat(leftDownCorner,
                new Position(1, 1), new Position(2, 1),
                new Position(3, 2), new Position(3, 3));

        verifyPositionsUnderThreat(middle,
                new Position(1, 2), new Position(3, 2),
                new Position(2, 1), new Position(2, 3));
    }

    @Override
    protected Figure createFigure() {
        return new RookFigure();
    }

    @Override
    protected Board getBoard() {
        return board;
    }
}