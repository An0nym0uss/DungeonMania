package dungeonmania.entities.collectable;

import dungeonmania.util.Position;

public class SunStone extends CollectableEntity{

    public SunStone(Position position) {
        super("sun_stone", position, false);
    }

    @Override
    public SunStone clone() {
        return new SunStone(this.getPosition());
    }
    
}
