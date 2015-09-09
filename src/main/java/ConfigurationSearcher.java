import java.util.*;

public class ConfigurationSearcher {
    private boolean drawFoundLayouts;

    public static void main(String[] args) {
        ConfigurationSearcher searcher = new ConfigurationSearcher(true);
    }

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

        if (figureCount(figureSpec) > m * n) {
            return 0;
        }

        Set<Board> foundLayouts = findLayouts(
                new Board(m, n), FigureSpec.toFigureSequence(figureSpec));

        if (drawFoundLayouts) {
            draw(foundLayouts);
        }

        return foundLayouts.size();
    }

    protected Set<Board> findLayouts(
            Board board, Queue<FigureType> figuresToPlace) {

        Set<Board> foundLayouts = new HashSet<>();

        FigureType figureType = figuresToPlace.poll();
        Figure figure = figureType.createFigure();
        Set<Position> availablePositions = board.findPositionsToPlace();
        for (Position position : availablePositions) {
            Board boardClone = board.clone();
            if (boardClone.canPlace(figure, position)) {
                boardClone.place(figure, position);
                if (figuresToPlace.isEmpty()) {
                    foundLayouts.add(boardClone);
                } else {
                    Set<Board> layouts = findLayouts(
                            boardClone, new LinkedList<>(figuresToPlace));
                    foundLayouts.addAll(layouts);
                }
            }
        }
        return foundLayouts;
    }

    private void draw(Set<Board> foundLayouts) {
        for (Board board : foundLayouts) {
            System.out.println(board.toString());
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