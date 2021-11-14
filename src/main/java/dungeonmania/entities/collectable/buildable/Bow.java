package dungeonmania.entities.collectable.buildable;


import dungeonmania.entities.collectable.Durable;
import dungeonmania.entities.player.ReadRecipe;
import dungeonmania.util.Position;

public class Bow extends BuildableEntity implements Durable{
    private int durability;

    private String recipeFile = "/recipes/bowRecipe.json";

    public Bow(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        this.durability = 10;

        ReadRecipe read = new ReadRecipe();
        this.recipes = read.readRecipes(recipeFile);
    }

    public Bow(String type, Position position, boolean isInteractable, int durability) {
        this(type, position, isInteractable);
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