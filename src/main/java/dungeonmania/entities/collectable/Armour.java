package dungeonmania.entities.collectable;

import dungeonmania.util.Position;

public class Armour extends CollectableEntity implements Durable{
    private int durability;

    public Armour(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        this.durability = 10;
    }

    public Armour(String type, Position position, boolean isInteractable, int durability) {
        this(type, position, isInteractable);
        this.durability = durability;
    }

    @Override
    public int getDurability() {
        return this.durability;
    }

    @Override
    public void setDurability(int durability) {
        this.durability = durability;
    }

    @Override
    public boolean isBroken() {
        if (this.durability == 0) {
            return true;
        }
        return false;
    }
}
