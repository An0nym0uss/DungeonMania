package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.DungeonManiaController;

public class BombTest {
    @Test
    public void testBombBasic() {
        DungeonManiaController controller = new DungeonManiaController();
        StandardDungeonMaker.RESOURCE_PATH = "src/test/resources/dungeons/";

        controller.newGame("bomb-1", "standard");

        // collect bomb on the right
        DungeonResponse response = controller.tick(null, Direction.RIGHT);
        assertEquals(1, response.getInventory().size());

        // push boulder on the right
        controller.tick(null, Direction.RIGHT);

        // place bomb, bomb explodes
        String bombId = response.getInventory().get(0).getId();
        response = controller.tick(bombId, null);
        // no item in entity
        assertEquals(0, response.getInventory().size());
        // entities in blast radius get destroyed
        assertEquals(15, response.getEntities().size());
    }

    @Test
    public void TestExit() {
        DungeonManiaController controller = new DungeonManiaController();
        StandardDungeonMaker.RESOURCE_PATH = "src/test/resources/dungeons/";

        controller.newGame("bomb-1", "standard");

        // collect bomb
        DungeonResponse response = controller.tick(null, Direction.RIGHT);
        // go to exit
        response = controller.tick(null, Direction.DOWN);
        // wins the game
        assertEquals("", response.getGoals());
    }

    @Test
    public void TestBombBasic2() {
        DungeonManiaController controller = new DungeonManiaController();
        StandardDungeonMaker.RESOURCE_PATH = "src/test/resources/dungeons/";

        controller.newGame("bomb-1", "standard");

        // collect bomb
        DungeonResponse response = controller.tick(null, Direction.RIGHT);
        // place bomb
        String bombId = response.getInventory().get(0).getId();
        response = controller.tick(bombId, null);

        // player cannot go to placed bomb cell
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.DOWN);

        assertNotEquals("", response.getGoals());
    }
}
