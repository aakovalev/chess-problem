import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board implements Bounded, Cloneable {
    private final byte rows;
    private final byte columns;

    private Map<Position, Figure> figuresByPositions = new HashMap<>();

    private Set<Position> threatenedPositions = new HashSet<>();

    public Board(int rows, int columns) {
        this.rows = (byte) rows;
        this.columns = (byte) columns;
    }

    public Set<Position> findPositionsToPlace() {
        Set<Position> availablePositions = new HashSet<>();
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= columns; j++) {
                Position position = Position.create(i, j);
                if (!isThreatened(position) && !isOccupied(position)) {
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
        threatenedPositions.addAll(figure.getPositionsUnderThreatWhenPlacedOn(this, position));
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
                Position position = Position.create(i, j);
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
            board.figuresByPositions = (Map<Position, Figure>) ((HashMap<Position, Figure>) figuresByPositions).clone();
            board.threatenedPositions = (Set<Position>) ((HashSet<Position>) threatenedPositions).clone();
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

        return toFENLayout().equals(board.toFENLayout());
    }

    @Override
    public int hashCode() {
        return toFENLayout().hashCode();
    }

    protected Set<Position> getThreatenedPositions() {
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

    /**
     * Represents board as string in Forsyth-Edwards notation
     * More details: https://en.wikipedia.org/wiki/Forsyth–Edwards_Notation
     *
     * @return string that represents board in Forsyth-Edwards notation
     */
    public String toFENLayout() {
        StringBuilder feNotation = new StringBuilder();
        for (int i = 1; i <= rows; i++) {
            int numberOfEmptyChecks = 0;

            for (int j = 1; j <= columns; j++) {
                Position position = Position.create(i, j);
                if (isOccupied(position)) {
                    if (numberOfEmptyChecks > 0) {
                        feNotation.append(numberOfEmptyChecks);
                        numberOfEmptyChecks = 0;
                    }
                    feNotation.append(findFigureTypeByPosition(position));
                }
                else {
                    numberOfEmptyChecks++;
                }
            }

            if (numberOfEmptyChecks > 0) {
                feNotation.append(numberOfEmptyChecks);
            }

            if (i < rows) {
                feNotation.append("/");
            }
        }
        return feNotation.toString();
    }
}