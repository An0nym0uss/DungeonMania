
package dungeonmania.entities.player;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private List<String> ingredients = new ArrayList<String>();

    public Recipe(List<String> ingredients) {
        this.ingredients = ingredients;
    }


    public List<String> getIngredients() {
        return this.ingredients;
    }


    public boolean isCraftable(Inventory inventory) {
        // TO-DO:
        return false;
    }

    

}