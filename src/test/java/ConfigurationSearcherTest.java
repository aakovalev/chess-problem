import org.junit.Ignore;
import org.junit.Test;

import java.util.Queue;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
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

    @Test
    public void twoKingsOneRookExample() throws Exception {
        ConfigurationSearcher searcher = new ConfigurationSearcher(true);
        assertThat(searcher.numberOfUniqueConfigurations(3, 3, "2K 1R"), is(4));
    }

    @Test
    public void twoRooksFourKnightsExample() throws Exception {
        ConfigurationSearcher searcher = new ConfigurationSearcher(true);
        assertThat(searcher.numberOfUniqueConfigurations(4, 4, "2R 4N"), is(8));
    }

    @Test
    public void trivialCaseForBishops() throws Exception {
        ConfigurationSearcher searcher = new ConfigurationSearcher();
        assertThat(searcher.numberOfUniqueConfigurations(2, 2, "2B"), is(4));
    }

    @Ignore
    @Test
    public void twoKingsOneQueenOneBishopOneRookOneKnight() throws Exception {
        ConfigurationSearcher searcher = new ConfigurationSearcher(true);
        System.out.println("Output :"
                + searcher.numberOfUniqueConfigurations(6, 9, "2K 1Q 1B 1R 1N"));
    }

    @Test
    public void eightQueens() throws Exception {
        ConfigurationSearcher searcher = new ConfigurationSearcher(true);
        System.out.println("Output :"
                + searcher.numberOfUniqueConfigurations(8, 8, "8Q"));
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
        board.place(Figure.KING , Position.create(1, 1));

        Set<String> foundLayouts =
                searcher.findLayouts(board, FigureSpec.toFigureSequence("1K"));

        Board expectedBoard = new Board(1, 3);
        expectedBoard.place(Figure.KING, Position.create(1, 1));
        expectedBoard.place(Figure.KING, Position.create(1, 3));
        assertThat(foundLayouts, hasItem("K1K"));
        verify(searcher, only()).findLayouts(any(Board.class), any(Queue.class));
    }

    private int numberOfUniqueConfigurations(int m, int n, String figureSpec) {
        ConfigurationSearcher searcher = new ConfigurationSearcher();
        return searcher.numberOfUniqueConfigurations(m, n, figureSpec);
    }
}