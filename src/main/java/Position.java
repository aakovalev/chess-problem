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
        Position position;
        if (positionsByKeys.containsKey(key)) {
            position = positionsByKeys.get(key);
        }
        else {
            position = new Position(row, column);
            positionsByKeys.put(key, position);
        }
        return position;
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