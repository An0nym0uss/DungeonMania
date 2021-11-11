package dungeonmania.entities.collectable.rarecollectable;

import dungeonmania.util.Position;
import dungeonmania.entities.player.Inventory;
import dungeonmania.entities.collectable.CollectableEntity;

public class Anduril extends RareCollectableEntities {

    private int attack;

    public Anduril() {
        this(20);
    }

    public Anduril(int attack) {
        super("anduril", new Position(0, 0), false, 10);
        this.attack = attack;
    }

    public Anduril(Anduril that) {
        this(that.getAttack());
    }

    @Override
    public void spawnnAnduril(Inventory inventory) {
        boolean hasRareCollectable = false;
        // can only have one andruil
        for (CollectableEntity entity : inventory.getItems()) {
            if (entity instanceof Anduril) {
                hasRareCollectable = true;
            }
        }
        if (!hasRareCollectable) {
            if (random.nextInt(100) < this.getDropRate()) {
                inventory.addItem(this);
            }
        }
    }

    public int getAttack() {
        return this.attack;
    }

    @Override
    public Anduril clone() {
        return new Anduril(this);
    }
}
