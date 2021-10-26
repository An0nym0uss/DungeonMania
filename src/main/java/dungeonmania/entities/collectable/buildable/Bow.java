package dungeonmania.entities.collectable.buildable;


import dungeonmania.entities.collectable.Durable;
import dungeonmania.entities.player.ReadRecipe;

public class Bow extends BuildableEntity implements Durable{
    private int durability;

    private String recipeFile = "/dungeonmania/entities/collectable/buildable/recipes/bowRecipe.json";

    public Bow(String id) {
        this.id = id;
        this.type = "bow";
        this.durability = 10;

        ReadRecipe read = new ReadRecipe();
        this.recipes = read.readRecipes(recipeFile);
    }

    public Bow(String id, int durability) {
        this(id);
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