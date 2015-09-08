import java.util.Set;

public interface Figure extends Positionable {
    Set<Position> getPositionsUnderThreatWhenPlacedOn(
            Bounded board, Position ownPosition);
    FigureType getType();
}