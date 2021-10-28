package dungeonmania.entities.collectable.rarecollectable;

import dungeonmania.entities.player.StatusEffect;
import dungeonmania.entities.player.Player;

public class TheOneRing extends RareCollectableEntities{
    
    public TheOneRing(String id) {
        this.id = id;
        this.type = "the_one_ring";
        this.dropRate = 1;
    }

    public void respawn(Player player, StatusEffect statusEffect) {
        player.setCurrentHealth(player.getMaxHealth());
        statusEffect.setInvincible(true);
        statusEffect.setInvincibleDuration(3);
    }
}