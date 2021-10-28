package dungeonmania.entities.collectable;

import dungeonmania.util.Position;

public class Sword extends CollectableEntity implements Durable{
    private int durability;
    private int attack;

    public Sword(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        this.durability = 10;
        this.attack = 10;
    }

    public Sword(String type, Position position, boolean isInteractable, int durability, int attack) {
        this(type, position, isInteractable);
        this.durability = durability;
        this.attack = attack;
    }

    public int getAttack() {
        return this.attack;
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
