import java.util.Collections;
import java.util.Map;

public final class Layout {
    private final Map<Position, Figure> figuresByPositions;

    public Layout(Map<Position, Figure> figuresByPositions) {
        this.figuresByPositions = Collections.unmodifiableMap(figuresByPositions);
    }

    @Override
    public int hashCode() {
        return figuresByPositions.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Layout layout = (Layout) o;

        return !(figuresByPositions != null
                ? !figuresByPositions.equals(layout.figuresByPositions)
                : layout.figuresByPositions != null);
    }
}
