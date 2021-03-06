import java.util.List;
import java.util.StringTokenizer;

import static java.lang.Integer.valueOf;
import static java.util.Collections.nCopies;

public class TypeAndQuantitySpec {
    public static final String SPEC_DELIMITERS = "[KQBRN]";
    private String figureTypeAndQuantityAsString;

    public TypeAndQuantitySpec(String figureTypeAndQuantityAsString) {
        this.figureTypeAndQuantityAsString = figureTypeAndQuantityAsString;
    }

    public List<Figure> toList() {
        StringTokenizer tokenizer =
                new StringTokenizer(
                        figureTypeAndQuantityAsString, SPEC_DELIMITERS);

        String qtyString = tokenizer.nextToken();
        String typeString = figureTypeAndQuantityAsString.replace(qtyString, "");

        int quantity = valueOf(qtyString);
        Figure figure = Figure.fromString(typeString);

        return nCopies(quantity, figure);
    }
}