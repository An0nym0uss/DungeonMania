package dungeonmania.entities.collectable.rarecollectable;

import dungeonmania.util.Position;
import dungeonmania.entities.player.Inventory;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.collectable.CollectableEntity;

public class Anduril extends RareCollectableEntities {

    private int attack;

    public Anduril() {
        this(new Position(0,0));
        this.attack = 20;
    }

    public Anduril(Position position) {
        super("anduril", position, false, 10);
        this.attack = 20;
    }

    public Anduril(Anduril that) {
        this();
    }

    @Override
    public void spawnnAnduril(Player player, Inventory inventory) {
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
                player.setAnduril(this);
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

    public void setAttack(int attack) {
        this.attack = attack;
    }
}
