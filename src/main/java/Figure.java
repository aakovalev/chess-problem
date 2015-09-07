import java.util.Set;

public interface Figure extends Positionable {
    Set<Position> getPositionsUnderThreat();
    FigureType getType();
}