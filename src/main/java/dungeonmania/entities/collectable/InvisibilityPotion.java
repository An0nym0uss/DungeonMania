package dungeonmania.entities.collectable;

import dungeonmania.entities.player.StatusEffect;

public class InvisibilityPotion extends CollectableEntity {

    public InvisibilityPotion(Position position) {
        super("invisible_potion", position, false);
    }

    @Override
    public void useItemWithEffect(StatusEffect statusEffect) {
        statusEffect.setInvisible(true);
        statusEffect.setInvisibleDuration(10);
    }
}

