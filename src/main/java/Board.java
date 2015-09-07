import java.util.*;

public class Board implements Bounded {
    private final int rows;
    private final int columns;
    private Map<Position, FigureType> figuresByOccupiedPositions = new HashMap<>();

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public List<Position> optionsToPlaceWithoutTreat(FigureType figureType) {
        // not occupied
        // not treatedByOtherFigures
        // treatedByThisFigure does not contain any from occupied
        return new ArrayList<>();
    }

    public Set<Position> findPositionsToPlace() {
        Set<Position> availablePositions = new HashSet<>();
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= columns; j++) {
                Position position = new Position(i, j);
                if (!isOccupied(position)) {
                    availablePositions.add(position);
                }
            }
        }
        return availablePositions;
    }

    private boolean isOccupied(Position position) {
        return getOccupiedPositions().contains(position);
    }

    protected Set<Position> getOccupiedPositions() {
        return figuresByOccupiedPositions.keySet();
    }

    @Override
    public int getMaxRows() {
        return rows;
    }

    @Override
    public int getMaxColumns() {
        return columns;
    }

    public void place(Figure figure) {
        if (isNotWithinBounds(figure.getPosition())) {
            throw new OutOfBoardPosition();
        }
        figuresByOccupiedPositions.put(figure.getPosition(), figure.getType());
    }

    private boolean isNotWithinBounds(Position position) {
        return position.getRow() < 1
                || position.getColumn() < 1
                || position.getRow() > getMaxRows()
                || position.getColumn() > getMaxColumns();
    }
}