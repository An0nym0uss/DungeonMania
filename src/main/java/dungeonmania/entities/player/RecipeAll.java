package dungeonmania.entities.player;

import java.util.ArrayList;
import java.util.List;

public class RecipeAll {
    private List<Recipe> recipes = new ArrayList<>();

    public RecipeAll() {
        String bowRecipeFile = "/recipes/bowRecipe.json";
        String shieldRecipeFile = "/recipes/shieldRecipe.json";

        List<String> files = new ArrayList<>();
        files.add(bowRecipeFile);
        files.add(shieldRecipeFile);

        ReadRecipe read = new ReadRecipe();
        for (String file : files) {
            for (Recipe recipe : read.readRecipes(file)) {
                this.recipes.add(recipe);
            }
        }
    }

    public List<Recipe> getRecipes() {
        return this.recipes;
    }
    
}
