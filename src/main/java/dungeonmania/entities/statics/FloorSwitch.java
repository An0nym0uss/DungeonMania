package dungeonmania.entities.statics;

import java.util.List;

import dungeonmania.Grid;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

/**
 * Switches behave like empty squares, so other entities can appear on top of them. 
 * When a boulder is pushed onto a floor switch, it is triggered. 
 * Pushing a boulder off the floor switch untriggers it.
 * 
 * If a bomb is placed adjacent to a switch and the switch is triggered, 
 * all entities in the bomb's blast radius will be destroyed.
 * @author Lachlan Kerr (z5118613)
 */
public class FloorSwitch extends StaticEntity {

    /**
     * Constructor for FloorSwitch.
     * 
     * @param position
     */
    public FloorSwitch(Position position) {
        super("switch", position, false);
        //TODO Auto-generated constructor stub
    }

    /**
     * Gets whether or not a boulder is on our floor switch.
     * @param grid The current grid we wish to check on.
     * @return True for triggered, false for not triggered
     */
    public boolean getIsTriggered(Grid grid) {
        Position floorSwitchPosition = this.getPosition();

        List<Entity> entitiesAtFloorSwitchPosition = grid.getEntities(floorSwitchPosition.getX(), floorSwitchPosition.getY());

        for (Entity entity : entitiesAtFloorSwitchPosition) {
            if (entity instanceof Boulder) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void collidesWith(Entity other, Grid grid) {
        Position floorSwitchPosition = this.getPosition();

        List<Entity> entitiesAtFloorSwitchPosition = grid.getEntities(floorSwitchPosition.getX(), floorSwitchPosition.getY());

        boolean canMove = true;

        //check if theres anything in floor switch position that won't allow our entity
        for (Entity entity : entitiesAtFloorSwitchPosition) {
            if (!other.canMoveInto(entity)) {
                canMove = false;
            }
        }

        if (canMove) {
            other.setPosition(floorSwitchPosition); //entity moves to floor switch position
        }
    }

    @Override
    public boolean canMoveInto(Entity other) {
        //Switches behave like empty squares, so other entities can appear on top of them.
        return true;
    }
}
