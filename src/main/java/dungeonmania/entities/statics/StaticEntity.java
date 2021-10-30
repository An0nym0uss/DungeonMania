package dungeonmania.entities.statics;

import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public abstract class StaticEntity extends Entity {

    /**
     * Constructor for StaticEntity.
     * 
     * @param type
     * @param position
     * @param isInteractable
     */
    public StaticEntity(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
    }
    
}
