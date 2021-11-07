package dungeonmania.entities.collectable.buildable;

import dungeonmania.entities.player.ReadRecipe;
import dungeonmania.util.Position;

public class Sceptre extends BuildableEntity {
    private String recipeFile = "/dungeonmania/entities/collectable/buildable/recipes/sceptreRecipe.json";

    public Sceptre(Position position) {
        super("sceptre", position, false);

        ReadRecipe read = new ReadRecipe();
        this.recipes = read.readRecipes(recipeFile);
    }
    
}
