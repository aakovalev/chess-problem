import java.util.*;
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

    public Queue<Figure> toFigureSequence() {
        Queue<Figure> figures = new LinkedList<>();
        if (!isEmptyOrNull(figureSpecAsText)) {
            String normalizedFigureSpec = normalizeFigureSpec(figureSpecAsText);
            validate(normalizedFigureSpec);
            String[] typeAndQtyTokens = normalizedFigureSpec.split(DELIMITERS_SPEC);
            for (String figureTypeAndQuantity : typeAndQtyTokens) {
                TypeAndQuantitySpec spec =
                        new TypeAndQuantitySpec(figureTypeAndQuantity);
                figures.addAll(spec.toList());
            }
        }
        return figures;
    }

    public Queue<Figure> toSortedFigureSequence() {
        LinkedList<Figure> sortedSequence = new LinkedList<>(toFigureSequence());
        Collections.sort(sortedSequence, new Comparator<Figure>() {
            @Override
            public int compare(Figure figure, Figure otherFigure) {
                return otherFigure.getInfluenceOnBoard() - figure.getInfluenceOnBoard();
            }
        });
        return sortedSequence;
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

    public static Queue<Figure> toFigureSequence(String figureSpecAsText) {
        return new FigureSpec(figureSpecAsText).toFigureSequence();
    }

    public static Queue<Figure> toSortedFigureSequence(String figureSpecAsText) {
        return new FigureSpec(figureSpecAsText).toSortedFigureSequence();
    }
}