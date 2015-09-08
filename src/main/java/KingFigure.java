import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class KingFigure extends AbstractFigure {

    public Set<Position> getPositionsUnderThreatWhenPlacedOn(
            Bounded board, Position ownPosition) {
        Set<Position> positionsUnderThreat = new HashSet<>();
        int row = ownPosition.getRow();
        int column = ownPosition.getColumn();

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
}