package dungeonmania.entities.statics;

import dungeonmania.util.Position;

public class SwampTile extends StaticEntity {

    private int movementFactor;
    
    public SwampTile(Position position, int movementFactor) {
        super("swamp_tile", position, false);
        this.movementFactor = movementFactor;
    }

    public int getMovementFactor() {
        return movementFactor;
    }
}
