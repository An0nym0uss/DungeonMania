package dungeonmania;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import dungeonmania.entities.collectable.CollectableEntity;
import dungeonmania.entities.collectable.InvincibilityPotion;
import dungeonmania.entities.collectable.InvisibilityPotion;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.enemy.Enemy;
import dungeonmania.entities.enemy.Spider;
import dungeonmania.entities.Entity;

import dungeonmania.constants.Layer;
import dungeonmania.modes.Standard;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class PotionTest {
    @Test
    public void testDuration() {

        Player player = new Player(new Position(1, 1, Layer.PLAYER), new Standard());
        CollectableEntity potion = new InvisibilityPotion(new Position(3, 1, Layer.COLLECTABLE));
        Grid grid = new Grid(10, 10, new Entity[10][10][Layer.LAYER_SIZE], null);
        grid.attach(player);
        grid.attach(potion);

        // get the potion
        player.move(grid, Direction.RIGHT);
        player.move(grid, Direction.RIGHT);

        // use the potion
        player.useItem(potion.getId(), grid);
        int prevDuration = player.getStatusEffect().getInvisibleDuration();

        player.move(grid, Direction.RIGHT);
        player.move(grid, Direction.RIGHT);

        assertTrue(player.getStatusEffect().getInvisibleDuration() < prevDuration);

    }

    @Test
    public void testInvisibilityEffect() {

        Player player = new Player(new Position(1, 1, Layer.PLAYER), new Standard());
        CollectableEntity potion = new InvisibilityPotion(new Position(3, 1, Layer.COLLECTABLE));
        Enemy enemy = new Spider(new Position(5, 1), 1, 1, 1);
        Grid grid = new Grid(10, 10, new Entity[10][10][Layer.LAYER_SIZE], null);
        grid.attach(player);
        grid.attach(potion);
        grid.attach(enemy);

        // get the potion
        player.move(grid, Direction.RIGHT);
        player.move(grid, Direction.RIGHT);

        // use the potion, enemy still alive
        player.useItem(potion.getId(), grid);
        int hp = player.getCurrentHealth();
        assertTrue(grid.getEntities(5, 1).size() > 0);

        // pass through the enemy
        player.move(grid, Direction.RIGHT);
        player.move(grid, Direction.RIGHT);
        player.move(grid, Direction.RIGHT);

        // enemy should still alive and player unharmed
        assertTrue(grid.getEntities(5, 1).size() > 0);
        assertTrue(player.getCurrentHealth() == hp);
    }

    @Test
    public void testInvincibilityEffect() {

        Player player = new Player(new Position(1, 1, Layer.PLAYER), new Standard());
        CollectableEntity potion = new InvincibilityPotion(new Position(3, 1, Layer.COLLECTABLE));
        Enemy enemy = new Spider(new Position(5, 1), 1, 1, 1);
        Grid grid = new Grid(10, 10, new Entity[10][10][Layer.LAYER_SIZE], null);
        grid.attach(player);
        grid.attach(potion);
        grid.attach(enemy);

        // get the potion
        player.move(grid, Direction.RIGHT);
        player.move(grid, Direction.RIGHT);

        // use the potion and enemy shuld still alive
        player.useItem(potion.getId(), grid);
        int hp = player.getCurrentHealth();
        assertTrue(grid.getEntities(5, 1).size() > 0);

        // kill the enemy
        player.move(grid, Direction.RIGHT);
        player.move(grid, Direction.RIGHT);
        player.move(grid, Direction.RIGHT);

        // enemy should be dead and player unharmed
        assertTrue(grid.getEntities(5, 1).size() == 0);
        assertTrue(player.getCurrentHealth() == hp);
    }
}
