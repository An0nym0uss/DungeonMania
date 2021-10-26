package dungeonmania.entities.collectable;

public class Armour extends CollectableEntity implements Durable{
    private int durability;

    public Armour(String id) {
        this.id = id;
        this.type = "armour";
        this.durability = 10;
    }

    public Armour(String id, int durability) {
        this(id);
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
