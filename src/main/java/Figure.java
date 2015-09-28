import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum Figure {
    KING {
        @Override
        Set<Position> getPositionsUnderThreatWhenPlacedOn(
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
        public int getInfluenceOnBoard() {
            return 1;
        }

        @Override
        public String toString() {
            return "K";
        }
    },

    QUEEN {
        @Override
        Set<Position> getPositionsUnderThreatWhenPlacedOn(Bounded board, Position ownPosition) {
            Set<Position> positionsUnderThreat = new HashSet<>();

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
        public int getInfluenceOnBoard() {
            return 5;
        }

        @Override
        public String toString() {
            return "Q";
        }
    },

    BISHOP {
        @Override
        Set<Position> getPositionsUnderThreatWhenPlacedOn(Bounded board, Position ownPosition) {
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

        @Override
        public int getInfluenceOnBoard() {
            return 4;
        }

        @Override
        public String toString() {
            return "B";
        }
    },

    ROOK {
        @Override
        Set<Position> getPositionsUnderThreatWhenPlacedOn(Bounded board, Position ownPosition) {
            Set<Position> positionsUnderThreat = new HashSet<>();

            positionsUnderThreat.addAll(
                    getThreatenedPositionsInColumn(board, ownPosition));

            positionsUnderThreat.addAll(
                    getThreatenedPositionsInRow(board, ownPosition));

            return positionsUnderThreat;
        }

        @Override
        public int getInfluenceOnBoard() {
            return 3;
        }

        @Override
        public String toString() {
            return "R";
        }
    },

    KNIGHT {
        @Override
        Set<Position> getPositionsUnderThreatWhenPlacedOn(Bounded board, Position ownPosition) {
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
        public int getInfluenceOnBoard() {
            return 2;
        }

        @Override
        public String toString() {
            return "N";
        }
    };

    abstract Set<Position> getPositionsUnderThreatWhenPlacedOn(
            Bounded board, Position ownPosition);

    protected Set<Position> getThreatenedPositionsInDiagonalPart(
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

    protected Set<Position> getThreatenedPositionsInRow(
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

    protected Set<Position> getThreatenedPositionsInColumn(
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


    public static Figure fromString(String typeString) {
        if ("K".equals(typeString)) {
            return KING;
        }

        if ("Q".equals(typeString)) {
            return QUEEN;
        }

        if ("B".equals(typeString)) {
            return BISHOP;
        }

        if ("R".equals(typeString)) {
            return ROOK;
        }

        if ("N".equals(typeString)) {
            return KNIGHT;
        }

        throw new IllegalArgumentException(
                "Given type string does not correspond to existed types");
    }

    abstract public int getInfluenceOnBoard();
}