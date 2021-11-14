package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.Test;

public class SpiderTest {
    @Test
    /**
     * Tests how the spiders spawn on a very small grid
     */
    public void testSpiderSmallGrid() {
        DungeonManiaController controller = new DungeonManiaController();
        // Changing where the files are loaded from.
        StandardDungeonMaker.RESOURCE_PATH = "src/test/resources/dungeons/";

        // start game
        DungeonResponse response = controller.newGame("spider-small", "standard");

        // do 29 ticks
        for (int i = 0; i < 29; i++) {
            response = controller.tick(null, Direction.NONE);
        }

        int totalSpiders = 0;

        // count any spiders in dungeon
        for (EntityResponse entity : response.getEntities()) {
            if (entity.getType().equals("spider")) {
                totalSpiders++;
            }
        }

        // no spiders should be on the grid yet
        assertTrue(totalSpiders == 0);

        // do 61 ticks
        for (int i = 0; i < 61; i++) {
            response = controller.tick(null, Direction.NONE);
        }

        totalSpiders = 0;

        // count any spiders in dungeon
        for (EntityResponse entity : response.getEntities()) {
            if (entity.getType().equals("spider")) {
                totalSpiders++;
            }
        }

        // 3 chances for spider to spawn, should be at least more than 1
        assertTrue(totalSpiders >= 1);
    }

    @Test
    public void testSpiderBoundary() {
        DungeonManiaController controller = new DungeonManiaController();
        // Changing where the files are loaded from.
        StandardDungeonMaker.RESOURCE_PATH = "src/test/resources/dungeons/";

        // start game
        DungeonResponse response = controller.newGame("spider-2", "standard");

        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        controller.tick(null, Direction.NONE);
        response = controller.tick(null, Direction.NONE);
    }
}
