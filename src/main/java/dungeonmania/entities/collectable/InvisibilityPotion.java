package dungeonmania.entities.collectable;

import dungeonmania.entities.player.Player;

public class InvisibilityPotion extends CollectableEntity {

    public InvisibilityPotion(String id, String type) {
        this.id = id;
        this.type = "health_potion";
    }

    @Override
    public void useItem(Player player) {
        player.setInvisible(true);
    }
}
