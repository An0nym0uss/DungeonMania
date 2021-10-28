package dungeonmania.entities.collectable;

import dungeonmania.entities.player.StatusEffect;
import dungeonmania.util.Position;

public class InvisibilityPotion extends CollectableEntity {

    public InvisibilityPotion(Position position) {
        super("invisible_potion", position, false);
    }

    @Override
    public void useItemWithEffect(StatusEffect statusEffect) {
        statusEffect.setInvisible(true);
    }
}
