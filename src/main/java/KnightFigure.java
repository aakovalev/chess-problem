import java.util.HashSet;
import java.util.Set;

public class KnightFigure extends AbstractFigure {
    @Override
    public Set<Position> getPositionsUnderThreatWhenPlacedOn(
            Bounded board, Position ownPosition) {

        HashSet<Position> positionsUnderThreat = new HashSet<>();
        int row = ownPosition.getRow();
        int column = ownPosition.getColumn();

        if (row + 1 <= board.getMaxRows() && column + 2 <= board.getMaxColumns()) {
            positionsUnderThreat.add(new Position(row + 1, column + 2));
        }

        if (row + 2 <= board.getMaxRows() && column + 1 <= board.getMaxColumns()) {
            positionsUnderThreat.add(new Position(row + 2, column + 1));
        }

        if (row + 2 <= board.getMaxRows() && column > 1) {
            positionsUnderThreat.add(new Position(row + 2, column - 1));
        }

        if (row + 1 <= board.getMaxRows() && column > 2) {
            positionsUnderThreat.add(new Position(row + 1, column - 2));
        }

        if (row > 1 && column > 2) {
            positionsUnderThreat.add(new Position(row - 1, column - 2));
        }

        if (row > 2 && column > 1) {
            positionsUnderThreat.add(new Position(row - 2, column - 1));
        }

        if (row > 1 && column + 2 <= board.getMaxColumns()) {
            positionsUnderThreat.add(new Position(row - 1, column + 2));
        }

        if (row > 2 && column + 1 <= board.getMaxColumns()) {
            positionsUnderThreat.add(new Position(row - 2, column + 1));
        }

        return positionsUnderThreat;
    }

    @Override
    public FigureType getType() {
        return FigureType.KNIGHT;
    }
}