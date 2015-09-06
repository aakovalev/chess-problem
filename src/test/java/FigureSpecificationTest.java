import org.junit.Test;

import static java.lang.Integer.valueOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FigureSpecificationTest {

    @Test
    public void nothing() throws Exception {
        assertThat(figureCount(null), is(0));
        assertThat(figureCount(""), is(0));
        assertThat(figureCount("1K"), is(1));
        assertThat(figureCount("2K"), is(2));
        assertThat(figureCount("1K2Q"), is(3));
        assertThat(figureCount("2K1Q2B1N"), is(6));
        assertThat(figureCount("1K20Q"), is(21));
    }

    private int figureCount(String figureSpecAsText) {
        int count = 0;
        if (figureSpecAsText != null && figureSpecAsText.length() > 0) {
            String[] parts = figureSpecAsText.split("[KQBRN]");
            for (String  partialCountAsText: parts) {
                count += valueOf(partialCountAsText);
            }
        }
        return count;
    }
}