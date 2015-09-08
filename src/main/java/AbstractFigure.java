public abstract class AbstractFigure implements Figure {
    private Position position;

    @Override
    public Position getPosition() {
        return new Position(position.getRow(), position.getColumn());
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}