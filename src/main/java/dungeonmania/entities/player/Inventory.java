package dungeonmania.entities.player;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.collectable.CollectableEntity;

public class Inventory {

    private List<CollectableEntity> items = new ArrayList<CollectableEntity>();
    private List<Recipe> recipe = new ArrayList<Recipe>();

    public void addItem(CollectableEntity item) {

    }

    public boolean checkItem(CollectableEntity item) {

        return false;
    }

    public void addRecipe(CollectableEntity item) {

    }

    public boolean checkRecipe(CollectableEntity item) {

        return false;
    }
}