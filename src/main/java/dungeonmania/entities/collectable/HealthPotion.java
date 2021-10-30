package dungeonmania.entities.collectable;

import dungeonmania.entities.player.Player;
import dungeonmania.util.Position;

public class HealthPotion extends CollectableEntity{

    public HealthPotion(Position position) {
        super("health_potion", position, false);
    }

    @Override
    public void useItem(Player player) {
        player.setCurrentHealth(player.getMaxHealth());
    }
}
