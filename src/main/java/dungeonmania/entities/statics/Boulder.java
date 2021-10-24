package dungeonmania.entities.statics;

import java.util.List;

import dungeonmania.Grid;
import dungeonmania.entities.Entity;
import dungeonmania.entities.player.Player;
import dungeonmania.util.Position;

public class Boulder extends StaticEntity {

    @Override
    public void collidesWith(Entity other, Grid grid) {
        if (other instanceof Player) {
            Position playerPosition = other.getPosition();
            Position boulderPosition = this.getPosition();
            Position newPosition = reflectPosition(boulderPosition, playerPosition);

            List<Entity> entitiesAtNewPosition = grid.getEntities(newPosition.getX(), newPosition.getY());

            boolean canMove = false;

            for (Entity entity : entitiesAtNewPosition) {
                //check there's nothing already in new position then update canMove
            }

            if (canMove) {
                this.setPosition(newPosition.getX(), newPosition.getY());
            }
        }
    }

    /**
     * Gets the reflection of a given position around the given origin. 
     * E.g. origin = {0,0}, other = {1,1}, returns {-1,-1}
     * E.g. origin = {1,1}, other = {1,0}, returns {1,2}
     * @param origin The centre of our reflection.
     * @param toReflect The position we wish to reflect.
     * @return The reflection of our given position.
     */
    private Position reflectPosition(Position origin, Position toReflect) {
        int xOffset = origin.getX() - toReflect.getX();
        int yOffset = origin.getY() - toReflect.getY();
        return new Position(origin.getX() + xOffset, origin.getY() + yOffset);
    }
    
    @Override
    public boolean canMoveInto(Entity other) {
        if (other instanceof Wall)                  { return false; }   //required
        if (other instanceof Exit)                  { return true; }    //assumption
        if (other instanceof Boulder)               { return false; }   //required
        if (other instanceof FloorSwitch)           { return true; }    //required
        if (other instanceof Door)                  { 
            if (((Door)other).getIsOpen())          { return true; }    //assumption
            else                                    { return false; } } //required
        if (other instanceof Portal)                { return true; }    //assumption
        if (other instanceof ZombieToastSpawner)    { return false; }   //assumption
        return true;
    }
}
