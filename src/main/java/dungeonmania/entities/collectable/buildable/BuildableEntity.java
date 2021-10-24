package dungeonmania.entities.collectable.buildable;

import java.util.List;

import dungeonmania.entities.collectable.CollectableEntity;
import dungeonmania.entities.player.Recipe;

public abstract class BuildableEntity extends CollectableEntity{
    protected List<Recipe> recipe;
}