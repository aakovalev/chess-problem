import org.junit.Test;

import static java.lang.Integer.valueOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ChessProblemTest {
    @Test
    public void degeneratedAndTrivialCases() throws Exception {
        assertThat(numberOfUniqueConfigurations(1, 1, null), is(0));
        assertThat(numberOfUniqueConfigurations(1, 1, ""), is(0));
        assertThat(numberOfUniqueConfigurations(1, 1, "1K"), is(1));
    }

    @Test (expected = IllegalArgumentException.class)
    public void dimensionOfTheBoardShouldBePositiveNumber() throws Exception {
        numberOfUniqueConfigurations(0, -1, "");
    }

    @Test
    public void whenNumberOfFiguresExceedsNumberOfCellsOfLayoutReturnsZero()
            throws Exception {
        assertThat(numberOfUniqueConfigurations(1, 1, "2K"), is(0));
    }

    @Test
    public void whenOneFigureOnlyThenNumberOfConfigurationsIsSizeOfLayout()
            throws Exception {
        assertThat(numberOfUniqueConfigurations(3, 8, "1Q"), is(24));
    }

    @Test
    public void canDetermineFigureCount() throws Exception {
        assertThat(figureCount(null), is(0));
        assertThat(figureCount(""), is(0));
        assertThat(figureCount("1K"), is(1));
        assertThat(figureCount("2K"), is(2));
        assertThat(figureCount("1K2Q"), is(3));
        assertThat(figureCount("2K1Q2B1R1N1"), is(8));
        assertThat(figureCount("1K20Q"), is(21));
        assertThat(figureCount("1R 2 B 3N"), is(6));
        assertThat(figureCount("1k 2Q 1 r"), is(4));
    }

    private int numberOfUniqueConfigurations(int m, int n, String figureSpec) {
        if (notPositive(m) || notPositive(n)) {
            throw new IllegalArgumentException(
                    "Dimension of layout can NOT be a negative number");
        }

        if (figureCount(figureSpec) == 1) {
            return m * n;
        }

        if (isEmptyOrNull(figureSpec)) {
            return 0;
        }

        if (figureCount(figureSpec) > m * n) {
            return 0;
        }

        return 0;
    }

    private boolean isEmptyOrNull(String figureSpec) {
        return figureSpec == null || figureSpec.length() == 0;
    }

    private boolean notPositive(int value) {
        return value <= 0;
    }

    private int figureCount(String figureSpecAsText) {
        int count = 0;
        if (figureSpecAsText != null && figureSpecAsText.length() > 0) {
            figureSpecAsText = normalizeFigureSpec(figureSpecAsText);
            String[] parts = figureSpecAsText.split("[KQBRN]");
            for (String  partialCountAsText: parts) {
                count += valueOf(partialCountAsText);
            }
        }
        return count;
    }

    private String normalizeFigureSpec(String spec) {
        spec = removeSpaces(spec);
        return spec.toUpperCase();
    }

    private String removeSpaces(String figureSpecAsText) {
        return figureSpecAsText.replace(" ", "");
    }
}