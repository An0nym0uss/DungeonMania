package dungeonmania;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import dungeonmania.entities.collectable.*;
import dungeonmania.entities.collectable.rarecollectable.*;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.enemy.Enemy;
import dungeonmania.entities.enemy.Spider;
import dungeonmania.entities.Entity;

import dungeonmania.constants.Layer;
import dungeonmania.modes.Standard;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BattleTest {
    @Test
    public void testBattleEnemy() {

        Player player = new Player(new Position(1, 1, Layer.PLAYER), new Standard());
        Enemy enemy = new Spider(new Position(2, 1), 1, 1, 1);
        Enemy shelob = new Spider(new Position(3, 1), 1, 20, 20);
        Grid grid = new Grid(10, 10, new Entity[10][10][Layer.LAYER_SIZE], null);
        grid.attach(player);
        grid.attach(enemy);
        grid.attach(shelob);

        // enemy dead
        player.move(grid, Direction.RIGHT);
        assertTrue(grid.getEntities(2, 1).size() == 1);
        assertTrue(grid.getEntities(2, 1).get(0).getType() == "player");

        // player dead
        player.move(grid, Direction.RIGHT);
        assertTrue(grid.getEntities(3, 1).size() == 1);
        assertTrue(grid.getEntities(3, 1).get(0).getType() == "spider");
    }

    @Test
    public void testHealthPotion() {

        Player player = new Player(new Position(1, 1, Layer.PLAYER), new Standard());
        Enemy enemy = new Spider(new Position(2, 1), 1, 1, 1);
        CollectableEntity hpPotion = new HealthPotion(new Position(4, 1, Layer.COLLECTABLE));
        Grid grid = new Grid(10, 10, new Entity[10][10][Layer.LAYER_SIZE], null);
        grid.attach(player);
        grid.attach(enemy);

        // enemy dead, player injured
        player.move(grid, Direction.RIGHT);
        assertTrue(grid.getEntities(2, 1).size() == 1);
        assertTrue(grid.getEntities(2, 1).get(0).getType() == "player");
        assertTrue(player.getCurrentHealth() < player.getMaxHealth());

        // pick up health potion
        player.move(grid, Direction.RIGHT);
        assertTrue(player.getCurrentHealth() == player.getMaxHealth());

    }

    @Test
    public void testOneRing() {

        Player player = new Player(new Position(1, 1, Layer.PLAYER), new Standard());
        Enemy shelob = new Spider(new Position(2, 1), 1, 20, 20);
        Grid grid = new Grid(10, 10, new Entity[10][10][Layer.LAYER_SIZE], null);
        grid.attach(player);
        grid.attach(shelob);

        // spawn a one ring
        RareCollectableEntities ring = new TheOneRing(new Position(0, 0));
        ring.setDropRate(100);
        ring.spawnnRareCollectableEntities(player.getInventory());
        assertTrue(player.getInventory().getItems().size() > 0);

        // shelob dead, player at max health
        player.move(grid, Direction.RIGHT);
        assertTrue(grid.getEntities(2, 1).size() == 1);
        assertTrue(grid.getEntities(2, 1).get(0).getType() == "player");
        assertTrue(player.getCurrentHealth() == player.getMaxHealth());

    }

    @Test
    public void testDamage() {

        Player player = new Player(new Position(1, 1, Layer.PLAYER), new Standard());
        CollectableEntity sword = new Sword(new Position(7, 1, Layer.COLLECTABLE));
        CollectableEntity wood = new Wood(new Position(3, 1, Layer.COLLECTABLE));
        CollectableEntity arrow1 = new Arrow(new Position(4, 1, Layer.COLLECTABLE));
        CollectableEntity arrow2 = new Arrow(new Position(5, 1, Layer.COLLECTABLE));
        CollectableEntity arrow3 = new Arrow(new Position(6, 1, Layer.COLLECTABLE));
        Grid grid = new Grid(10, 10, new Entity[10][10][Layer.LAYER_SIZE], null);
        grid.attach(player);
        grid.attach(sword);
        grid.attach(wood);
        grid.attach(arrow1);
        grid.attach(arrow2);
        grid.attach(arrow3);

        // get the potion
        for (int i = 0; i < 5; i++){
            player.move(grid, Direction.RIGHT);
        }

        // build a bow
        assertTrue(player.getBuildables().size() > 0);
        int prevDamage = player.damageDealt();
        player.craftItem(player.getBuildables().get(0));
        assertTrue(player.damageDealt() == prevDamage*2);
        prevDamage = player.damageDealt();

        // pick up sword
        player.move(grid, Direction.RIGHT);
        assertTrue(player.damageDealt() > prevDamage);
    }

    @Test
    public void testDefend() {

        Player player = new Player(new Position(1, 1, Layer.PLAYER), new Standard());
        CollectableEntity armour = new Armour(new Position(2, 1, Layer.COLLECTABLE));
        CollectableEntity wood1 = new Wood(new Position(3, 1, Layer.COLLECTABLE));
        CollectableEntity wood2 = new Wood(new Position(4, 1, Layer.COLLECTABLE));
        CollectableEntity treasure = new Treasure(new Position(5, 1, Layer.COLLECTABLE));
        Enemy enemy1 = new Spider(new Position(1, 2), 1, 1, 6);
        Enemy enemy2 = new Spider(new Position(2, 2), 1, 1, 6);
        Enemy enemy3 = new Spider(new Position(5, 2), 1, 1, 6);
        Grid grid = new Grid(10, 10, new Entity[10][10][Layer.LAYER_SIZE], null);
        grid.attach(player);
        grid.attach(armour);
        grid.attach(wood1);
        grid.attach(wood2);
        grid.attach(treasure);
        grid.attach(enemy1);
        grid.attach(enemy2);
        grid.attach(enemy3);

        // kill the first enemy
        player.move(grid, Direction.DOWN);
        player.move(grid, Direction.UP);
        int damageTaken = player.getMaxHealth() - player.getCurrentHealth();
        player.setCurrentHealth(player.getMaxHealth());

        // pick up armour
        player.move(grid, Direction.RIGHT);
        // kill the second enemy and compare damage
        player.move(grid, Direction.DOWN);
        player.move(grid, Direction.UP);
        assertTrue(player.getMaxHealth() - player.getCurrentHealth() > damageTaken);
        damageTaken = player.getMaxHealth() - player.getCurrentHealth();
        player.setCurrentHealth(player.getMaxHealth());

        // pick up ingredients and build shield
        player.move(grid, Direction.RIGHT);
        player.move(grid, Direction.RIGHT);
        player.move(grid, Direction.RIGHT);
        assertTrue(player.getBuildables().size() > 0);
        player.craftItem(player.getBuildables().get(0));
        // kill the third enemy and compare damage
        player.move(grid, Direction.DOWN);
        player.move(grid, Direction.UP);
        assertTrue(player.getMaxHealth() - player.getCurrentHealth() > damageTaken);
    }
    
}

    // @Test
    // public void testDamage() {

    //     Player player = new Player(new Position(1, 1, Layer.PLAYER), new Standard());
    //     CollectableEntity sword = new Sword(new Position(2, 1, Layer.COLLECTABLE));
    //     CollectableEntity wood = new Wood(new Position(3, 1, Layer.COLLECTABLE));
    //     CollectableEntity arrow1 = new Arrow(new Position(4, 1, Layer.COLLECTABLE));
    //     CollectableEntity arrow2 = new Arrow(new Position(5, 1, Layer.COLLECTABLE));
    //     CollectableEntity arrow3 = new Arrow(new Position(6, 1, Layer.COLLECTABLE));
    //     Grid grid = new Grid(10, 10, new Entity[10][10][Layer.LAYER_SIZE], null);
    //     grid.attach(player);
    //     grid.attach(sword);
    //     grid.attach(wood);
    //     grid.attach(arrow1);
    //     grid.attach(arrow2);
    //     grid.attach(arrow3);

    //     // get the potion
    //     for (int i = 0; i < 5; i++){
    //         player.move(grid, Direction.RIGHT);
    //     }
    //     Map<String, Integer> map = new HashMap<String, Integer>();
    //     map.put("wood",1);
    //     map.put("arrow", 3);
    //     Recipe recipe = new Recipe("bow",map);
    //     assertTrue(recipe.isCraftable(player.getInventory()));
    //     assertTrue(player.getBuildables().size() > 0);
    // }