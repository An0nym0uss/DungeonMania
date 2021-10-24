package dungeonmania.entities.collectable;

public class Armour extends CollectableEntity implements Durable{
    private int durability;

    public Armour(String id, String type, int durability) {
        this.id = id;
        this.type = type;
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
