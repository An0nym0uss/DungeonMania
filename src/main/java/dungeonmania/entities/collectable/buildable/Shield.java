package dungeonmania.entities.collectable.buildable;


import org.json.JSONObject;

import dungeonmania.entities.collectable.Durable;
import dungeonmania.entities.player.ReadRecipe;
import dungeonmania.util.Position;

public class Shield extends BuildableEntity implements Durable{
    private int durability;
    private int defense;

    private final String recipeFile = "/dungeonmania/entities/collectable/buildable/recipes/shieldRecipe.json";

    public Shield(Position position) {
        this(position, 10, 10);
    }

    public Shield( Position position, int durability, int defense) {
        super("shield", position, false);

        ReadRecipe read = new ReadRecipe();
        this.recipes = read.readRecipes(recipeFile);

        this.durability = durability;
        this.defense = defense;
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
    public JSONObject getJSON() {
        JSONObject shield = super.getJSON();

        shield.put("durability", durability);

        return shield;
    }
}