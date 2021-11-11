package dungeonmania.entities.collectable.buildable;

import dungeonmania.entities.player.ReadRecipe;
import dungeonmania.util.Position;

public class MidnightArmour extends BuildableEntity{
    private int attack;
    private int defense;
    
    private String recipeFile = "/dungeonmania/entities/collectable/buildable/recipes/midnightArmourRecipe.json";

    public MidnightArmour(Position position) {
        super("midnight_armour", position, false);
        this.attack = 10;
        this.defense = 10;

        ReadRecipe read = new ReadRecipe();
        this.recipes = read.readRecipes(recipeFile);
    }

    public MidnightArmour(Position position, int attack, int defense) {
        super("midnight_armour", position, false);
        this.attack = attack;
        this.defense = defense;
    }

    public int getAttack() {
        return this.attack;
    }

    public int getDefense() {
        return this.defense;
    }
    
}
