package dungeonmania.entities.collectable;

import dungeonmania.util.Position;

public class Wood extends CollectableEntity {

    public Wood(Position position) {
        super("wood", position, false);
    }

    @Override
    public Wood clone() {
        return new Wood(this.getPosition());
    }
}

