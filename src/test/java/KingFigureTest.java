import org.junit.Before;
import org.junit.Test;

public class KingFigureTest extends CommonFigureTest {
    private Board board;
    private Position rightUpperCorner;
    private Position leftUpperCorner;
    private Position rightDownCorner;
    private Position leftDownCorner;
    private Position middle;

    @Before
    public void setUp() throws Exception {
        board = new Board(5, 5);
        leftUpperCorner = new Position(1, 1);
        rightUpperCorner = new Position(1, board.getMaxColumns());
        rightDownCorner = new Position(board.getMaxRows(), board.getMaxColumns());
        leftDownCorner = new Position(board.getMaxRows(), 1);
        middle = new Position(3, 3);
    }

    @Test
    public void shouldTellPositionsUnderThreat() throws Exception {
        verifyPositionsUnderThreat(
                leftUpperCorner,
                new Position(1, 2), new Position(2, 1), new Position(2, 2));

        verifyPositionsUnderThreat(rightUpperCorner,
                new Position(1, board.getMaxColumns() - 1),
                new Position(2, board.getMaxColumns() - 1),
                new Position(2, board.getMaxColumns()));

        verifyPositionsUnderThreat(rightDownCorner,
                new Position(board.getMaxRows() - 1, board.getMaxColumns()),
                new Position(board.getMaxRows() - 1, board.getMaxColumns() - 1),
                new Position(board.getMaxRows(), board.getMaxColumns() - 1));

        verifyPositionsUnderThreat(leftDownCorner,
                new Position(board.getMaxRows() - 1, 1),
                new Position(board.getMaxRows() - 1, 2),
                new Position(board.getMaxRows(), 2));

        verifyPositionsUnderThreat(middle,
                new Position(middle.getRow() - 1, middle.getColumn() - 1),
                new Position(middle.getRow() - 1, middle.getColumn()),
                new Position(middle.getRow() - 1, middle.getColumn() + 1),
                new Position(middle.getRow(), middle.getColumn() + 1),
                new Position(middle.getRow() + 1, middle.getColumn() + 1),
                new Position(middle.getRow() + 1, middle.getColumn()),
                new Position(middle.getRow() + 1, middle.getColumn() - 1),
                new Position(middle.getRow(), middle.getColumn() - 1));
    }

    @Override
    protected Figure createFigure() {
        return new KingFigure();
    }

    @Override
    protected Board getBoard() {
        return board;
    }
}