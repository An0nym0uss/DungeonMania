package dungeonmania;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.collectable.Sword;
import dungeonmania.entities.enemy.Mercenary;
import dungeonmania.entities.statics.ZombieToastSpawner;
import dungeonmania.exceptions.InvalidActionException;

public class InteractTest {
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
