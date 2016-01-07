import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

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
        Position thePosition = Position.create(1, 1);

        board.place(Figure.KING, thePosition);

        Set<Position> occupiedPositions = board.getOccupiedPositions();
        assertThat(occupiedPositions, hasSize(1));
        assertThat(occupiedPositions, hasItem(thePosition));
    }

    @Test
    public void whenFigureIsPlacedAtTheBoardItMarksThreatenedPositions()
            throws Exception {

        Board board = new Board(5, 5);
        Position position = Position.create(3, 3);

        board.place(Figure.KING, position);

        Set<Position> threatenedPositions = board.getThreatenedPositions();
        assertThat(threatenedPositions, hasSize(8));
        assertThat(threatenedPositions, hasItems(
                Position.create(2, 2), Position.create(2, 3), Position.create(2, 4),
                Position.create(3, 4), Position.create(4, 4), Position.create(4, 3),
                Position.create(4, 2), Position.create(3, 2)));
    }

    @Test
    public void canFindNonThreatenedAndOccupiedPositions() throws Exception {
        Board board = new Board(3, 3);
        Position position = Position.create(1, 1);

        board.place(Figure.KING, position);

        Set<Position> availablePositions = board.findPositionsToPlace();
        assertThat(availablePositions, hasSize(5));
        assertThat(availablePositions, hasItems(
                Position.create(3, 1), Position.create(3, 2), Position.create(3, 3),
                Position.create(1, 3), Position.create(2, 3)));
    }

    @Test
    public void shouldEqualIfMatchesSizeFigureTypesAndPositionOccupied()
            throws Exception {

        Board oneBoard = new Board(5, 5);
        Board anotherBoard = new Board(5, 5);

        Position onePosition = Position.create(1, 1);
        Position anotherPosition = Position.create(3, 3);

        oneBoard.place(Figure.KING, onePosition);
        oneBoard.place(Figure.KING, anotherPosition);
        anotherBoard.place(Figure.KING, onePosition);
        anotherBoard.place(Figure.KING, anotherPosition);

        assertEquals(oneBoard, anotherBoard);
    }

    @Test
    public void shouldNotEqualsIfSizeAreDifferent() throws Exception {
        Board oneBoard = new Board(5, 5);
        Board anotherBoard = new Board(3, 3);

        assertNotEquals(oneBoard, anotherBoard);
    }

    @Test
    public void shouldNotEqualsIfFiguresPlacedAtDifferentPositions()
            throws Exception {
        Board oneBoard = new Board(5, 5);
        Board anotherBoard = new Board(5, 5);

        oneBoard.place(Figure.KING, Position.create(3, 3));
        anotherBoard.place(Figure.KING, Position.create(1, 1));

        assertNotEquals(oneBoard, anotherBoard);
    }

    @Test
    public void shouldTellIfCanPlaceFigureToBoardWithoutThreateningOthers()
            throws Exception {
        Board board = new Board(1, 3);
        board.place(Figure.KING, Position.create(1, 1));

        assertTrue(board.canPlace(Figure.KING, Position.create(1, 3)));
        assertFalse(board.canPlace(Figure.KING, Position.create(1, 2)));
    }

    @Test(expected = OutOfBoardPosition.class)
    public void whenFigureIsNotWithinTheBoardThenNotifiesClient() throws Exception {
        Board board = new Board(2, 3);

        board.place(Figure.KING, getOutOfBoardPosition(board));
    }

    @Test
    public void cloneInstanceIsEqualOriginalInstance() throws Exception {
        Board originalBoard = new Board(2, 3);
        originalBoard.place(Figure.KING, Position.create(2, 2));

        Board clonedBoard = originalBoard.clone();

        assertEquals(originalBoard, clonedBoard);
        assertEquals(clonedBoard, originalBoard);
    }

    @Test
    public void canRepresentAsFENLayout() {
        Board emptyBoard = new Board(3, 5);
        assertEquals("5/5/5", emptyBoard.toFENLayout());

        Board arbitraryBoard = new Board(3, 4);
        arbitraryBoard.place(Figure.KING, Position.create(1, 1));
        arbitraryBoard.place(Figure.KING, Position.create(1, 3));
        arbitraryBoard.place(Figure.ROOK, Position.create(3, 2));
        assertEquals("K1K1/4/1R2", arbitraryBoard.toFENLayout());
    }

    private Position getOutOfBoardPosition(Board board) {
        return Position.create(board.getMaxRows() + 1, board.getMaxColumns() + 1);
    }
}