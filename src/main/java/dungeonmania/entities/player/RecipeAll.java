package dungeonmania.entities.player;

import java.util.ArrayList;
import java.util.List;

public class RecipeAll {
    private List<Recipe> recipes = new ArrayList<>();

    public RecipeAll() {
        String bowRecipeFile = "/dungeonmania/entities/collectable/buildable/recipes/bowRecipe.json";
        String shieldRecipeFile = "/dungeonmania/entities/collectable/buildable/recipes/shieldRecipe.json";
        String sceptreRecipeFile = "/dungeonmania/entities/collectable/buildable/recipes/sceptreRecipe.json";
        String midnightArmourRecipeFile = "/dungeonmania/entities/collectable/buildable/recipes/midnightArmourRecipe.json";

        List<String> files = new ArrayList<>();
        files.add(bowRecipeFile);
        files.add(shieldRecipeFile);
        files.add(sceptreRecipeFile);
        files.add(midnightArmourRecipeFile);

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
