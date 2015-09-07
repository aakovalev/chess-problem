import java.util.*;

public class Board {
    private final int rows;
    private final int columns;
    private Map<Position, Figure> figuresByOccupiedPositions = new HashMap<>();

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public List<Position> optionsToPlaceWithoutTreat(Figure figure) {
        // not occupied
        // not treatedByOtherFigures
        // treatedByThisFigure does not contain any from occupied
        return new ArrayList<>();
    }

    public void place(Figure figure, Position position) {
        figuresByOccupiedPositions.put(position, figure);
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
}