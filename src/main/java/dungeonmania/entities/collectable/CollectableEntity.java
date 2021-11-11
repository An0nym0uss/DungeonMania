package dungeonmania.entities.collectable;

import dungeonmania.util.Position;
import dungeonmania.Grid;
import dungeonmania.entities.Entity;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.player.StatusEffect;

public abstract class CollectableEntity extends Entity{
    
    /**
     * Constructor for CollectableEntity.
     * @param id
     * @param type
     * @param position
     * @param isInteractable
     */
    public CollectableEntity(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
    }

    public void useItem(Player player) {

    }

    public void useItemWithEffect(StatusEffect statusEffect) {

    }

    public void placeBomb(Player player, Grid grid) {

    }

    @Override
    public CollectableEntity clone() {
        return this;
    }
}
