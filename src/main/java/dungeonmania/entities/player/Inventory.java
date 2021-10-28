package dungeonmania.entities.player;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.collectable.CollectableEntity;
import dungeonmania.response.models.ItemResponse;

public class Inventory {

    private List<CollectableEntity> items;
    private List<Recipe> recipes;


    public Inventory() {
        items = new ArrayList<CollectableEntity>();
        recipes = new ArrayList<Recipe>();
    }


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

    public List<CollectableEntity> getItems() {
        return this.items;
    }

    public CollectableEntity getItem(String type) {
        for (CollectableEntity item : this.items) {
            if (item.getType() == type) {
                return item;
            }
        }

        return null;
    }


    public void removeNonSpecificItem(String item) {
        for (CollectableEntity ownedItem : items) {
            if (item == ownedItem.getType()) {
                items.remove(ownedItem);
                break;
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

    public List<ItemResponse> inventoryItems () {
        List<ItemResponse> itemsInfo = new ArrayList<ItemResponse>();
        for (CollectableEntity item : items) {
            itemsInfo.add(item.createItemResponse());
        }
        return itemsInfo;
    }
}