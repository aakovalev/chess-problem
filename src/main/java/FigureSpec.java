import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class FigureSpec {
    private static final Pattern FIGURE_SPEC_FORMAT = compile(
            "(\\d+K)??(\\d+Q)??(\\d+B)??(\\d+R)??(\\d+N)??");

    public static final String DELIMITERS_SPEC = "(?<=[KQBRN])";

    private String figureSpecAsText;

    public FigureSpec(String figureSpecAsText) {
        this.figureSpecAsText = figureSpecAsText;
    }

    public Queue<FigureType> toFigureSequence() {
        Queue<FigureType> figureTypes = new LinkedList<>();
        if (!isEmptyOrNull(figureSpecAsText)) {
            String normalizedFigureSpec = normalizeFigureSpec(figureSpecAsText);
            validate(normalizedFigureSpec);
            String[] typeAndQtyTokens = normalizedFigureSpec.split(DELIMITERS_SPEC);
            for (String figureTypeAndQuantity : typeAndQtyTokens) {
                TypeAndQuantitySpec spec =
                        new TypeAndQuantitySpec(figureTypeAndQuantity);
                figureTypes.addAll(spec.toList());
            }
        }
        return figureTypes;
    }

    private boolean isEmptyOrNull(String figureSpec) {
        return figureSpec == null || figureSpec.length() == 0;
    }

    private void validate(String figureSpecAsText) {
        Matcher m = FIGURE_SPEC_FORMAT.matcher(figureSpecAsText);
        if (!m.matches()) {
            throw new IllegalArgumentException(
                    "Figure specification does not match expected format");
        }
    }

    private String normalizeFigureSpec(String spec) {
        spec = removeSpaces(spec);
        return spec.toUpperCase();
    }

    private String removeSpaces(String figureSpecAsText) {
        return figureSpecAsText.replace(" ", "");
    }

    public static Queue<FigureType> toFigureSequence(String figureSpecAsText) {
        return new FigureSpec(figureSpecAsText).toFigureSequence();
    }
}