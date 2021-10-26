package dungeonmania.entities.collectable;

import dungeonmania.response.models.ItemResponse;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.player.StatusEffect;

public abstract class CollectableEntity {
    protected String id;
    protected String type;

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void useItem(Player player) {

    }

    public void useItemWithEffect(StatusEffect statusEffect) {

    }

    public ItemResponse createItemResponse() {
        return new ItemResponse(id, type);
    }
}