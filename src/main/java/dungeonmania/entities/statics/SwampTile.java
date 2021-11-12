package dungeonmania.entities.statics;

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
    
    public SwampTile(Position position, int movementFactor) {
        super("swamp_tile", position, false);
        this.movementFactor = movementFactor;
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
