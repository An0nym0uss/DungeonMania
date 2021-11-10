package dungeonmania.entities;

import dungeonmania.Grid;
import dungeonmania.util.Direction;

/**
 * An entity implements this interface if it has the ability to move.
 */
public interface Moving {
    /**
     * What happens when the entity needs to move given a certain direction.
     * @param grid The grid on which the entity will move.
     * @param d The direction the entity is to move in.
     */
    public void move(Grid grid, Direction d);

    /**
     * Determines if the specified entity prevents this class from moving into it.
     * @param e The entity we are checking against.
     * @return True if we can move into the given entity, otherwise false.
     */
    public boolean movingConstraints(Entity e);
}
