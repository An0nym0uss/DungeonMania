package dungeonmania.entities.collectable;

import dungeonmania.entities.player.StatusEffect;
import dungeonmania.util.Position;

public class InvincibilityPotion extends CollectableEntity {

    public InvincibilityPotion(Position position) {
        super("invincibility_potion", position, false);
    }

    
    @Override
    public void useItemWithEffect(StatusEffect statusEffect) {
        statusEffect.setInvincible(true);
        statusEffect.setInvincibleDuration(10);
    }
}

