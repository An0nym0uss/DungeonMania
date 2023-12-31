package dungeonmania;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dungeonmania.entities.collectable.Armour;
import dungeonmania.entities.collectable.Arrow;
import dungeonmania.entities.collectable.CollectableEntity;
import dungeonmania.entities.collectable.HealthPotion;
import dungeonmania.entities.collectable.SunStone;
import dungeonmania.entities.collectable.Sword;
import dungeonmania.entities.collectable.Treasure;
import dungeonmania.entities.collectable.Wood;
import dungeonmania.entities.collectable.rarecollectable.*;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.statics.Boulder;
import dungeonmania.entities.statics.Door;
import dungeonmania.entities.statics.Wall;
import dungeonmania.entities.enemy.Enemy;
import dungeonmania.entities.enemy.Mercenary;
import dungeonmania.entities.enemy.Spider;
import dungeonmania.entities.Entity;

import dungeonmania.constants.Layer;
import dungeonmania.modes.Hard;
import dungeonmania.modes.Peaceful;
import dungeonmania.modes.Standard;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BattleTest {
    @Test
    public void testBattleEnemy() {

        Player player = new Player(new Position(1, 1, Layer.PLAYER), new Standard());
        Enemy enemy = new Spider(new Position(2, 1), 1, 1, 1);
        Enemy shelob = new Spider(new Position(3, 1), 1, 100, 100);
        Grid grid = new Grid(10, 10, new Entity[10][10][Layer.LAYER_SIZE], null);
        grid.attach(player);
        grid.attach(enemy);
        grid.attach(shelob);
        player.setRareDrop(false);

        // enemy dead
        player.move(grid, Direction.RIGHT);
        assertTrue(grid.getEntities(2, 1).size() == 1);
        assertTrue(grid.getEntities(2, 1).get(0).getType().equals("player"));

        // player dead
        player.move(grid, Direction.RIGHT);
        assertTrue(grid.getEntities(3, 1).size() == 1);
        assertTrue(grid.getEntities(3, 1).get(0).getType().equals("spider"));
    }

    @Test
    public void testHealthPotion() {

        Player player = new Player(new Position(1, 1, Layer.PLAYER), new Standard());
        Enemy enemy = new Spider(new Position(2, 1), 1, 1, 1);
        CollectableEntity hpPotion = new HealthPotion(new Position(3, 1, Layer.COLLECTABLE));
        Grid grid = new Grid(10, 10, new Entity[10][10][Layer.LAYER_SIZE], null);
        grid.attach(player);
        grid.attach(enemy);
        grid.attach(hpPotion);

        // enemy dead, player injured
        player.move(grid, Direction.RIGHT);
        assertTrue(grid.getEntities(2, 1).size() == 1);
        assertTrue(grid.getEntities(2, 1).get(0).getType().equals("player"));
        assertTrue(player.getCurrentHealth() < player.getMaxHealth());

        // pick up health potion
        player.move(grid, Direction.RIGHT);
        assertTrue(!player.getInventory().getItems().isEmpty());

        String id = "";
        if (player.getInventory().getItems().get(0).getType().equalsIgnoreCase("health_potion")) {   
            id = player.getInventory().getItems().get(0).getId();
        } else {
            id = player.getInventory().getItems().get(1).getId();
        }
        player.useItem(id, grid);
        assertTrue(player.getCurrentHealth() == player.getMaxHealth());
    }

    @Test
    public void testOneRing() {

        Player player = new Player(new Position(1, 1, Layer.PLAYER), new Standard());
        Enemy shelob = new Spider(new Position(2, 1), 1, 100, 100);
        Grid grid = new Grid(10, 10, new Entity[10][10][Layer.LAYER_SIZE], null);
        grid.attach(player);
        grid.attach(shelob);
        player.setRareDrop(false);

        // spawn a one ring
        RareCollectableEntities ring = new TheOneRing();
        ring.setDropRate(100);
        ring.spawnnOneRing(player.getInventory());
        assertTrue(player.getInventory().getItems().size() > 0);

        // shelob dead, player at max health
        player.move(grid, Direction.RIGHT);
        assertTrue(grid.getEntities(2, 1).size() == 1);
        assertTrue(grid.getEntities(2, 1).get(0).getType().equals("player"));
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
        Enemy shelob = new Spider(new Position(7, 2), 1, 200, 10);
        Grid grid = new Grid(10, 10, new Entity[10][10][Layer.LAYER_SIZE], null);
        grid.attach(player);
        grid.attach(sword);
        grid.attach(wood);
        grid.attach(arrow1);
        grid.attach(arrow2);
        grid.attach(arrow3);
        grid.attach(shelob);

        // attack without weapon
        player.move(grid, Direction.DOWN);
        for (int i = 0; i < 6; i++) {
            player.move(grid, Direction.RIGHT);
        }
        assertTrue(grid.getEntities(7, 2).get(0).getType().equals("spider"));
        assertTrue(shelob.getHealth() < 200);
        int prevHealthLeft =  shelob.getHealth();
        grid.dettach(shelob);

         // attack with weapons
        player = new Player(new Position(1, 1, Layer.PLAYER), new Standard());
        shelob = new Spider(new Position(7, 2), 1, 200, 10);
        grid.attach(player);
        grid.attach(shelob);
        for (int i = 0; i < 6; i++) {
            player.move(grid, Direction.RIGHT);
        }
        player.craftItem(player.getBuildables().get(0), grid);
        assertTrue(grid.getEntities(7, 1).get(0).getType().equals("player"));
        assertTrue(player.getInventory().getItems().size() == 2);
        player.move(grid, Direction.DOWN);
        assertTrue(shelob.getHealth() < prevHealthLeft);
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
        assertTrue(player.getMaxHealth() - player.getCurrentHealth() < damageTaken);
        damageTaken = player.getMaxHealth() - player.getCurrentHealth();
        player.setCurrentHealth(player.getMaxHealth());

        // pick up ingredients and build shield
        player.move(grid, Direction.RIGHT);
        player.move(grid, Direction.RIGHT);
        player.move(grid, Direction.RIGHT);
        assertTrue(player.getBuildables().size() > 0);
        player.craftItem(player.getBuildables().get(0), grid);

        // kill the third enemy and compare damage
        player.move(grid, Direction.DOWN);
        player.move(grid, Direction.UP);
        assertTrue(player.getMaxHealth() - player.getCurrentHealth() < damageTaken);
    }

    @Test
    public void testUseAnduril() {
        Player player = new Player(new Position(1, 1, Layer.PLAYER), new Standard());
        Enemy enemy = new Spider(new Position(2, 1), 1, 1, 1);
        Grid grid = new Grid(10, 10, new Entity[10][10][Layer.LAYER_SIZE], null);
        grid.attach(player);
        grid.attach(enemy);

        RareCollectableEntities anduril = new Anduril();
        anduril.setDropRate(100);
        anduril.spawnnAnduril(player, player.getInventory());
        assertTrue(player.getInventory().getItems().size() == 1);

        player.move(grid, Direction.RIGHT);
        assertTrue(grid.getEntities(2, 1).get(0).getType().equals("player"));
    }

    @Test
    public void testUseMidnightArmour() {
        Player player = new Player(new Position(1, 1, Layer.PLAYER), new Standard());
        CollectableEntity armour = new Armour(new Position(2, 1, Layer.COLLECTABLE));
        CollectableEntity sunStone = new SunStone(new Position(3, 1, Layer.COLLECTABLE));
        Enemy enemy = new Spider(new Position(4, 1), 1, 1, 1);
        Grid grid = new Grid(10, 10, new Entity[10][10][Layer.LAYER_SIZE], null);
        grid.attach(player);
        grid.attach(enemy);
        grid.attach(armour);
        grid.attach(sunStone);

        player.move(grid, Direction.RIGHT);
        player.move(grid, Direction.RIGHT);

        assertTrue(player.getInventory().getItems().size() == 2);
        player.craftItem(player.getBuildables().get(0), grid);
        assertTrue(player.getInventory().getItems().size() == 1);

        player.move(grid, Direction.RIGHT);
        assertTrue(grid.getEntities(4, 1).get(0).getType().equals("player"));
    }

    @Test
    public void testBattlePeaceful() {

        Player player = new Player(new Position(1, 1, Layer.PLAYER), new Peaceful());
        Enemy shelob = new Spider(new Position(2, 1), 1, 100, 100);
        Grid grid = new Grid(10, 10, new Entity[10][10][Layer.LAYER_SIZE], null);
        grid.attach(player);
        grid.attach(shelob);

        // enemy dont attack
        player.move(grid, Direction.RIGHT);
        assertTrue(grid.getEntities(2, 1).size() == 1);
        assertTrue(grid.getEntities(2, 1).get(0).getType().equals("player"));
    }

    @Test
    public void testDurabilitySword() {

        Player player = new Player(new Position(1, 1, Layer.PLAYER), new Standard());
        CollectableEntity sword = new Sword(new Position(2, 1, Layer.COLLECTABLE));
        Grid grid = new Grid(20, 20, new Entity[20][20][Layer.LAYER_SIZE], null);
        grid.attach(player);
        grid.attach(sword);
        player.setRareDrop(false);
        for (int i = 0; i < 10; i++) {
            Enemy enemy = new Spider(new Position(i+3, 1), 1, 1, 1);
            grid.attach(enemy);
        }
        player.move(grid, Direction.RIGHT);
        assertTrue(player.getInventory().getItems().size() == 1);
        for (int i = 0; i < 10; i++) {
            player.move(grid, Direction.RIGHT);
        }
        // sword durability reached zero after defeating 10 enemies
        assertTrue(grid.getEntities(12, 1).get(0).getType().equals("player"));
        assertTrue(player.getInventory().getItems().size() == 0);
    }

    @Test
    public void testDurabilityArmour() {

        Player player = new Player(new Position(1, 1, Layer.PLAYER), new Standard());
        CollectableEntity armour = new Armour(new Position(2, 1, Layer.COLLECTABLE));
        Grid grid = new Grid(20, 20, new Entity[20][20][Layer.LAYER_SIZE], null);
        grid.attach(player);
        grid.attach(armour);
        player.setRareDrop(false);
        for (int i = 0; i < 10; i++) {
            Enemy enemy = new Spider(new Position(i + 3, 1), 1, 1, 1);
            grid.attach(enemy);
        }
        player.move(grid, Direction.RIGHT);
        assertTrue(player.getInventory().getItems().size() == 1);
        for (int i = 0; i < 10; i++) {
            player.move(grid, Direction.RIGHT);
        }
        // sword durability reached zero after defeating 10 enemies
        assertTrue(grid.getEntities(12, 1).get(0).getType().equals("player"));
        assertTrue(player.getInventory().getItems().size() == 0);
    }

    @Test
    public void testDurabilityBow() {

        Player player = new Player(new Position(1, 1, Layer.PLAYER), new Standard());
        CollectableEntity wood = new Wood(new Position(2, 1, Layer.COLLECTABLE));
        CollectableEntity arrow1 = new Arrow(new Position(3, 1, Layer.COLLECTABLE));
        CollectableEntity arrow2 = new Arrow(new Position(4, 1, Layer.COLLECTABLE));
        CollectableEntity arrow3 = new Arrow(new Position(5, 1, Layer.COLLECTABLE));
        Grid grid = new Grid(20, 20, new Entity[20][20][Layer.LAYER_SIZE], null);
        grid.attach(player);
        grid.attach(wood);
        grid.attach(arrow1);
        grid.attach(arrow2);
        grid.attach(arrow3);

        player.move(grid, Direction.RIGHT);
        player.move(grid, Direction.RIGHT);
        player.move(grid, Direction.RIGHT);
        player.move(grid, Direction.RIGHT);

        // build a bow
        player.craftItem(player.getBuildables().get(0), grid);
        player.setRareDrop(false);
        for (int i = 0; i < 10; i++) {
            Enemy enemy = new Spider(new Position(i + 6, 1), 1, 100, 1);
            grid.attach(enemy);
        }

        assertTrue(player.getInventory().getItems().size() == 1);
        assertTrue(player.getInventory().getItems().get(0).getType() == "bow");
        for (int i = 0; i < 10; i++) {
            player.move(grid, Direction.RIGHT);
        }
        assertTrue(player.getInventory().getItems().size() == 0);
    }

    // @Test
    // public void testMerc() {
    //     Grid grid = new Grid(10, 10, new Entity[10][10][Layer.LAYER_SIZE], null);
    //     Position p = new Position(1,1);
    //     Mercenary m = new Mercenary(new Position(1,1), 1, 1, 1);

    //     m.damageDealt();
    //     m.isDead();

    //     assertFalse(m.movingConstraints(new Sword(p, 1, 1)));
    //     assertTrue(m.movingConstraints(new Wall(p)));
    //     assertTrue(m.movingConstraints(new Door("door_locked_silver", p, 1, false)));
    //     assertTrue(m.movingConstraints(new Boulder(p)));
    //     assertTrue(m.movingConstraints(m));
    //     assertTrue(m.movingConstraints(new Player(new Position(1, 1, Layer.PLAYER), new Standard())));
    //     //m.spawn(new Wall(p), grid);
    // }

//    @Test
//    public void testDamageNotInvincible() {
//
//    }
    
}

