package dungeonmania.entities.collectable;

import dungeonmania.util.Position;

public class TimeTurner extends CollectableEntity {

    public TimeTurner(Position position) {
        super("time_turner", position, false);
    }

    @Override
    public TimeTurner clone() {
        return new TimeTurner(this.getPosition());
    }
}
