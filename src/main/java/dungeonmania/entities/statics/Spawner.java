package dungeonmania.entities.statics;

import dungeonmania.util.Position;

public abstract class Spawner extends StaticEntity {

    public Spawner(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
    }
    
}
