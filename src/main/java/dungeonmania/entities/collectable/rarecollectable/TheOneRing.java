package dungeonmania.entities.collectable.rarecollectable;

import dungeonmania.util.Position;

public class TheOneRing extends RareCollectableEntities{
    
    public TheOneRing(Position position) {
        super("the_one_ring", position, false);
        setDropRate(1);
    }
}