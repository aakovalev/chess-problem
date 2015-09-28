public class Position {
    private final byte row;
    private final byte column;

    public Position(int row, int column) {
        this.row = (byte) row;
        this.column = (byte) column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;

        Position position = (Position) o;

        return column == position.column && row == position.row;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        return result;
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