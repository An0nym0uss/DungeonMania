package dungeonmania.entities;

import dungeonmania.Grid;
import dungeonmania.util.Position;

public abstract class Entity implements ObserverEntity {
    private String id;
    private String type;
    private Position position;
    private boolean isInteractable;

    /**
     * Getter for entity id.
     * 
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Getter for entity type.
     * 
     * @return
     */
    public String getType() {
        return type;
    }


    /**
     * Getter for position.
     * 
     * @return
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Setter for position
     * 
     * @param position
     * 
     * @pre 0 <= x < WIDTH
     * @pre 0 <= y < HEIGHT
     */
    public void setPosition(int x, int y) {
        position = position.translateBy(x, y);
    }

    /**
     * Is Interactable.
     * 
     */
    public boolean isInteractable() {
        return isInteractable;
    }

    /**
     * Setter for position.
     * @param position The new position to set.
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * What happens every tick.
     */
    @Override
    public void update(Grid grid) {

    };

    /**
     * This is attempting move into other. This is the interaction between them.
     * 
     * @param other
     */
    public void collidesWith(Entity other, Grid grid) {

    }
    
    /**
     * This checks this entity can move into other entity.
     * 
     * @param other
     * @return true if can.
     */
    public boolean canMoveInto(Entity other) {
        // For subclasses to override otherwise by default returns true.
        return true;
    }
}