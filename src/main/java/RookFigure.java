import java.util.HashSet;
import java.util.Set;

public class RookFigure extends AbstractFigure {
    @Override
    public Set<Position> getPositionsUnderThreatWhenPlacedOn(
            Bounded board, Position ownPosition) {
        Set<Position> positionsUnderThreat = new HashSet<>();

        positionsUnderThreat.addAll(
                getThreatenedPositionsInColumn(board, ownPosition));

        positionsUnderThreat.addAll(
                getThreatenedPositionsInRow(board, ownPosition));

        return positionsUnderThreat;
    }

    private Set<Position> getThreatenedPositionsInRow(
            Bounded board, Position ownPosition) {

        Set<Position> rowPositions = new HashSet<>();
        int row = ownPosition.getRow();
        int column = ownPosition.getColumn();

        for (int j = 1; j <= board.getMaxColumns(); j++) {
            if (j == column) {
                continue;
            }
            rowPositions.add(new Position(row, j));
        }
        return rowPositions;
    }

    private Set<Position> getThreatenedPositionsInColumn(
            Bounded board, Position ownPosition) {

        Set<Position> columnPositions = new HashSet<>();
        int row = ownPosition.getRow();
        int column = ownPosition.getColumn();

        for (int i = 1; i <= board.getMaxRows(); i++) {
            if (i == row) {
                continue;
            }
            columnPositions.add(new Position(i, column));
        }
        return columnPositions;
    }

    @Override
    public FigureType getType() {
        return FigureType.ROOK;
    }
}