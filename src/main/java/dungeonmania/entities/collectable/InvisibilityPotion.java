package dungeonmania.entities.collectable;

import dungeonmania.entities.player.StatusEffect;
import dungeonmania.util.Position;

public class InvisibilityPotion extends CollectableEntity {

    public InvisibilityPotion(Position position) {
        super("invisibility_potion", position, false);
    }

    @Override
    public void useItemWithEffect(StatusEffect statusEffect) {
        statusEffect.setInvisible(true);
        statusEffect.setInvisibleDuration(10);
    }

    @Override
    public InvisibilityPotion clone() {
        return new InvisibilityPotion(this.getPosition());
    }
}

