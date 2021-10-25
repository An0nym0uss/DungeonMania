package dungeonmania.entities.collectable;

import dungeonmania.entities.player.Player;

public class HealthPotion extends CollectableEntity{

    public HealthPotion(String id, String type) {
        this.id = id;
        this.type = "health_potion";
    }

    @Override
    public void useItem(Player player) {
        player.setCurrentHealth(player.getMaxHealth());
    }
}
