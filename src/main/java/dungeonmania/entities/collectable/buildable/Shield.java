package dungeonmania.entities.collectable.buildable;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.collectable.Durable;
import dungeonmania.entities.player.Recipe;

public class Shield extends BuildableEntity implements Durable{
    private int durability;
    private int defense;

    public Shield(String id, String type, int durability, int defense) {
        this.id = id;
        this.type = type;
        this.durability = durability;
        this.defense = defense;
        List<String> ingredients = new ArrayList<String>();
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
}