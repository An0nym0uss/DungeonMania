package dungeonmania.entities;

import dungeonmania.Grid;
import dungeonmania.util.Direction;

public interface Moving {
    int speed = 1;

    void move(Grid grid, Direction d);

    boolean movingConstraints(Entity e);
}
