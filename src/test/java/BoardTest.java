import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
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

    @Test
    public void whenFigureIsPlacedAtTheBoardItMarksThreatenedPositions()
            throws Exception {

        Board board = new Board(5, 5);
        Position position = new Position(3, 3);
        Figure figure = new KingFigure(board, position);

        board.place(figure);

        Set<Position> threatenedPositions = board.getThreatenedPositions();
        assertThat(threatenedPositions, hasSize(8));
        assertThat(threatenedPositions, hasItems(
                new Position(2, 2), new Position(2, 3), new Position(2, 4),
                new Position(3, 4), new Position(4, 4), new Position(4, 3),
                new Position(4, 2), new Position(3, 2)));
    }

    @Test
    public void canFindNonThreatenedAndOccupiedPositions() throws Exception {
        Board board = new Board(3, 3);
        Position position = new Position(1, 1);
        Figure figure = new KingFigure(board, position);

        board.place(figure);

        Set<Position> availablePositions = board.findPositionsToPlace();
        assertThat(availablePositions, hasSize(5));
        assertThat(availablePositions, hasItems(
                new Position(3, 1), new Position(3, 2), new Position(3, 3),
                new Position(1, 3), new Position(2, 3)));
    }

    @Test(expected = OutOfBoardPosition.class)
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