package dungeonmania.entities;

import org.json.JSONObject;

import dungeonmania.GameToJSON;
import dungeonmania.Grid;
import dungeonmania.util.Position;

public abstract class Entity implements ObserverEntity, GameToJSON {

    private static int n_entities = 0;
    private String id;
    private String type;
    private Position position;
    private boolean isInteractable;

    /**
     * Constructor for Entity.
     * 
     * @param id
     * @param type
     * @param position
     * @param isInteractable
     */
    public Entity(String type, Position position, boolean isInteractable) {
        this.id = Integer.toString(Entity.n_entities++);
        this.type = type;
        this.position = position;
        this.isInteractable = isInteractable;
    }

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
     * Setter for entity type.
     * @param type
     */
    public void setType(String type) {
        this.type = type;
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
        return other.getPosition().getLayer() != getPosition().getLayer();
    }

    @Override
    public JSONObject getJSON() {

        JSONObject entity = new JSONObject();

        entity.put("x", getPosition().getX());
        entity.put("y", getPosition().getY());
        entity.put("type", getType());

        return entity;
    }

    public Entity clone() {
        return null;
    }
}
