import java.util.*;

public class ConfigurationSearcher {
    private boolean drawFoundLayouts;

    public ConfigurationSearcher(boolean drawFoundLayouts) {
        this.drawFoundLayouts = drawFoundLayouts;
    }

    public ConfigurationSearcher() {
        this(false);
    }

    public int numberOfUniqueConfigurations(int m, int n, String figureSpec) {
        if (notPositive(m) || notPositive(n)) {
            throw new IllegalArgumentException(
                    "Dimension of board should be positive number!");
        }

        if (isEmptyOrNull(figureSpec)) {
            return 0;
        }

        if (figureCount(figureSpec) == 1) {
            return m * n;
        }

        Set<String> foundLayouts = findLayouts(
                new Board(m, n), FigureSpec.toSortedFigureSequence(figureSpec));

        if (drawFoundLayouts) {
            draw(foundLayouts);
        }

        return foundLayouts.size();
    }

    protected Set<String> findLayouts(
            Board board, Queue<Figure> figuresToPlace) {

        Set<String> foundLayouts = new HashSet<>();
        Set<Position> availablePositions = board.findPositionsToPlace();

        if (figuresToPlace.size() > availablePositions.size()) {
            return foundLayouts;
        }

        if (figuresToPlace.size() == 0) {
            return foundLayouts;
        }

        Figure figure = figuresToPlace.poll();

        for (Position position : availablePositions) {
            if (board.canPlace(figure, position)) {
                Board boardClone = board.clone();
                boardClone.place(figure, position);
                if (figuresToPlace.isEmpty()) {
                    foundLayouts.add(boardClone.toFENLayout());
                } else {
                    foundLayouts.addAll(findLayouts(
                            boardClone, new LinkedList<>(figuresToPlace)));
                }
            }
        }

        return foundLayouts;
    }

    private void draw(Set<String> fenLayouts) {
        for (String layout : fenLayouts) {
            System.out.println(layout);
        }
    }

    private boolean isEmptyOrNull(String figureSpec) {
        return figureSpec == null || figureSpec.length() == 0;
    }

    private boolean notPositive(int value) {
        return value <= 0;
    }

    private int figureCount(String figureSpecAsText) {
        return FigureSpec.toFigureSequence(figureSpecAsText).size();
    }
}