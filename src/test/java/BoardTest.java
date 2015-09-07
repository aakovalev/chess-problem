import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItem;
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
        board.place(Figure.KING, thePosition);
        Set<Position> occupiedPositions = board.getOccupiedPositions();
        assertThat(occupiedPositions.size(), is(1));
        assertThat(occupiedPositions, hasItem(thePosition));
    }
}