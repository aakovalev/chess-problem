import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BoardTest {
    @Test
    public void whenNoFiguresAddedToTheBoardThenNoOccupiedPositions()
            throws Exception {
        Board board = new Board(2, 3);
        assertThat(board.getOccupiedPositions().size(), is(0));
    }

    @Test
    public void whenFigureAddedToBoardItsPositionMarkedOccupied()
            throws Exception {
        Board board = new Board(2, 3);
        Position thePosition = new Position(1, 1);
        Figure figure = FigureType.KING.createFigure(board, thePosition);
        board.place(figure);
        Set<Position> occupiedPositions = board.getOccupiedPositions();
        assertThat(occupiedPositions, hasSize(1));
        assertThat(occupiedPositions, hasItem(thePosition));
    }

    @Test (expected = OutOfBoardPosition.class)
    public void whenFigureIsNotWithinTheBoardThenNotifiesClient() throws Exception {
        Board board = new Board(2, 3);
        Figure figure = FigureType.KING.createFigure(
                board, getOutOfBoardPosition(board));
        board.place(figure);
    }

    private Position getOutOfBoardPosition(Board board) {
        return new Position(board.getMaxRows() + 1, board.getMaxColumns() + 1);
    }
}