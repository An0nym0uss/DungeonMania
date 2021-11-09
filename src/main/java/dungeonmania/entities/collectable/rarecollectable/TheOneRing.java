package dungeonmania.entities.collectable.rarecollectable;

import dungeonmania.util.Position;
import dungeonmania.entities.player.Inventory;
import dungeonmania.entities.collectable.CollectableEntity;

public class TheOneRing extends RareCollectableEntities{
    
    public TheOneRing(Position position) {
        super("one_ring", position, false);
        setDropRate(1);
    }

    @Override
    public void spawnnOneRing(Inventory inventory) {
        boolean hasRareCollectable = false;
        // can only have one one ring
        for (CollectableEntity entity : inventory.getItems()) {
            if (entity instanceof TheOneRing) {
                hasRareCollectable = true;
            }
        }
        if (!hasRareCollectable) {
            if (random.nextInt(100) < this.getDropRate()) {
                inventory.addItem(this);
            }
        }
    }
}