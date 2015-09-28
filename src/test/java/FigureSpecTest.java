import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

public class FigureSpecTest {
    @Test
    public void whenFigureSpecStringIsEmptyOrNullProducesEmptyList()
            throws Exception {
        assertThat(new FigureSpec("").toFigureSequence(), empty());
        assertThat(new FigureSpec(null).toFigureSequence(), empty());
    }

    @Test
    public void whenGivenFigureSpecAsStringConvertItToListOfFigureTypes()
            throws Exception {
        assertThat(new FigureSpec("1K").toFigureSequence(), contains(Figure.KING));
        assertThat(new FigureSpec("2B").toFigureSequence(),
                contains(Figure.BISHOP, Figure.BISHOP));
    }

    @Test
    public void canTolerateToRedundantSpacesWithinFigureSpec() throws Exception {
        assertThat(new FigureSpec(" 1 K 2N").toFigureSequence(),
                contains(Figure.KING, Figure.KNIGHT, Figure.KNIGHT));
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenSpecStringDoesNotConfirmSpecFormatNotifiesClient()
            throws Exception {

        new FigureSpec("1K 2Q 3B 2Q").toFigureSequence();
    }

    @Test
    public void canTolerateToLetterCase() throws Exception {
        assertThat(new FigureSpec("1k 1q").toFigureSequence(),
                contains(Figure.KING, Figure.QUEEN));
    }

    @Test
    public void canSortFiguresByTheirInfluenceOnBoard() {
        assertThat(
                new FigureSpec("1K 1Q 1B 1R 1N").toSortedFigureSequence(),
                contains(
                        Figure.QUEEN, Figure.BISHOP, Figure.ROOK,
                        Figure.KNIGHT, Figure.KING));
    }
}