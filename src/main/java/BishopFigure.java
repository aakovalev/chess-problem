import java.util.HashSet;
import java.util.Set;

public class BishopFigure extends AbstractFigure {

    @Override
    public Set<Position> getPositionsUnderThreatWhenPlacedOn(
            Bounded board, Position ownPosition) {
        HashSet<Position> positionsUnderThreat = new HashSet<>();



        positionsUnderThreat.addAll(
                getThreatenedPositionsInDiagonalPart(board, ownPosition, 1, 1));

        positionsUnderThreat.addAll(
                getThreatenedPositionsInDiagonalPart(board, ownPosition, 1, -1));

        positionsUnderThreat.addAll(
                getThreatenedPositionsInDiagonalPart(board, ownPosition, -1, -1));

        positionsUnderThreat.addAll(
                getThreatenedPositionsInDiagonalPart(board, ownPosition, -1, 1));

        return positionsUnderThreat;
    }

    private Set<Position> getThreatenedPositionsInDiagonalPart(
            Bounded board, Position ownPosition, int rowDelta, int columnDelta) {
        Set<Position> diagonalPositions = new HashSet<>();
        int row = ownPosition.getRow();
        int column = ownPosition.getColumn();

        int shiftRow = rowDelta;
        int shiftColumn = columnDelta;
        while (row + shiftRow <= board.getMaxRows() &&
                row + shiftRow >= 1 &&
                column + shiftColumn <= board.getMaxColumns() &&
                column + shiftColumn >= 1) {

            diagonalPositions.add(new Position
                    (row + shiftRow, column + shiftColumn));
            shiftRow += rowDelta;
            shiftColumn += columnDelta;
        }
        return diagonalPositions;
    }

    @Override
    public FigureType getType() {
        return FigureType.BISHOP;
    }
}