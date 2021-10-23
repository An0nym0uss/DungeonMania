package dungeonmania.entities;

import dungeonmania.util.Position;

public abstract class Entity {
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