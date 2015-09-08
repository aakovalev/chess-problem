import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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

        board.place(new KingFigure(), thePosition);

        Set<Position> occupiedPositions = board.getOccupiedPositions();
        assertThat(occupiedPositions, hasSize(1));
        assertThat(occupiedPositions, hasItem(thePosition));
    }

    @Test
    public void whenFigureIsPlacedAtTheBoardItMarksThreatenedPositions()
            throws Exception {

        Board board = new Board(5, 5);
        Position position = new Position(3, 3);

        board.place(new KingFigure(), position);

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

        board.place(new KingFigure(), position);

        Set<Position> availablePositions = board.findPositionsToPlace();
        assertThat(availablePositions, hasSize(5));
        assertThat(availablePositions, hasItems(
                new Position(3, 1), new Position(3, 2), new Position(3, 3),
                new Position(1, 3), new Position(2, 3)));
    }

    @Test
    public void shouldEqualIfMatchesSizeFigureTypesAndPositionOccupied()
            throws Exception {

        Board oneBoard = new Board(5, 5);
        Board anotherBoard = new Board(5, 5);

        Position onePosition = new Position(1, 1);
        Position anotherPosition = new Position(3, 3);

        oneBoard.place(new KingFigure(), onePosition);
        oneBoard.place(new KingFigure(), anotherPosition);
        anotherBoard.place(new KingFigure(), onePosition);
        anotherBoard.place(new KingFigure(), anotherPosition);

        assertEquals(oneBoard, anotherBoard);
    }

    @Test
    public void shouldNotEqualsIfSizeAreDifferent() throws Exception {
        Board oneBoard = new Board(5, 5);
        Board anotherBoard = new Board(3, 3);

        assertNotEquals(oneBoard, anotherBoard);
    }

    @Test
    public void shouldNotEqualsIfFiguresPlacedAtDifferentPositions() throws Exception {
        Board oneBoard = new Board(5, 5);
        Board anotherBoard = new Board(5, 5);

        oneBoard.place(new KingFigure(), new Position(3, 3));
        anotherBoard.place(new KingFigure(), new Position(1, 1));

        assertNotEquals(oneBoard, anotherBoard);
    }

    @Test(expected = OutOfBoardPosition.class)
    public void whenFigureIsNotWithinTheBoardThenNotifiesClient() throws Exception {
        Board board = new Board(2, 3);

        board.place(new KingFigure(), getOutOfBoardPosition(board));
    }

    private Position getOutOfBoardPosition(Board board) {
        return new Position(board.getMaxRows() + 1, board.getMaxColumns() + 1);
    }
}