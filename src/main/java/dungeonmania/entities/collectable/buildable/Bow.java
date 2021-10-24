package dungeonmania.entities.collectable.buildable;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.collectable.Durable;
import dungeonmania.entities.player.Recipe;

public class Bow extends BuildableEntity implements Durable{
    private int durability;

    public Bow(String id, String type, int durability) {
        this.id = id;
        this.type = type;
        this.durability = durability;
        List<String> ingredients = new ArrayList<String>();
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