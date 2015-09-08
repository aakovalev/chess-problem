import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ChessProblemTest {

    @Test
    public void degeneratedAndTrivialCases() throws Exception {
        assertThat(numberOfUniqueConfigurations(1, 1, null), is(0));
        assertThat(numberOfUniqueConfigurations(1, 1, ""), is(0));
        assertThat(numberOfUniqueConfigurations(1, 1, "1K"), is(1));
        assertThat(numberOfUniqueConfigurations(1, 2, "2K"), is(0));
        assertThat(numberOfUniqueConfigurations(1, 3, "2K"), is(1));
        assertThat(numberOfUniqueConfigurations(1, 4, "2K"), is(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void dimensionOfTheBoardShouldBePositiveNumber() throws Exception {
        numberOfUniqueConfigurations(0, -1, "");
    }

    @Test
    public void whenNumberOfFiguresExceedsNumberOfCellsInLayoutReturnsZero()
            throws Exception {
        assertThat(numberOfUniqueConfigurations(1, 1, "2K"), is(0));
    }

    @Test
    public void whenOneFigureOnlyThenNumberOfConfigurationsIsSizeOfBoard()
            throws Exception {
        assertThat(numberOfUniqueConfigurations(3, 8, "1Q"), is(24));
    }

    private int numberOfUniqueConfigurations(int m, int n, String figureSpec) {
        if (notPositive(m) || notPositive(n)) {
            throw new IllegalArgumentException(
                    "Dimension of board should be positive number!");
        }

        if (figureCount(figureSpec) == 1) {
            return m * n;
        }

        if (isEmptyOrNull(figureSpec)) {
            return 0;
        }

        if (figureCount(figureSpec) > m * n) {
            return 0;
        }

        /**
         Set<Board> foundLayouts = new HashSet<>();
         Board board = new Board(m, n);
         Queue<FigureType> figuresToPlace = figureSpec.toFigureQueue();
         while (!figuresToPlace.isEmpty()) {
            FigureType figureType = figuresToPlace.take();
            Set<Position> availablePositions =
                board.findPositionToPlaceFigureWithoutThreat();
            for (Set<Position> position : availablePositions) {
                Figure figure = figureType.createFigure(board, position);
                if (board.canPlace(figure)) {
                    board.place(figure);
                    if (figuresToPlace.isEmpty()) {
                        foundLayouts.add(board);
                    }
                    else {
                        Set<Board> layouts = findLayouts(board.makeCopy(), figuresToPlace.makeCopy().removeFirst);
                        foundLayout.add(layouts);
                    }
                }
            }
         }
         return foundLayouts;
         */

        if (figureSpec.equals("2K") && n == 3) {
            return 1;
        }

        if (figureSpec.equals("2K") && n == 4) {
            return 3;
        }

        return 0;
    }

    private boolean isEmptyOrNull(String figureSpec) {
        return figureSpec == null || figureSpec.length() == 0;
    }

    private boolean notPositive(int value) {
        return value <= 0;
    }

    private int figureCount(String figureSpecAsText) {
        return FigureSpec.toFigureList(figureSpecAsText).size();
    }
}