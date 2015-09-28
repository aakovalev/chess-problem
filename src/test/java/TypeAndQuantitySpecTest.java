import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

public class TypeAndQuantitySpecTest {
    @Test
    public void shouldConvertTypeAndQuantitySpecToCorrespondingListOfFigures()
            throws Exception {
        assertThat(
                new TypeAndQuantitySpec("1K").toList(),
                contains(Figure.KING));

        assertThat(
                new TypeAndQuantitySpec("2K").toList(),
                contains(Figure.KING, Figure.KING));
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldNotifyClientIfTypeAndQuantityStringDoesNotConformSpec()
            throws Exception {
        new TypeAndQuantitySpec("2L").toList();
    }
}