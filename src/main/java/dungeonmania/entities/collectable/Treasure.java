package dungeonmania.entities.collectable;

import dungeonmania.util.Position;

public class Treasure extends CollectableEntity{
    
    public Treasure(Position position) {
        super("treasure", position, false);
    }

    @Override
    public Treasure clone() {
        return new Treasure(this.getPosition());
    }
}
