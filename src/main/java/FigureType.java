public enum FigureType {
    KING {
        @Override
        protected Figure createFigure() {
            return new KingFigure();
        }

        @Override
        public String toString() {
            return "K";
        }
    },

    QUEEN {
        @Override
        protected Figure createFigure() {
            return new QueenFigure();
        }

        @Override
        public String toString() {
            return "Q";
        }
    },

    BISHOP {
        @Override
        protected Figure createFigure() {
            return new BishopFigure();
        }

        @Override
        public String toString() {
            return "B";
        }
    },

    ROOK {
        @Override
        protected Figure createFigure() {
            return new RookFigure();
        }

        @Override
        public String toString() {
            return "R";
        }
    },

    KNIGHT {
        @Override
        protected Figure createFigure() {
            return new KnightFigure();
        }

        @Override
        public String toString() {
            return "N";
        }
    };

    abstract protected Figure createFigure();

    public static FigureType fromString(String typeString) {
        if ("K".equals(typeString)) {
            return KING;
        }

        if ("Q".equals(typeString)) {
            return QUEEN;
        }

        if ("B".equals(typeString)) {
            return BISHOP;
        }

        if ("R".equals(typeString)) {
            return ROOK;
        }

        if ("N".equals(typeString)) {
            return KNIGHT;
        }

        throw new IllegalArgumentException(
                "Given type string does not correspond to existed types");
    }
}