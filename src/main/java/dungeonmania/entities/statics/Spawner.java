package dungeonmania.entities.statics;

import dungeonmania.Grid;
import dungeonmania.entities.Entity;

/**
 * An interface for anything that requires the ability to spawn.
 * @author Lachlan Kerr (z5118613)
 */
public interface Spawner {

    /**
     * Spawns an entity on the grid.
     * @param entity The entity to spawn.
     * @param grid The grid we wish to spawn the entity on.
     * @pre the position of entity are in bounds of grid size.
     * @pre there is not already entity of the same type (static, enemy, etc.) already at that position.
     * @post entity is added to grid.
     */
    public void spawn(Entity entity, Grid grid);
    
}
