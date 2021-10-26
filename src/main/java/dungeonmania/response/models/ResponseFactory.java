package dungeonmania.response.models;

import dungeonmania.Dungeon;
import dungeonmania.Grid;
import dungeonmania.entities.Entity;
import dungeonmania.entities.collectable.CollectableEntity;
import dungeonmania.entities.player.Inventory;

import java.util.List;

/**
 * Abstract Factory Method Interface for creating responses.
 * 
 * @author Enoch Kavur (z5258204)
 */
public interface ResponseFactory {

    /**
     * @param dungeon
     * @pre dungeon is not null
     * @post DungeonResponse is created and returned
     */
    public DungeonResponse createDungeonResponse(Dungeon dungeon);
    
    /**
     * @param grid
     * @pre grid is not null
     * @post List of EntityResponse is created and returned
     */
    public List<EntityResponse> createEntityResponseList(Grid grid);

    /**
     * @param entity
     * @pre entity is not null
     * @post EntityResponse is created and returned
     */
    public EntityResponse createEntityResponse(Entity entity);

    /**
     * @param inventory
     * @pre inventory is not null
     * @post List of ItemResponse is created and returned
     */
    public List<ItemResponse> createItemResponseList(Inventory inventory);
    
    /**
     * @param item
     * @pre entity is not null
     * @post EntityResponse is created and returned
     */
    public ItemResponse createItemResponse(CollectableEntity item);
}
