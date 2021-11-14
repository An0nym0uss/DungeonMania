package dungeonmania.entities.collectable.buildable;

import java.util.List;

import dungeonmania.entities.collectable.Armour;
import dungeonmania.entities.player.ReadRecipe;
import dungeonmania.entities.player.Recipe;
import dungeonmania.util.Position;

public class MidnightArmour extends Armour implements Buildable{
    private List<Recipe> recipes;
    
    private String recipeFile = "/recipes/midnightArmourRecipe.json";

    public MidnightArmour(Position position) {
        super("midnight_armour", position);
        this.attack = 10;
        this.defense = 10;

        ReadRecipe read = new ReadRecipe();
        this.recipes = read.readRecipes(recipeFile);
    }

    public MidnightArmour(Position position, int attack, int defense) {
        this(position);
        this.attack = attack;
        this.defense = defense;
    }

    public MidnightArmour(MidnightArmour that) {
        this(that.getPosition(), that.getAttack(), that.getDefense());
    }

    @Override
    public List<Recipe> getRecipes() {
        return this.recipes;
    }

    @Override
    public MidnightArmour clone() {
        return new MidnightArmour(this);
    }
}
