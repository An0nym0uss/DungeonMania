package dungeonmania.entities.collectable.buildable;


import dungeonmania.entities.collectable.Durable;
import dungeonmania.entities.player.ReadRecipe;
import dungeonmania.util.Position;

public class Shield extends BuildableEntity implements Durable{
    private int durability;
    private int defense;

    private final String recipeFile = "/dungeonmania/entities/collectable/buildable/recipes/shieldRecipe.json";

    public Shield(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
        this.durability = 10;
        this.defense = 10;

        ReadRecipe read = new ReadRecipe();
        this.recipes = read.readRecipes(recipeFile);
    }

    public Shield(String type, Position position, boolean isInteractable, int durability, int defense) {
        this(type, position, isInteractable);
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
}