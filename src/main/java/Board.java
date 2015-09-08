import java.util.*;

import static java.util.Collections.unmodifiableSet;

public class Board implements Bounded {
    private final int rows;
    private final int columns;
    private Map<Position, FigureType> figuresByOccupiedPositions = new HashMap<>();
    private Set<Position> threatenedPositions = new HashSet<>();

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public Set<Position> findPositionsToPlace() {
        Set<Position> availablePositions = new HashSet<>();
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= columns; j++) {
                Position position = new Position(i, j);
                if (!isOccupied(position) && !isThreatened(position)) {
                    availablePositions.add(position);
                }
            }
        }
        return availablePositions;
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
        markOccupiedPosition(figure);
        markThreatenedPositions(figure);
    }

    protected Set<Position> getThreatenedPositions() {
        return unmodifiableSet(threatenedPositions);
    }

    protected Set<Position> getOccupiedPositions() {
        return figuresByOccupiedPositions.keySet();
    }

    private boolean isThreatened(Position position) {
        return threatenedPositions.contains(position);
    }

    private boolean isOccupied(Position position) {
        return getOccupiedPositions().contains(position);
    }

    private void markThreatenedPositions(Figure figure) {
        threatenedPositions.addAll(figure.getPositionsUnderThreat());
    }

    private void markOccupiedPosition(Figure figure) {
        figuresByOccupiedPositions.put(figure.getPosition(), figure.getType());
    }

    private boolean isNotWithinBounds(Position position) {
        return position.getRow() < 1
                || position.getColumn() < 1
                || position.getRow() > getMaxRows()
                || position.getColumn() > getMaxColumns();
    }
}