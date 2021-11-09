package dungeonmania.entities.collectable.buildable;


import org.json.JSONObject;

import dungeonmania.entities.collectable.Durable;
import dungeonmania.entities.player.ReadRecipe;
import dungeonmania.util.Position;

public class Bow extends BuildableEntity implements Durable{
    private int durability;

    private String recipeFile = "/dungeonmania/entities/collectable/buildable/recipes/bowRecipe.json";

    public Bow(Position position) {
        this(position, 10);
    }

    public Bow(Position position, int durability) {
        super("bow", position, false);
        ReadRecipe read = new ReadRecipe();
        this.recipes = read.readRecipes(recipeFile);
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
        JSONObject bow = super.getJSON();

        bow.put("durability", durability);

        return bow;
    }
}