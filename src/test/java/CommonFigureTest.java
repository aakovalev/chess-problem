import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public abstract class CommonFigureTest {
    abstract protected Figure getFigure();
    abstract protected Board getBoard();

    protected void verifyPositionsUnderThreat(
            Position position, Position... expectedPositions) {
        Figure figure = getFigure();

        Set<Position> positionsUnderThreat =
                figure.getPositionsUnderThreatWhenPlacedOn(getBoard(), position);

        assertThat(positionsUnderThreat, hasSize(expectedPositions.length));
        assertThat(positionsUnderThreat, hasItems(expectedPositions));
    }

    protected Position getLeftUpperCorner() {
        return Position.create(1, 1);
    }

    protected Position getRightUpperCorner() {
        return Position.create(1, getBoard().getMaxColumns());
    }

    protected Position getRightDownCorner() {
        return Position.create(getBoard().getMaxRows(), getBoard().getMaxColumns());
    }

    protected Position getLeftDownCorner() {
        return Position.create(getBoard().getMaxRows(), 1);
    }
}