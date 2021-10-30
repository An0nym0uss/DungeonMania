package dungeonmania.entities.statics;

import java.util.List;

import dungeonmania.Grid;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

/**
 * If the character goes through it, the puzzle is complete.
 */
public class Exit extends StaticEntity {

    public Exit(Position position) {
        super("exit", position, false);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void collidesWith(Entity other, Grid grid) {
        Position exitPosition = this.getPosition();

        List<Entity> entitiesAtExitPosition = grid.getEntities(exitPosition.getX(), exitPosition.getY());

        boolean canMove = true;

        //check if theres anything in exit position that won't allow our entity
        for (Entity entity : entitiesAtExitPosition) {
            if (!other.canMoveInto(entity)) {
                canMove = false;
            }
        }

        if (canMove) {
            other.setPosition(exitPosition); //entity moves to exit position
        }
    }

    @Override
    public boolean canMoveInto(Entity other) {
        return true;
    }
}
