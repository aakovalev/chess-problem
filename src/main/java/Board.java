import java.lang.ref.SoftReference;
import java.util.*;

public class Board implements Bounded, Cloneable {
    private final byte rows;
    private final byte columns;

    private Map<Position, Figure> figuresByPositions = new HashMap<>();

    private SoftReference<Set<Position>> cachedThreatenedPositions =
            new SoftReference<Set<Position>>(new HashSet<Position>());

    public Board(int rows, int columns) {
        this.rows = (byte) rows;
        this.columns = (byte) columns;
    }

    public Set<Position> findPositionsToPlace() {
        Set<Position> availablePositions = new HashSet<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Position position = Position.create(i + 1, j + 1);
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
        figuresByPositions.put(position, figure);
        cachedThreatenedPositions.clear();
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
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Position position = Position.create(i + 1, j + 1);
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

    private Figure findFigureTypeByPosition(Position position) {
        return figuresByPositions.get(position);
    }

    public Board clone() {
        try {
            Board board = (Board) super.clone();
            board.figuresByPositions = new HashMap<>(figuresByPositions);
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
                figuresByPositions.equals(board.figuresByPositions);
    }

    @Override
    public int hashCode() {
        int result = rows;
        result = 31 * result + columns;
        result = 31 * result + figuresByPositions.hashCode();
        return result;
    }

    protected Set<Position> getThreatenedPositions() {
        Set<Position> threatenedPositions = cachedThreatenedPositions.get();
        if (threatenedPositions == null) {
            threatenedPositions = new HashSet<>();
            for (Position position : getOccupiedPositions()) {
                Figure figure = figuresByPositions.get(position);
                threatenedPositions.addAll(
                        figure.getPositionsUnderThreatWhenPlacedOn(
                                this, position));
            }
            this.cachedThreatenedPositions =
                    new SoftReference<>(threatenedPositions);
        }
        return threatenedPositions;
    }

    protected Set<Position> getOccupiedPositions() {
        return figuresByPositions.keySet();
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

    public Layout toLayout() {
        return new Layout(figuresByPositions);
    }
}