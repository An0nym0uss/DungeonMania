package dungeonmania.entities.player;

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
        try {
            String read = FileLoader.loadResourceFile(filename);
            JSONObject js = new JSONObject(read);
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
                    String type = ingredient.get(0);
                    type = type.substring(1, type.length() - 1);
                    int amount = Integer.parseInt(ingredient.get(1));

                    ingredients.put(type, amount);
                }

                Recipe recipe = new Recipe(ingredients);
                recipeCollection.add(recipe);
            }
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        return recipeCollection;
    }
    public static void main(String[] args) {
        ReadRecipe read = new ReadRecipe();
        String filename = "/dungeonmania/entities/collectable/buildable/recipes/shieldRecipe.json";
        
        for (Recipe recipe : read.readRecipes(filename)) {
            System.out.println("------------------");
            for (HashMap.Entry<String, Integer> ingredient : recipe.getIngredients().entrySet()) {
                System.out.println(ingredient.getKey() + " " + ingredient.getValue());
            }
        }
    }
    
}