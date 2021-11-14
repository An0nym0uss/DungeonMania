package dungeonmania.entities.player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.util.FileLoader;

public class ReadRecipe {

    public List<Recipe> readRecipes(String filename) {
        List<Recipe> recipeCollection = new ArrayList<>();

        // creates recipes folder when it doesn't exist.
        File recipes_folder = new File(FileLoader.class.getResource("/").getPath() + "recipes");
        if (!recipes_folder.exists()) {
            recipes_folder.mkdirs();
        }
        
        try {
            String read = FileLoader.loadResourceFile(filename);
            JSONObject js = new JSONObject(read);
            // get type of buildable
            Object obj = js.get("type");
            String type = (String) obj;
            // each info is a piece of recipe
            JSONArray recipes = (JSONArray) js.get("ingredients");
            for (int i = 0; i < recipes.length(); i++) {
                JSONObject r = (JSONObject) recipes.get(i);
                String singleRecipe = r.toString();
                singleRecipe = singleRecipe.substring(1, singleRecipe.length() - 1);
                List<String> list = new ArrayList<>();
                list = Arrays.asList(singleRecipe.split(","));

                Map<String, Integer> ingredients = new HashMap<>();

                for (String single : list) {
                    List<String> ingredient = new ArrayList<>();
                    ingredient = Arrays.asList(single.split(":"));
                    String ingredientType = ingredient.get(0);
                    ingredientType = ingredientType.substring(1, ingredientType.length() - 1);
                    int amount = Integer.parseInt(ingredient.get(1));

                    ingredients.put(ingredientType, amount);
                }

                Recipe recipe = new Recipe(type, ingredients);
                recipeCollection.add(recipe);
            }
            
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return recipeCollection;
    }
    // public static void main(String[] args) {
    //     ReadRecipe read = new ReadRecipe();
    //     String filename = "/dungeonmania/entities/collectable/buildable/recipes/bowRecipe.json";
        
    //     for (Recipe recipe : read.readRecipes(filename)) {
    //         System.out.println("------------------");
    //         System.out.println(recipe.getType() + ": ");
    //         for (HashMap.Entry<String, Integer> ingredient : recipe.getIngredients().entrySet()) {
    //             System.out.println(ingredient.getKey() + " " + ingredient.getValue());
    //         }
    //     }
    // }
    
}