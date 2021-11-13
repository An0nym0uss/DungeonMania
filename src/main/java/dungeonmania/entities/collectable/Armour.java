package dungeonmania.entities.collectable;

import org.json.JSONObject;

import dungeonmania.util.Position;

public class Armour extends CollectableEntity implements Durable{
    protected int attack;
    protected int defense;
    private int durability;

    public Armour(Position position) {
        this(position, 1, 10);
    }

    public Armour(Position position, int defense, int durability) {
        super("armour", position, false);
        this.durability = durability;
        this.attack = 0;
    }

    public Armour(String type, Position position) {
        super(type, position, false);
    }

    public Armour(Armour that) {
        this(that.getPosition(), that.getDefense(), that.getDurability());
    }

    public int getAttack() {
        return this.attack;
    }

    public int getDefense() {
        return this.defense;
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
    public Armour clone() {
        return new Armour(this);
    }

    @Override
    public JSONObject getJSON() {
        JSONObject armour = super.getJSON();

        armour.put("durability", durability);

        return armour;
    }
}
