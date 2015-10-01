import org.junit.Before;
import org.junit.Test;

public class KingFigureTest extends CommonFigureTest {
    private Board board;
    private Position middle;

    @Before
    public void setUp() throws Exception {
        board = new Board(5, 5);
        middle = Position.create(3, 3);
    }

    @Test
    public void shouldTellPositionsUnderThreat() throws Exception {
        verifyPositionsUnderThreat(
                getLeftUpperCorner(),
                Position.create(1, 2), Position.create(2, 1), Position.create(2, 2));

        verifyPositionsUnderThreat(getRightUpperCorner(),
                Position.create(1, board.getMaxColumns() - 1),
                Position.create(2, board.getMaxColumns() - 1),
                Position.create(2, board.getMaxColumns()));

        verifyPositionsUnderThreat(getRightDownCorner(),
                Position.create(board.getMaxRows() - 1, board.getMaxColumns()),
                Position.create(board.getMaxRows() - 1, board.getMaxColumns() - 1),
                Position.create(board.getMaxRows(), board.getMaxColumns() - 1));

        verifyPositionsUnderThreat(getLeftDownCorner(),
                Position.create(board.getMaxRows() - 1, 1),
                Position.create(board.getMaxRows() - 1, 2),
                Position.create(board.getMaxRows(), 2));

        verifyPositionsUnderThreat(middle,
                Position.create(middle.getRow() - 1, middle.getColumn() - 1),
                Position.create(middle.getRow() - 1, middle.getColumn()),
                Position.create(middle.getRow() - 1, middle.getColumn() + 1),
                Position.create(middle.getRow(), middle.getColumn() + 1),
                Position.create(middle.getRow() + 1, middle.getColumn() + 1),
                Position.create(middle.getRow() + 1, middle.getColumn()),
                Position.create(middle.getRow() + 1, middle.getColumn() - 1),
                Position.create(middle.getRow(), middle.getColumn() - 1));
    }

    @Override
    protected Figure getFigure() {
        return Figure.KING;
    }

    @Override
    protected Board getBoard() {
        return board;
    }
}