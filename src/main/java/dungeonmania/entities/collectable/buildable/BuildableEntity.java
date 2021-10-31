package dungeonmania.entities.collectable.buildable;

import java.util.List;

import dungeonmania.entities.collectable.CollectableEntity;
import dungeonmania.entities.player.Inventory;
import dungeonmania.entities.player.Recipe;
import dungeonmania.util.Position;

public abstract class BuildableEntity extends CollectableEntity{
    protected List<Recipe> recipes;

    public BuildableEntity(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
    }

    public List<Recipe> getRecipes() {
        return this.recipes;
    }

    // public boolean isBuildable(Inventory inventory) {
    //     for (Recipe recipe : recipes) {
    //         if (!recipe.isCraftable(inventory)) {
    //             return false;
    //         }
    //     }
    //     return true;
    // }
}