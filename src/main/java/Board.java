import java.util.HashSet;
import java.util.Set;

public class Board implements Bounded, Cloneable {
    private final int rows;
    private final int columns;
    private Set<Figure> placedFigures = new HashSet<>();

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

    public void place(Figure figure, Position position) {
        if (isNotWithinBounds(position)) {
            throw new OutOfBoardPosition();
        }
        figure.setPosition(position);
        placedFigures.add(figure);
    }

    public boolean canPlace(Figure figure, Position position) {
        Set<Position> positionsUnderThreat =
                figure.getPositionsUnderThreatWhenPlacedOn(this, position);
        for (Position p: positionsUnderThreat) {
            if (isOccupied(p)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= columns; j++) {
                Position position = new Position(i, j);
                String posSymbol = "#";
                if (isOccupied(position)) {
                    posSymbol = findFigureTypeByPosition(position).toString();
                }
                content.append(posSymbol).append(" ");
            }
            content.append("\n");
        }
        return content.toString();
    }

    private FigureType findFigureTypeByPosition(Position position) {
        for (Figure figure : placedFigures) {
            if (figure.getPosition().equals(position)) {
                return figure.getType();
            }
        }
        throw new IllegalStateException("No figure at this position!");
    }

    public Board clone() {
        try {
            Board board = (Board) super.clone();
            board.placedFigures = new HashSet<>(placedFigures);
            return board;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        return columns == board.columns &&
                rows == board.rows &&
                placedFigures.equals(board.placedFigures);
    }

    @Override
    public int hashCode() {
        int result = rows;
        result = 31 * result + columns;
        result = 31 * result + placedFigures.hashCode();
        return result;
    }

    protected Set<Position> getThreatenedPositions() {
        HashSet<Position> threatenedPositions = new HashSet<>();
        for (Figure figure : placedFigures) {
            threatenedPositions.addAll(
                    figure.getPositionsUnderThreatWhenPlacedOn(
                            this, figure.getPosition()));
        }
        return threatenedPositions;
    }

    protected Set<Position> getOccupiedPositions() {
        Set<Position> occupiedPositions = new HashSet<>();
        for (Figure figure : placedFigures) {
            occupiedPositions.add(figure.getPosition());
        }
        return occupiedPositions;
    }

    private boolean isThreatened(Position position) {
        return getThreatenedPositions().contains(position);
    }

    private boolean isOccupied(Position position) {
        return getOccupiedPositions().contains(position);
    }

    private boolean isNotWithinBounds(Position position) {
        return position.getRow() < 1
                || position.getColumn() < 1
                || position.getRow() > getMaxRows()
                || position.getColumn() > getMaxColumns();
    }
}