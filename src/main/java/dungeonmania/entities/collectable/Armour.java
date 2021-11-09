package dungeonmania.entities.collectable;

import org.json.JSONObject;

import dungeonmania.util.Position;

public class Armour extends CollectableEntity implements Durable{
    private int durability;

    public Armour(Position position) {
        this(position, 10);
    }

    public Armour(Position position, int durability) {
        super("armour", position, false);
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

    @Override
    public JSONObject getJSON() {
        JSONObject armour = super.getJSON();

        armour.put("durability", durability);

        return armour;
    }
}
