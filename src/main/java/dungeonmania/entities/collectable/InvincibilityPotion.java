package dungeonmania.entities.collectable;

import dungeonmania.entities.player.Player;

public class InvincibilityPotion extends CollectableEntity {

    public InvincibilityPotion(String id, String type) {
        this.id = id;
        this.type = "health_potion";
    }

    @Override
    public void useItem(Player player) {
        player.setInvincible(true);
    }
}
