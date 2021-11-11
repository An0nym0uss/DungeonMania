package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.Test;


public class HydraTest {
    @Test
    /**
     * Test to make sure hydra spawns on hard.
     */
    public void testHydraSpawnHard() {
        DungeonManiaController controller = new DungeonManiaController();

        // start game
        DungeonResponse response = controller.newGame("hydra", "hard");

        int totalHydras = 0;

        // count any hydras in dungeon
        for (EntityResponse entity : response.getEntities()) {
            if (entity.getType().equals("hydra")) {
                totalHydras++;
            }
        }

        assertEquals(0, totalHydras);

        // do 50 ticks
        for (int i = 0; i < 50; i++) {
            response = controller.tick(null, Direction.NONE);
        }

        totalHydras = 0;

        // count any hydras in dungeon
        for (EntityResponse entity : response.getEntities()) {
            if (entity.getType().equals("hydra")) {
                totalHydras++;
            }
        }
        
        assertEquals(1, totalHydras);
    }

    @Test
    /**
     * Test to make sure hydra does not spawn on standard.
     */
    public void testHydraSpawnStandard() {
        DungeonManiaController controller = new DungeonManiaController();

        // start game
        DungeonResponse response = controller.newGame("hydra", "standard");

        int totalHydras = 0;

        // count any hydras in dungeon
        for (EntityResponse entity : response.getEntities()) {
            if (entity.getType().equals("hydra")) {
                totalHydras++;
            }
        }

        assertEquals(0, totalHydras);

        // do 50 ticks
        for (int i = 0; i < 50; i++) {
            response = controller.tick(null, Direction.NONE);
        }

        totalHydras = 0;

        // count any hydras in dungeon
        for (EntityResponse entity : response.getEntities()) {
            if (entity.getType().equals("hydra")) {
                totalHydras++;
            }
        }
        
        assertEquals(0, totalHydras);
    }

    @Test
    /**
     * Test to make sure hydra does not spawn on peaceful.
     */
    public void testHydraSpawnPeaceful() {
        DungeonManiaController controller = new DungeonManiaController();

        // start game
        DungeonResponse response = controller.newGame("hydra", "peaceful");

        int totalHydras = 0;

        // count any hydras in dungeon
        for (EntityResponse entity : response.getEntities()) {
            if (entity.getType().equals("hydra")) {
                totalHydras++;
            }
        }

        assertEquals(0, totalHydras);

        // do 50 ticks
        for (int i = 0; i < 50; i++) {
            response = controller.tick(null, Direction.NONE);
        }

        totalHydras = 0;

        // count any hydras in dungeon
        for (EntityResponse entity : response.getEntities()) {
            if (entity.getType().equals("hydra")) {
                totalHydras++;
            }
        }
        
        assertEquals(0, totalHydras);
    }
}
