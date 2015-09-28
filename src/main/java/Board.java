import java.lang.ref.SoftReference;
import java.util.*;

public class Board implements Bounded, Cloneable {
    private final byte rows;
    private final byte columns;
    private char[][] positions;

    private SoftReference<Set<Position>> cachedThreatenedPositions =
            new SoftReference<Set<Position>>(new HashSet<Position>());

    private SoftReference<List<Position>> cachedOccupiedPositions =
            new SoftReference<List<Position>>(new ArrayList<Position>());

    public Board(int rows, int columns) {
        this.rows = (byte) rows;
        this.columns = (byte) columns;
        positions = new char[rows][columns];
    }

    public Set<Position> findPositionsToPlace() {
        Set<Position> availablePositions = new HashSet<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Position position = new Position(i+1, j+1);
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
        positions[position.getRow()-1][position.getColumn()-1] = figure.toString().charAt(0);
        cachedThreatenedPositions.clear();
        cachedOccupiedPositions.clear();
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
                Position position = new Position(i+1, j+1);
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
        char figureSymbol = positions[position.getRow()-1][position.getColumn()-1];
        return Figure.fromString(new String(new char[]{figureSymbol}));
    }

    public Board clone() {
        try {
            Board board = (Board) super.clone();
            board.positions = new char[rows][columns];
            for (int i = 0; i < rows; i++) {
                System.arraycopy(positions[i], 0, board.positions[i], 0, columns);
            }
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
                Arrays.deepEquals(positions, board.positions);
    }

    @Override
    public int hashCode() {
        int result = rows;
        result = 31 * result + columns;
        result = 31 * result + Arrays.deepHashCode(positions);
        return result;
    }

    protected Set<Position> getThreatenedPositions() {
        Set<Position> threatenedPositions = cachedThreatenedPositions.get();
        if (threatenedPositions == null) {
            threatenedPositions = new HashSet<>();
            for (Position position : getOccupiedPositions()) {
                char figureSymbol =
                        positions[position.getRow() - 1][position.getColumn() - 1];
                Figure figure = Figure.fromString(
                        new String(new char[]{figureSymbol}));
                threatenedPositions.addAll(
                        figure.getPositionsUnderThreatWhenPlacedOn(
                                this, position));
            }
            this.cachedThreatenedPositions =
                    new SoftReference<>(threatenedPositions);
        }
        return threatenedPositions;
    }

    protected List<Position> getOccupiedPositions() {
        List<Position> occupiedPositions = cachedOccupiedPositions.get();
        if (occupiedPositions == null) {
            occupiedPositions = new ArrayList<>();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    if (positions[i][j] != 0) {
                        occupiedPositions.add(new Position(i + 1, j + 1));
                    }
                }
            }
            this.cachedOccupiedPositions =
                    new SoftReference<>(occupiedPositions);
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