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
    }

    @Override
    public void collidesWith(Entity other, Grid grid) {
        // exit doesn't move
    }

    @Override
    public boolean canMoveInto(Entity other) {
        return true;
    }
}
