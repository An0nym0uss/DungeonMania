package dungeonmania.entities.collectable;

import dungeonmania.response.models.ItemResponse;
<<<<<<< HEAD
=======
import dungeonmania.Grid;
>>>>>>> master
import dungeonmania.entities.Entity;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.player.StatusEffect;

<<<<<<< HEAD
public abstract class CollectableEntity extends Entity {
    protected String id;
    protected String type;

    public String getId() {
        return id;
    }
=======
public abstract class CollectableEntity extends Entity{
    public void useItem(Player player) {
>>>>>>> master

    }

    public void useItemWithEffect(StatusEffect statusEffect) {

    }

    public void placeBomb(Player player, Grid grid) {

    }

    public ItemResponse createItemResponse() {
        return new ItemResponse(id, type);
    }
}
