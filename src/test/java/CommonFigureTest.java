import org.junit.Before;

import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public abstract class CommonFigureTest {
    abstract protected Figure createFigure();
    abstract protected Board getBoard();

    protected void verifyPositionsUnderThreat(
            Position position, Position... expectedPositions) {
        Figure figure = createFigure();

        Set<Position> positionsUnderThreat =
                figure.getPositionsUnderThreatWhenPlacedOn(getBoard(), position);

        assertThat(positionsUnderThreat, hasSize(expectedPositions.length));
        assertThat(positionsUnderThreat, hasItems(expectedPositions));
    }
}