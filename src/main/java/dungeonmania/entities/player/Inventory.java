package dungeonmania.entities.player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dungeonmania.entities.collectable.CollectableEntity;

public class Inventory {

    private List<CollectableEntity> items;
    private List<Recipe> recipes;
    private RecipeAll recipesAll;


    public Inventory() {
        items = new ArrayList<CollectableEntity>();
        recipesAll = new RecipeAll();
        recipes = recipesAll.getRecipes();
    }

    public void addItem(CollectableEntity item) {
        items.add(item);
    }

    public void removeItem(CollectableEntity item) {
        Iterator<CollectableEntity> itr = items.iterator();
        while (itr.hasNext()) {
            CollectableEntity e = itr.next();

            if (e.getId() == item.getId()) {
                itr.remove();
            }
        }
    }

    public List<CollectableEntity> getItems() {
        return this.items;
    }

    public CollectableEntity getItem(String type) {
        for (CollectableEntity item : this.items) {
            if (item.getType().equals(type)) {
                return item;
            }
        }

        return null;
    }

    public CollectableEntity getItemfromId(String id) {
        for (CollectableEntity item : this.items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }

        return null;
    }

    public void removeNonSpecificItem(String item) {
        Iterator<CollectableEntity> itr = items.iterator();
        while (itr.hasNext()) {
            CollectableEntity e = itr.next();

            if (e.getType().equals(item)) {
                itr.remove();
            }
        }
    }

    public int checkItem(String item) {
        int numberOfItem = 0;
        for (CollectableEntity ownedItem : items) {
            if (item.equals(ownedItem.getType())) {
                numberOfItem++;
            }
        }
        return numberOfItem;
    }

    public List<Recipe> getRecipes() {
        return this.recipes;
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