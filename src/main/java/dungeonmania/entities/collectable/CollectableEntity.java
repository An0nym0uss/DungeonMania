package dungeonmania.entities.collectable;

import dungeonmania.response.models.ItemResponse;
import dungeonmania.entities.Entity;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.player.StatusEffect;

public abstract class CollectableEntity extends Entity{
    public void useItem(Player player) {

    }

    public void useItemWithEffect(StatusEffect statusEffect) {

    }

    public ItemResponse createItemResponse() {
        return new ItemResponse(id, type);
    }
}
