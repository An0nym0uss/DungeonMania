package dungeonmania.entities.collectable.buildable;

import java.util.List;

import dungeonmania.entities.collectable.CollectableEntity;
import dungeonmania.entities.player.Inventory;
import dungeonmania.entities.player.Recipe;
import dungeonmania.util.Position;

public interface Buildable{
    public List<Recipe> getRecipes();

    // public boolean isBuildable(Inventory inventory) {
    //     for (Recipe recipe : recipes) {
    //         if (!recipe.isCraftable(inventory)) {
    //             return false;
    //         }
    //     }
    //     return true;
    // }
}