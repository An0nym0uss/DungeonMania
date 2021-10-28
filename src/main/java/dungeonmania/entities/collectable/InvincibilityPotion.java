package dungeonmania.entities.collectable;

import dungeonmania.entities.player.StatusEffect;

public class InvincibilityPotion extends CollectableEntity {

    public InvincibilityPotion(String id) {
        this.id = id;
        this.type = "invincibility_potion";
    }
    
    @Override
    public void useItemWithEffect(StatusEffect statusEffect) {
        statusEffect.setInvincible(true);
        statusEffect.setInvincibleDuration(10);
    }
}

