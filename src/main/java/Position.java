import java.util.HashMap;
import java.util.Map;

public final class Position {
    private final byte row;
    private final byte column;
    private final static Map<String, Position> positionsByKeys = new HashMap<>();

    private Position(int row, int column) {
        this.row = (byte) row;
        this.column = (byte) column;
    }

    public static Position create(int row, int column) {
        String key = toKey(row, column);
        Position position = positionsByKeys.get(key);
        if (position == null) {
            position = new Position(row, column);
            positionsByKeys.put(key, position);
        }
        return position;
    }

    @Override
    public int hashCode() {
        return toKey(row, column).hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (row != position.row) return false;
        return column == position.column;
    }

    private static String toKey(int row, int column) {
        return row + ", " + column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return String.format("Position(%s, %s)", getRow(), getColumn());
    }
}