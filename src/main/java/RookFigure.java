import java.util.HashSet;
import java.util.Set;

public class RookFigure extends AbstractFigure {
    @Override
    public Set<Position> getPositionsUnderThreatWhenPlacedOn(
            Bounded board, Position ownPosition) {
        Set<Position> positionsUnderThreat = new HashSet<>();

        int row = ownPosition.getRow();
        int column = ownPosition.getColumn();

        for (int i = 1; i <= board.getMaxRows(); i++) {
            if (i == row) {
                continue;
            }
            positionsUnderThreat.add(new Position(i, column));
        }

        for (int j = 1; j <= board.getMaxColumns(); j++) {
            if (j == column) {
                continue;
            }
            positionsUnderThreat.add(new Position(row, j));
        }

        return positionsUnderThreat;
    }

    @Override
    public FigureType getType() {
        return FigureType.ROOK;
    }
}