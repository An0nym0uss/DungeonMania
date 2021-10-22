package dungeonmania.entities;

import dungeonmania.util.Position;

public abstract class Entity {
    private String id;
    private String type;
    private Position position;
    private boolean isInteractable;

    /**
     * What happens every tick.
     */
    public void update() {

    };

    /**
     * This is attempting move into other. This is the interaction between them.
     * 
     * @param other
     */
    public void collidesWith(Entity other) {

    }
}