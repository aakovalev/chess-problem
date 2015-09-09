public abstract class AbstractFigure implements Figure {
    private Position position;

    @Override
    public Position getPosition() {
        return new Position(position.getRow(), position.getColumn());
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractFigure)) return false;

        AbstractFigure figure = (AbstractFigure) o;

        return position.equals(figure.getPosition())
                && getType().equals(figure.getType());
    }

    @Override
    public int hashCode() {
        int result = getType().hashCode();
        result = 31 * result + getPosition().hashCode();
        return result;
    }
}