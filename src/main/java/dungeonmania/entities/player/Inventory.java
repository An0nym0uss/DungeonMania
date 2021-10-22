package dungeonmania.entities.player;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.collectable.CollectableEntities;
import dungeonmania.entities.player.Recipe;

public class Inventory {

    private List<CollectableEntities> items = new ArrayList<CollectableEntities>();
    private List<Recipe> recipe = new ArrayList<Recipe>();

    public void addItem(CollectableEntities item) {

    }

    public boolean checkItem(CollectableEntities item) {

        return false;
    }

    public void addRecipe(CollectableEntities item) {

    }

    public boolean checkRecipe(CollectableEntities item) {

        return false;
    }
}