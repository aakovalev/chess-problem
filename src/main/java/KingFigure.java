import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class KingFigure implements Figure {
    private final Bounded board;
    private final Position position;

    public KingFigure(Bounded board, Position position) {
        this.board = board;
        this.position = position;
    }

    @Override
    public Set<Position> getPositionsUnderThreat() {
        Set<Position> positionsUnderThreat = new HashSet<>();
        int row = position.getRow();
        int column = position.getColumn();

        if (row > 1) {
            positionsUnderThreat.add(new Position(row - 1, column));
        }

        if (row < board.getMaxRows()) {
            positionsUnderThreat.add(new Position(row + 1, column));
        }

        if (column > 1) {
            positionsUnderThreat.add(new Position(row, column - 1));
        }

        if (column < board.getMaxColumns()) {
            positionsUnderThreat.add(new Position(row, column + 1));
        }

        if (row > 1 && column > 1) {
            positionsUnderThreat.add(new Position(row - 1, column - 1));
        }

        if (row < board.getMaxRows() && column < board.getMaxColumns()) {
            positionsUnderThreat.add(new Position(row + 1, column + 1));
        }

        if (row > 1 && column < board.getMaxColumns()) {
            positionsUnderThreat.add(new Position(row - 1, column + 1));
        }

        if (row < board.getMaxRows() && column > 1) {
            positionsUnderThreat.add(new Position(row + 1, column - 1));
        }

        return Collections.unmodifiableSet(positionsUnderThreat);
    }

    @Override
    public FigureType getType() {
        return FigureType.KING;
    }

    @Override
    public Position getPosition() {
        return new Position(position.getRow(), position.getColumn());
    }
}