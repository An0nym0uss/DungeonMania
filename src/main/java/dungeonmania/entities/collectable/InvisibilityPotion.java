package dungeonmania.entities.collectable;

import dungeonmania.entities.player.StatusEffect;

public class InvisibilityPotion extends CollectableEntity {

    public InvisibilityPotion(String id) {
        this.id = id;
        this.type = "invisible_potion";
    }

    @Override
    public void useItemWithEffect(StatusEffect statusEffect) {
        statusEffect.setInvisible(true);
        statusEffect.setInvisibleDuration(10);
    }
}

