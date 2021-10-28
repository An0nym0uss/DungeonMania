package dungeonmania.entities.collectable.rarecollectable;

import dungeonmania.entities.collectable.CollectableEntity;
import dungeonmania.util.Position;

public abstract class RareCollectableEntities extends CollectableEntity {
    public RareCollectableEntities(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        //TODO Auto-generated constructor stub
    }

    private double dropDate;
    
}