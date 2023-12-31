package dungeonmania.entities.statics;

import org.json.JSONObject;
/*
import dungeonmania.entities.enemy.Mercenary;
import dungeonmania.entities.enemy.Spider;
import dungeonmania.entities.enemy.Zombie;
*/
import dungeonmania.util.Position;

/**
 * A door that requires a specific key to open.
 * If the player has the correct key, moving through it will open the door.
 */
public class Door extends StaticEntity {
    private int keyNumber;
    private boolean isOpen;

    /**
     * Creates a new Door object with the required key to open and door status.
     * @param key The specific key needed to open the door.
     * @param isOpen The status of the door. True for open, false for closed.
     */
    public Door(String type, Position position, int keyNumber, boolean isOpen) {
        super(type, position, false);
        this.keyNumber = keyNumber;
        this.isOpen = isOpen;
    }

    /**
     * Gets the key needed to open the door.
     * @return
     */
    public int getKey() {
        return keyNumber;
    }

    /**
     * Gets the status of the door.
     * @return True for open, false for closed.
     */
    public boolean getIsOpen(){
        return isOpen;
    }

    /**
     * Sets the key needed to open the door.
     * @param key The specific key needed to open the door.
     */
    /* we never need to set
    public void setKey(int keyNumber) {
        this.keyNumber = keyNumber;
    }*/

    /**
     * Sets the status of the door.
     * @param isOpen True for open, false for closed.
     */
    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    /*
    @Override
    public void collidesWith(Entity other, Grid grid) {
        if (other instanceof Player) {
            Player player = (Player)other;
            Position doorPosition = this.getPosition();

            List<Entity> entitiesAtDoorPosition = grid.getEntities(doorPosition.getX(), doorPosition.getY());

            boolean canMove = true;

            //check if theres anything in door position that won't allow our player
            for (Entity entity : entitiesAtDoorPosition) {
                if (!player.canMoveInto(entity)) {
                    canMove = false;
                }
            }

            //can uncomment when Player has method
            if (*!player.hasItem(this.getKey()) &&/ !this.getIsOpen()) {
                canMove = false;
            }

            if (canMove) {
                /player.removeItem(this.getKey());/ //can uncomment when Player has method
                this.setIsOpen(true);
                player.setPosition(doorPosition); //player moves to door position
            }
        }
    }

    @Override
    public boolean canMoveInto(Entity other) {
        if (this.getIsOpen()) {
            if (other instanceof Player)                    { return true; }   //required
            / //can uncomment when these extend Entity
            //moving entities
            else if (other instanceof Spider)               { return true; }   //required
            else if (other instanceof Zombie)               { return true; }   //required
            else if (other instanceof Mercenary)            { return true; }   //required
            *
        }
        else {
            if (other instanceof Player)    { 
                / can uncomment when Player has method
                if (((Player)other).hasItem(this.getKey())) { return true; }    //required
                
                else /                                     { return false; } } //required
        }

        return false;
    }*/

    @Override
    public JSONObject getJSON() {
        JSONObject door = super.getJSON();

        door.put("key", keyNumber);

        return door;
    }

    @Override
    public Door clone() {
        return new Door(this.getType(), this.getPosition(), this.getKey(), this.isOpen);
    }
}