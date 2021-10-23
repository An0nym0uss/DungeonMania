package dungeonmania.entities;

import dungeonmania.Grid;
import dungeonmania.util.Position;

public abstract class Entity implements ObserverEntity {
    private String id;
    private String type;
    private Position position;
    private boolean isInteractable;

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
    public void collidesWith(Entity other) {

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