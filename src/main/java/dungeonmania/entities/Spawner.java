package dungeonmania.entities;

import dungeonmania.Grid;
import dungeonmania.constants.Layer;
import dungeonmania.util.Position;

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
    
    /**
     * @return The amount of ticks required to occur before the entity can spawn.
     */
    public int getSpawnRate();

    /**
     * @return The maximum amount of entities to have on the grid at any time. 0 for infinite.
     */
    public int getMaxEntities();

    /**
     * @return How many ticks have occured since the last spawn.
     */
    public int getSpawnCounter();

    /**
     * Sets the spawn counter to a specific number.
     * @param spawnCounter The number to set the spawn counter to.
     */
    public void setSpawnCounter(int spawnCounter);

    /**
     * @return The entity we wish to spawn.
     */
    public Entity getSpawnEntity();

    /**
     * @param grid The grid we wish to spawn the entity on.
     * @return The position we want to spawn the entity at.
     */
    public Position getSpawnPosition(Grid grid);

    /**
     * @param grid The grid where we wish to count the entities.
     * @return How many entities already exist on the grid. Use -1 to allow for infinite spawning.
     */
    default public <T> int getNumEntitiesOnGrid(Grid grid, Class<T> clas) {
        int num = 0;
        Entity[][][] map = grid.getMap();
        for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {
                for (int z = 0; z < grid.getLayerSize(); z++) {
                    if (clas.isInstance(map[x][y][z])) {
                        num++;
                    }
                }
            }
        }
        return num;
    }

    /**
     * Checks if we should spawn a new entity this tick.
     */
    default public <T> void checkForNextSpawn(Grid grid, Class<T> clas) {
        if (getNumEntitiesOnGrid(grid, clas) < getMaxEntities()) {
            setSpawnCounter(getSpawnCounter() + 1);
            if (getSpawnCounter() == getSpawnRate()) {
                setSpawnCounter(0);
                Position spawnPosition = getSpawnPosition(grid);

                if (spawnPosition != null) {
                    Entity newEntity = getSpawnEntity();
                    newEntity.setPosition(spawnPosition);
                    spawn(newEntity, grid);
                }
            }
        }
    }
}
