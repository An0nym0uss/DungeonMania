package dungeonmania.entities.collectable.rarecollectable;

import dungeonmania.util.Position;

public class TheOneRing extends RareCollectableEntities{
    
    public TheOneRing(Position position) {
        super("one_ring", position, false);
        setDropRate(1);
    }
}