package dungeonmania.response.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.Grid;
import dungeonmania.entities.Entity;
import dungeonmania.entities.collectable.CollectableEntity;
import dungeonmania.entities.player.Inventory;
import dungeonmania.util.Position;

/**
 * 
 * 
 * @author Enoch Kavur (z5258204)
 */
public class StandardResponseFactory implements ResponseFactory {

    /**
     * @param dungeon
     * @pre dungeon is not null
     * @post DungeonResponse is created and returned
     */
    @Override
    public DungeonResponse createDungeonResponse(Dungeon dungeon) {
        String dungeonId = dungeon.getDungeonId();
        String dungeonName = dungeon.getDungeonName();
        List<EntityResponse> entities = createEntityResponseList(dungeon.getGrid());
        List<ItemResponse> inventory = createItemResponseList(dungeon.getGrid().getPlayer().getInventory()); // Bad code right here.
        List<String> buildables = dungeon.getGrid().getPlayer().getBuildables();// TODO
        String goals = "";
        if (dungeon.getGoal() != null) {
            goals = dungeon.getGoal().toString();
        }
        List<AnimationQueue> animations = createAnimations(dungeon.getGrid());

        return new DungeonResponse(dungeonId, dungeonName, entities, inventory, buildables, goals, animations);
    }

    /**
     * @param grid
     * @pre grid is not null
     * @post List of EntityResponse is created and returned
     */
    @Override
    public List<EntityResponse> createEntityResponseList(Grid grid) {

        List<EntityResponse> entities = new ArrayList<>();

        for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {
                for (Entity entity : grid.getEntities(x, y)) {
                    entities.add(createEntityResponse(entity));
                }
            }
        }
        
        return entities;
    }

    /**
     * @param grid
     * @pre grid is not null
     * @post List of EntityResponse is created and returned
     */
    public List<AnimationQueue> createAnimations(Grid grid) {

        List<AnimationQueue> animations = new ArrayList<>();

        for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {
                for (Entity entity : grid.getEntities(x, y)) {
                    AnimationQueue animation = entity.getAnimation();

                    if (animation != null) animations.add(animation);
                }
            }
        }
        
        return animations;
    }

    /**
     * @param entity
     * @pre entity is not null
     * @post EntityResponse is created and returned
     */
    @Override
    public EntityResponse createEntityResponse(Entity entity) {
        String id = entity.getId();
        String type = entity.getType();
        Position position = entity.getPosition();
        boolean isInteractable = entity.isInteractable();

        return new EntityResponse(id, type, position, isInteractable);
    }

    @Override
    public List<ItemResponse> createItemResponseList(Inventory inventory) {

        List<ItemResponse> items = new ArrayList<>();
        
        for (CollectableEntity item : inventory.getItems()) {
            items.add(createItemResponse(item));
        }
        return items;
    }

    @Override
    public ItemResponse createItemResponse(CollectableEntity item) {

        return new ItemResponse(item.getId(), item.getType());
    }
    
}
