package dungeonmania;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.collectable.Sword;
import dungeonmania.entities.enemy.Enemy;
import dungeonmania.entities.enemy.Mercenary;
import dungeonmania.entities.statics.ZombieToastSpawner;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.entities.collectable.*;
import dungeonmania.entities.Entity;

import dungeonmania.constants.Layer;
import dungeonmania.modes.Standard;

public class InteractTest {
    @Test
    public void testBribe() {

        Player player = new Player(new Position(5, 5, Layer.PLAYER), new Standard());
        CollectableEntity treasure = new Treasure(new Position(6, 5, Layer.COLLECTABLE));
        Enemy enemy = new Mercenary(new Position(7, 5), 1, 1, 1);
        Grid grid = new Grid(10, 10, new Entity[10][10][Layer.LAYER_SIZE], null);
        grid.attach(player);
        grid.attach(enemy);
        grid.attach(treasure);

        assertTrue(player.getInventory().getItems().isEmpty());
        player.move(grid, Direction.RIGHT);
        assertTrue(player.getInventory().getItems().size() == 1);
        player.interact(enemy.getId(), grid);
        assertTrue(player.getInventory().getItems().isEmpty());
    }

    @Test
    public void testBribe2() {

        DungeonManiaController controller = new DungeonManiaController();
        StandardDungeonMaker.RESOURCE_PATH = "src/test/resources/dungeons/";
        controller.newGame("bribe", "standard");

        DungeonResponse hasTreasure = controller.tick(null, Direction.DOWN);
        assertTrue(hasTreasure.getEntities().size() == 2);
        assertTrue(hasTreasure.getEntities().get(0).getType() == "player");
        assertTrue(hasTreasure.getEntities().get(0).getPosition().getX() == 5);
        assertTrue(hasTreasure.getEntities().get(0).getPosition().getY() == 6);
        assertTrue(hasTreasure.getEntities().get(1).getType() == "mercenary");
        assertTrue(hasTreasure.getEntities().get(1).getPosition().getX() == 5);
        assertTrue(hasTreasure.getEntities().get(1).getPosition().getY() == 7);
        assertTrue(hasTreasure.getInventory().size() == 1);
        DungeonResponse nextToMercenary = controller.interact(hasTreasure.getEntities().get(1).getId());
        assertTrue(nextToMercenary.getInventory().size() == 0);

    }

    @Test
    public void testSpawnerNoInteraction() {

        DungeonManiaController controller = new DungeonManiaController();
        StandardDungeonMaker.RESOURCE_PATH = "src/test/resources/dungeons/";
        controller.newGame("interaction", "standard");

        DungeonResponse noSword = controller.tick(null, Direction.RIGHT);
        assertTrue(noSword.getEntities().size() == 3);
        DungeonResponse hasSword = controller.tick(null, Direction.RIGHT);
        assertTrue(hasSword.getEntities().size() == 2);
        DungeonResponse onSpawner = controller.tick(null, Direction.DOWN);
        assertTrue(onSpawner.getEntities().size() == 2);
    }

    @Test
    public void testSpawnerInteraction() {

        DungeonManiaController controller = new DungeonManiaController();
        StandardDungeonMaker.RESOURCE_PATH = "src/test/resources/dungeons/";
        controller.newGame("interaction", "standard");

        DungeonResponse noSword = controller.tick(null, Direction.RIGHT);
        assertTrue(noSword.getEntities().size() == 3);
        DungeonResponse hasSword = controller.tick(null, Direction.RIGHT);
        assertTrue(hasSword.getEntities().size() == 2);
        DungeonResponse nextToSpawner = controller.interact(hasSword.getEntities().get(1).getId());
        assertTrue(nextToSpawner.getEntities().size() == 1);
    }

    @Test
    public void testSpawnerNoSword() {

        DungeonManiaController controller = new DungeonManiaController();
        StandardDungeonMaker.RESOURCE_PATH = "src/test/resources/dungeons/";
        controller.newGame("interaction", "standard");

        controller.tick(null, Direction.DOWN);
        DungeonResponse noSword = controller.tick(null, Direction.RIGHT);
        assertTrue(noSword.getEntities().size() == 3);
        assertThrows(InvalidActionException.class, () -> controller.interact(noSword.getEntities().get(2).getId()));
    }

    @Test
    public void testSpawnerWrongId() {

        DungeonManiaController controller = new DungeonManiaController();
        StandardDungeonMaker.RESOURCE_PATH = "src/test/resources/dungeons/";
        controller.newGame("interaction", "standard");

        DungeonResponse noSword = controller.tick(null, Direction.RIGHT);
        assertTrue(noSword.getEntities().size() == 3);
        DungeonResponse hasSword = controller.tick(null, Direction.RIGHT);
        assertTrue(hasSword.getEntities().size() == 2);
        assertThrows(IllegalArgumentException.class, () -> controller.interact(noSword.getEntities().get(0).getId()));
    }

    @Test
    public void testSpawnerNotAdjancent() {

        DungeonManiaController controller = new DungeonManiaController();
        StandardDungeonMaker.RESOURCE_PATH = "src/test/resources/dungeons/";
        controller.newGame("interaction", "standard");

        // spawner is adjancent to the player but not cardinally
        DungeonResponse noSword = controller.tick(null, Direction.RIGHT);
        assertTrue(noSword.getEntities().size() == 3);
        assertThrows(InvalidActionException.class, () -> controller.interact(noSword.getEntities().get(2).getId()));
    }
}
