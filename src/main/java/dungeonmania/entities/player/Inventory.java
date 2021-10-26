package dungeonmania.entities.player;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.collectable.CollectableEntity;

public class Inventory {

    private List<CollectableEntity> items = new ArrayList<CollectableEntity>();
    private List<Recipe> recipes = new ArrayList<Recipe>();


    public void addItem(CollectableEntity item) {
        items.add(item);
    }


    public void removeItem(CollectableEntity item) {
        for (CollectableEntity ownedItem : items) {
            if (item.getId() == ownedItem.getId()) {
               items.remove(ownedItem);
            }
        }
    }
    

    public int checkItem(String item) {
        int numberOfItem = 0;
        for (CollectableEntity ownedItem : items) {
            if (item == ownedItem.getType()) {
                numberOfItem++;
            }
        }
        return numberOfItem;
    }


    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }


    public boolean checkRecipe(Recipe recipe) {
        boolean recipeExist = false;
        for (Recipe ownedRecipe : recipes) {
            if (ownedRecipe.getIngredients() == ownedRecipe.getIngredients()) {
                recipeExist = true;
            }
        }
        return recipeExist;
    }
}