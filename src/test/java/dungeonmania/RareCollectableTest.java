package dungeonmania;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import dungeonmania.entities.player.Player;
import dungeonmania.entities.enemy.Enemy;
import dungeonmania.entities.enemy.Spider;
import dungeonmania.entities.Entity;

import dungeonmania.constants.Layer;
import dungeonmania.modes.Standard;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class RareCollectableTest {
    @Test
    public void testSpawn() {

        Player player = new Player(new Position(1, 1, Layer.PLAYER), new Standard());
        Grid grid = new Grid(10, 1010, new Entity[1010][10][Layer.LAYER_SIZE], null);
        grid.attach(player);

        for (int i = 2; i < 1000; i++) {
            Enemy enemy = new Spider(new Position(i, 1), 1, 1, 0);
            grid.attach(enemy);
        }

        int ringSpawned = 0;
        int swordSpawned = 0;
        for (int j = 0; j < 1003; j++) {
            player.move(grid, Direction.RIGHT);
            if (player.getInventory().getItem("the_one_ring") != null) {
                player.getInventory().removeNonSpecificItem("the_one_ring");
                ringSpawned++;
            }
            else if (player.getInventory().getItem("anduril") != null) {
                player.getInventory().removeNonSpecificItem("anduril");
                swordSpawned++;
            }
            player.setCurrentHealth(player.getMaxHealth());
        }
        assertTrue(ringSpawned > 25 && ringSpawned < 75);
        assertTrue(swordSpawned > 75 && swordSpawned < 125);
    }
}
