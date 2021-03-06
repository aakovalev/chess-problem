import optimizations.KeyBuilder;

import java.util.*;

public enum Figure {
    KING {
        @Override
        Set<Position> getPositionsUnderThreatWhenPlacedOn(
                Bounded board, Position ownPosition) {
            Set<Position> positionsUnderThreat = new HashSet<>();
            int row = ownPosition.getRow();
            int column = ownPosition.getColumn();

            if (row > 1) {
                positionsUnderThreat.add(Position.create(row - 1, column));
            }

            if (row < board.getMaxRows()) {
                positionsUnderThreat.add(Position.create(row + 1, column));
            }

            if (column > 1) {
                positionsUnderThreat.add(Position.create(row, column - 1));
            }

            if (column < board.getMaxColumns()) {
                positionsUnderThreat.add(Position.create(row, column + 1));
            }

            if (row > 1 && column > 1) {
                positionsUnderThreat.add(Position.create(row - 1, column - 1));
            }

            if (row < board.getMaxRows() && column < board.getMaxColumns()) {
                positionsUnderThreat.add(Position.create(row + 1, column + 1));
            }

            if (row > 1 && column < board.getMaxColumns()) {
                positionsUnderThreat.add(Position.create(row - 1, column + 1));
            }

            if (row < board.getMaxRows() && column > 1) {
                positionsUnderThreat.add(Position.create(row + 1, column - 1));
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
            Set<Position> positionsUnderThreat = new HashSet<>();

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
            Set<Position> positionsUnderThreat = new HashSet<>();
            int row = ownPosition.getRow();
            int column = ownPosition.getColumn();

            if (row + 1 <= board.getMaxRows() && column + 2 <= board.getMaxColumns()) {
                positionsUnderThreat.add(Position.create(row + 1, column + 2));
            }

            if (row + 2 <= board.getMaxRows() && column + 1 <= board.getMaxColumns()) {
                positionsUnderThreat.add(Position.create(row + 2, column + 1));
            }

            if (row + 2 <= board.getMaxRows() && column > 1) {
                positionsUnderThreat.add(Position.create(row + 2, column - 1));
            }

            if (row + 1 <= board.getMaxRows() && column > 2) {
                positionsUnderThreat.add(Position.create(row + 1, column - 2));
            }

            if (row > 1 && column > 2) {
                positionsUnderThreat.add(Position.create(row - 1, column - 2));
            }

            if (row > 2 && column > 1) {
                positionsUnderThreat.add(Position.create(row - 2, column - 1));
            }

            if (row > 1 && column + 2 <= board.getMaxColumns()) {
                positionsUnderThreat.add(Position.create(row - 1, column + 2));
            }

            if (row > 2 && column + 1 <= board.getMaxColumns()) {
                positionsUnderThreat.add(Position.create(row - 2, column + 1));
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

    // cache of calculated positions to speed up the search
    private final static Map<String, Set<Position>>
            cacheOfAlreadyCalculatedPositions = new HashMap<>();

    protected Set<Position> getThreatenedPositionsInDiagonalPart(
            Bounded board, Position ownPosition, int rowDelta, int columnDelta) {

        String key = new KeyBuilder()
                .appendKeyPart("diagonalPositions")
                .appendKeyPart(board.getMaxRows())
                .appendKeyPart(board.getMaxColumns())
                .appendKeyPart(ownPosition.getRow())
                .appendKeyPart(ownPosition.getColumn())
                .appendKeyPart(rowDelta)
                .appendKeyPart(columnDelta).makeKey();

        Set<Position> diagonalPositions = cacheOfAlreadyCalculatedPositions.get(key);

        if (diagonalPositions == null) {
            diagonalPositions = new HashSet<>();
            int row = ownPosition.getRow();
            int column = ownPosition.getColumn();

            int shiftRow = rowDelta;
            int shiftColumn = columnDelta;
            while (row + shiftRow <= board.getMaxRows() &&
                    row + shiftRow >= 1 &&
                    column + shiftColumn <= board.getMaxColumns() &&
                    column + shiftColumn >= 1) {

                diagonalPositions.add(Position.create
                        (row + shiftRow, column + shiftColumn));
                shiftRow += rowDelta;
                shiftColumn += columnDelta;
            }

            cacheOfAlreadyCalculatedPositions.put(key, diagonalPositions);
        }

        return diagonalPositions;
    }

    protected Set<Position> getThreatenedPositionsInRow(
            Bounded board, Position ownPosition) {

        String key = new KeyBuilder()
                .appendKeyPart("rowPositions")
                .appendKeyPart(board.getMaxRows())
                .appendKeyPart(board.getMaxColumns())
                .appendKeyPart(ownPosition.getRow())
                .appendKeyPart(ownPosition.getColumn())
                .makeKey();

        Set<Position> rowPositions = cacheOfAlreadyCalculatedPositions.get(key);

        if (rowPositions == null) {
            rowPositions = new HashSet<>();

            int row = ownPosition.getRow();
            int column = ownPosition.getColumn();

            for (int j = 1; j <= board.getMaxColumns(); j++) {
                if (j == column) {
                    continue;
                }
                rowPositions.add(Position.create(row, j));
            }

            cacheOfAlreadyCalculatedPositions.put(key, rowPositions);
        }
        return rowPositions;
    }

    protected Set<Position> getThreatenedPositionsInColumn(
            Bounded board, Position ownPosition) {

        String key = new KeyBuilder()
                .appendKeyPart("columnPositions")
                .appendKeyPart(board.getMaxRows())
                .appendKeyPart(board.getMaxColumns())
                .appendKeyPart(ownPosition.getRow())
                .appendKeyPart(ownPosition.getColumn())
                .makeKey();

        Set<Position> columnPositions = cacheOfAlreadyCalculatedPositions.get(key);

        if (columnPositions == null) {
            columnPositions = new HashSet<>();

            int row = ownPosition.getRow();
            int column = ownPosition.getColumn();

            for (int i = 1; i <= board.getMaxRows(); i++) {
                if (i == row) {
                    continue;
                }
                columnPositions.add(Position.create(i, column));
            }

            cacheOfAlreadyCalculatedPositions.put(key, columnPositions);
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