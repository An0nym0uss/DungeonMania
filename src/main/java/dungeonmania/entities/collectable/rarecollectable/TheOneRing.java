package dungeonmania.entities.collectable.rarecollectable;

import dungeonmania.util.Position;
import dungeonmania.entities.player.Inventory;
import dungeonmania.entities.collectable.CollectableEntity;

public class TheOneRing extends RareCollectableEntities{
    
    public TheOneRing() {
        super("the_one_ring", new Position(0,0), false, 5);
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
