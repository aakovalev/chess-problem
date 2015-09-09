import java.util.HashSet;
import java.util.Set;

public class QueenFigure extends AbstractFigure {
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

        positionsUnderThreat.addAll(
                getThreatenedPositionsInColumn(board, ownPosition));

        positionsUnderThreat.addAll(
                getThreatenedPositionsInRow(board, ownPosition));

        return positionsUnderThreat;
    }

    @Override
    public FigureType getType() {
        return FigureType.QUEEN;
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
}