public enum FigureType {
    KING {
        @Override
        protected Figure createFigure(Bounded board, Position position) {
            return new KingFigure(board, position);
        }
    };

    protected abstract Figure createFigure(Bounded board, Position position);
}