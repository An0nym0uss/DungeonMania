package dungeonmania.entities.collectable;

import org.json.JSONObject;

import dungeonmania.util.Position;

public class Sword extends CollectableEntity implements Durable{
    private int durability;
    private int attack;

    public Sword(Position position) {
        this(position, 10, 10);
    }

    public Sword(Position position, int durability, int attack) {
        super("sword", position, false);
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

    @Override
    public JSONObject getJSON() {
        JSONObject sword = super.getJSON();

        sword.put("durability", durability);

        return sword;
    }
}
