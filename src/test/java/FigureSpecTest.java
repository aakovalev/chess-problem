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
        assertThat(new FigureSpec("1K").toFigureSequence(), contains(FigureType.KING));
        assertThat(new FigureSpec("2B").toFigureSequence(),
                contains(FigureType.BISHOP, FigureType.BISHOP));
    }

    @Test
    public void canTolerateToRedundantSpacesWithinFigureSpec() throws Exception {
        assertThat(new FigureSpec(" 1 K 2N").toFigureSequence(),
                contains(FigureType.KING, FigureType.KNIGHT, FigureType.KNIGHT));
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenSpecStringDoesNotConfirmSpecFormatNotifiesClient()
            throws Exception {

        new FigureSpec("1K 2Q 3B 2Q").toFigureSequence();
    }

    @Test
    public void canTolerateToLetterCase() throws Exception {
        assertThat(new FigureSpec("1k 1q").toFigureSequence(),
                contains(FigureType.KING, FigureType.QUEEN));
    }
}