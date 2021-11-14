package dungeonmania.entities.collectable.buildable;

import java.text.CollationKey;
import java.util.List;

import dungeonmania.entities.collectable.CollectableEntity;
import dungeonmania.entities.player.ReadRecipe;
import dungeonmania.entities.player.Recipe;
import dungeonmania.util.Position;

public class Sceptre extends CollectableEntity implements Buildable {
    private List<Recipe> recipes;
    private String recipeFile = "/recipes/sceptreRecipe.json";

    public Sceptre(Position position) {
        super("sceptre", position, false);

        ReadRecipe read = new ReadRecipe();
        this.recipes = read.readRecipes(recipeFile);
    }

    public Sceptre(Sceptre that) {
        this(that.getPosition());
    }

    @Override
    public List<Recipe> getRecipes() {
        return this.recipes;
    }

    @Override
    public Sceptre clone() {
        return new Sceptre(this);
    }
    
}
