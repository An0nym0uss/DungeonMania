package dungeonmania.entities.statics;

import org.json.JSONObject;

/*import java.util.List;

import dungeonmania.Grid;
import dungeonmania.entities.Entity;
import dungeonmania.entities.enemy.Enemy;
import dungeonmania.entities.enemy.Zombie;
import dungeonmania.entities.player.Player;*/
import dungeonmania.util.Position;

/**
 * Teleports entities to a corresponding portal.
 * Zombies are not allowed to use it.
 * @author Lachlan Kerr (z5118613)
 */
public class Portal extends StaticEntity {
    private Portal correspondingPortal;
    private String colour;

    /**
     * Creates a new Portal entity with a corresponding portal to transport entities to.
     * @param correspondingPortal The corresponding portal to set.
     */
    public Portal(String type, Position position, String colour) {
        super(type, position, false);
        this.correspondingPortal = null;
        this.colour = colour;
    }

    /**
     * @return The corresponding portal
     */
    public Portal getCorrespondingPortal() {
        return correspondingPortal;
    }

    /**
     * Sets a new corresponding portal, should only be called at dungeon creation.
     * @param correspondingPortal The corresponding portal to set.
     */
    public void setCorrespondingPortal(Portal correspondingPortal) {
        this.correspondingPortal = correspondingPortal;
    }

    /**
     * Getter for colour.
     * @return
     */
    public String getColour() {
        return colour;
    }

    /*
    @Override
    public void collidesWith(Entity other, Grid grid) {
        //every enemy entity and player can pass through except zombie
        if ((other instanceof Enemy || other instanceof Player) && !(other instanceof Zombie)) {
            Position portalPosition = this.getPosition();
            Position correspondingPortalPosition = correspondingPortal.getPosition();

            List<Entity> entitiesAtPortalPosition = grid.getEntities(portalPosition.getX(), portalPosition.getY());
            List<Entity> entitiesAtCorrespondingPortalPosition = grid.getEntities(correspondingPortalPosition.getX(), correspondingPortalPosition.getY());

            boolean canMove = true;

            //check if theres anything in this portal position that won't allow our entity
            //this occurs after an entity teleports, they go to the corresponding portals position,
            //if they never move / can't move then they could still be blocking it.
            for (Entity entity : entitiesAtPortalPosition) {
                if (!other.canMoveInto(entity)) {
                    canMove = false;
                }
            }

            //check if theres anything in corresponding portal position that won't allow our entity
            for (Entity entity : entitiesAtCorrespondingPortalPosition) {
                if (!other.canMoveInto(entity)) {
                    canMove = false;
                }
            }

            if (canMove) {
                other.setPosition(correspondingPortalPosition); //entity moves to corresponding portal position
            }
        }
    }

    @Override
    public boolean canMoveInto(Entity other) {
        if ((other instanceof Enemy || other instanceof Player) && !(other instanceof Zombie)) { //required
            return true; 
        }  

        return false;
    }*/

    @Override
    public JSONObject getJSON() {
        JSONObject portal = super.getJSON();

        // Reseting type since define as portal_colour
        portal.put("type", "portal");
        portal.put("colour", colour);

        return portal;
    }
}
