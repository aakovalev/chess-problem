import org.junit.Test;

import java.util.Queue;
import java.util.Set;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ConfigurationSearcherTest {

    @Test
    public void degeneratedAndTrivialCases() throws Exception {
        assertThat(numberOfUniqueConfigurations(1, 1, null), is(0));
        assertThat(numberOfUniqueConfigurations(1, 1, ""), is(0));
        assertThat(numberOfUniqueConfigurations(1, 1, "1K"), is(1));
        assertThat(numberOfUniqueConfigurations(1, 2, "2K"), is(0));
        assertThat(numberOfUniqueConfigurations(1, 3, "2K"), is(1));
        assertThat(numberOfUniqueConfigurations(1, 4, "2K"), is(3));
        assertThat(numberOfUniqueConfigurations(2, 3, "2K"), is(4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void dimensionOfTheBoardShouldBePositiveNumber() throws Exception {
        numberOfUniqueConfigurations(0, -1, "");
    }

    @Test
    public void whenNumberOfFiguresExceedsNumberOfCellsInLayoutReturnsZero()
            throws Exception {
        assertThat(numberOfUniqueConfigurations(1, 1, "2K"), is(0));
    }

    @Test
    public void whenOneFigureOnlyThenNumberOfConfigurationsIsSizeOfBoard()
            throws Exception {
        assertThat(numberOfUniqueConfigurations(3, 8, "1Q"), is(24));
    }

    @Test
    public void givenBoardWithOnlyOneOptionToPlaceFigureStopsSearchAndReturnFoundConfiguration()
            throws Exception {
        ConfigurationSearcher searcher = spy(new ConfigurationSearcher());
        Board board = new Board(1, 3);
        board.place(new KingFigure(), new Position(1, 1));

        Set<Board> foundLayouts =
                searcher.findLayouts(board, FigureSpec.toFigureSequence("1K"));

        Board expectedBoard = new Board(1, 3);
        expectedBoard.place(new KingFigure(), new Position(1, 1));
        expectedBoard.place(new KingFigure(), new Position(1, 3));
        assertThat(foundLayouts, containsInAnyOrder(expectedBoard));
        verify(searcher, only()).findLayouts(any(Board.class), any(Queue.class));
    }

    private int numberOfUniqueConfigurations(int m, int n, String figureSpec) {
        ConfigurationSearcher searcher = new ConfigurationSearcher();
        return searcher.numberOfUniqueConfigurations(m, n, figureSpec);
    }
}