package dungeonmania;

import dungeonmania.util.Direction;

public class Tick {
    String itemUsed;
    Direction movementDirection;

    public Tick(String itemUsed, Direction movementDirection) {
        this.itemUsed = itemUsed;
        this.movementDirection = movementDirection;
    }

    public String getItemUsed() {
        return itemUsed;
    }

    public Direction getMovementDirection() {
        return movementDirection;
    }
}
