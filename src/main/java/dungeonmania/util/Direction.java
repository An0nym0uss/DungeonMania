package dungeonmania.util;

public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    NONE(0, 0)
    ;

    private final Position offset;

    private Direction(Position offset) {
        this.offset = offset;
    }

    private Direction(int x, int y) {
        this.offset = new Position(x, y);
    }

    public Position getOffset() {
        return this.offset;
    }

    static public Direction getOppositeDirection(Direction d) {
        if (d.equals(Direction.UP)) {
            return Direction.DOWN;
        } else if (d.equals(Direction.DOWN)) {
            return Direction.UP;
        } else if (d.equals(Direction.RIGHT)) {
            return Direction.LEFT;
        } else if (d.equals(Direction.LEFT)) {
            return Direction.RIGHT;
        } else {
            return Direction.NONE;
        }
    }
}
