package dungeonmania.entities.collectable.buildable;


import java.util.List;

import org.json.JSONObject;

import dungeonmania.entities.collectable.CollectableEntity;
import dungeonmania.entities.collectable.Durable;
import dungeonmania.entities.player.ReadRecipe;
import dungeonmania.entities.player.Recipe;
import dungeonmania.util.Position;

public class Shield extends CollectableEntity implements Durable, Buildable{
    private int durability;
    private int defense;
    private List<Recipe> recipes;

    private final String recipeFile = "/recipes/shieldRecipe.json";

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

    public Shield(Shield that) {
        this(that.getPosition(), that.getDurability(), that.getDefense());
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

    @Override
    public List<Recipe> getRecipes() {
        return this.recipes;
    }

    @Override
    public Shield clone() {
        return new Shield(this);
    }
}