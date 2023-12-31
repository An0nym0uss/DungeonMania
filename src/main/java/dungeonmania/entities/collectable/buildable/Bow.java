package dungeonmania.entities.collectable.buildable;


import java.util.List;

import org.json.JSONObject;

import dungeonmania.entities.collectable.CollectableEntity;
import dungeonmania.entities.collectable.Durable;
import dungeonmania.entities.player.ReadRecipe;
import dungeonmania.entities.player.Recipe;
import dungeonmania.util.Position;

public class Bow extends CollectableEntity implements Durable, Buildable{
    private int durability;
    private List<Recipe> recipes;

    private String recipeFile = "/recipes/bowRecipe.json";

    public Bow(Position position) {
        this(position, 10);
    }

    public Bow(Position position, int durability) {
        super("bow", position, false);
        ReadRecipe read = new ReadRecipe();
        this.recipes = read.readRecipes(recipeFile);
        this.durability = durability;
    }

    public Bow(Bow that) {
        this(that.getPosition(), that.getDurability());
    }

    @Override
    public List<Recipe> getRecipes() {
        return this.recipes;
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

    @Override
    public Bow clone() {
        return new Bow(this);
    }
}