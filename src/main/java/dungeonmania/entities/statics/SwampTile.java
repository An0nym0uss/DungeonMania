package dungeonmania.entities.statics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dungeonmania.Grid;
import dungeonmania.entities.Entity;
import dungeonmania.entities.enemy.Enemy;
import dungeonmania.util.Position;

/**
 * Swamp tiles slow down the movement of enemy entities by the provided movement factor.
 * i.e. it takes 'movementFactor' ticks to traverse the tile.
 * @author Lachlan Kerr
 */
public class SwampTile extends StaticEntity {

    private int movementFactor;
    private HashMap<Entity, Integer> entityTicksLeft = new HashMap<Entity, Integer>();
    
    public SwampTile(Position position, int movementFactor) {
        super("swamp_tile", position, false);
        this.movementFactor = movementFactor;
    }

    @Override
    public void update(Grid grid) {
        List<Entity> entitiesOnSwampTile = grid.getEntities(getPosition().getX(), getPosition().getY());

        List<Entity> toRemove = new ArrayList<Entity>();
        // remove any entities from hash map that no longer reside on this swamp tile
        for (Entity entity : entityTicksLeft.keySet()) {
            if (!entitiesOnSwampTile.contains(entity)) {
                toRemove.add(entity);
            }
        }

        for (Entity entity : toRemove) {
            entityTicksLeft.remove(entity);
        }

        // add any entities in swamp tile that aren't in hash map
        for (Entity entity : entitiesOnSwampTile) {
            if (!entityTicksLeft.containsKey(entity)) {
                entityTicksLeft.put(entity, getMovementFactor());
            }
        }

        // decreases all the ticks left by 1
        for (Entity entity : entityTicksLeft.keySet()) {
            int ticksLeft = entityTicksLeft.get(entity);
            if (ticksLeft > 0) {
                entityTicksLeft.put(entity, ticksLeft - 1);
            }
        }
    }

    /**
     * @param entity The entity we wish to check.
     * @return True if the supplied entity is allowed to move off the swamp tile, otherwise false. Also returns false if the supplied entity is not on this swamp tile.
     */
    public boolean canMoveOff(Entity entity) {
        return entityTicksLeft.containsKey(entity) && entityTicksLeft.get(entity) == 0;
    }
    
    /**
     * @return The movement factor 
     */
    public int getMovementFactor() {
        return movementFactor;
    }
    
    /**
     * Checks whether the given entity should be affected by the swamp tile.
     * @param entity The entity we wish to check.
     * @return True if swamp tiles affects entity, otherwise false.
     */
    public boolean affects(Entity entity) {
        if (entity instanceof Enemy) {
            return true;
        }
        return false;
    }
}
