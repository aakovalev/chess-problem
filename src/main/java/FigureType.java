public enum FigureType {
    KING {
        @Override
        protected Figure createFigure() {
            return new KingFigure();
        }
    };

    abstract protected Figure createFigure();
}