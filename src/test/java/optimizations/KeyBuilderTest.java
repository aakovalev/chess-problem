package optimizations;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KeyBuilderTest {
    @Test
    public void canMakeKeyFromIntKeyPart() throws Exception {
        KeyBuilder keyBuilder = new KeyBuilder().appendKeyPart(12345);
        assertEquals("12345", keyBuilder.makeKey());
    }

    @Test
    public void canMakeKeyFromMultipleIntKeyParts() throws Exception {
        KeyBuilder keyBuilder = new KeyBuilder()
                .appendKeyPart(12345).appendKeyPart(54321);
        assertEquals("12345:54321", keyBuilder.makeKey());
    }

    @Test
    public void canMakeKeyFromStringKeyPart() throws Exception {
        KeyBuilder keyBuilder = new KeyBuilder().appendKeyPart("abc");
        assertEquals("abc", keyBuilder.makeKey());
    }

    @Test
    public void canMakeKeyFromMultipleStringKeyParts() throws Exception {
        KeyBuilder keyBuilder = new KeyBuilder().appendKeyPart("abc").appendKeyPart("def");
        assertEquals("abc:def", keyBuilder.makeKey());
    }

    @Test
    public void canMakeKeyFromMultipleKeyPartsOfDiffTypes() throws Exception {
        KeyBuilder keyBuilder = new KeyBuilder()
                .appendKeyPart(12345)
                .appendKeyPart("abc")
                .appendKeyPart(67890);

        assertEquals("12345:abc:67890", keyBuilder.makeKey());
    }
}