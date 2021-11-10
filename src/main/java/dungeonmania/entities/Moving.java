package dungeonmania.entities;

import dungeonmania.Grid;
import dungeonmania.util.Direction;

public interface Moving {
    /**
     * What happens when the entity needs to move given a certain direction.
     * @param grid The grid on which the entity will move.
     * @param d The direction the entity is to move in.
     */
    public void move(Grid grid, Direction d);

    /**
     * Determines if the specified entity constraints (prevents) the movement of this class
     */
    public boolean movingConstraints(Entity e);
}
