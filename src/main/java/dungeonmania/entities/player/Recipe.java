package dungeonmania.entities.player;

import java.util.HashMap;
import java.util.Map;

public class Recipe {
    private String type;
    private Map<String, Integer> ingredients = new HashMap<String, Integer>();

    public Recipe(String type, Map<String, Integer> ingredients) {
        this.type = type;
        this.ingredients = ingredients;
    }

    public String getType() {
        return this.type;
    }

    public Map<String, Integer> getIngredients() {
        return this.ingredients;
    }


    public boolean isCraftable(Inventory inventory) {
        boolean craftable = true;
        for (HashMap.Entry<String, Integer> ingredient : ingredients.entrySet()) {
            if (inventory.checkItem(ingredient.getKey()) < ingredient.getValue()) {
                craftable = false;
            }
        }
        return craftable;
    }
}