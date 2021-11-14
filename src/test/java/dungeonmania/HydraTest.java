package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dungeonmania.entities.enemy.Hydra;
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

    @Test
    /**
     * Test to make sure that the hydra will heal itself instead of taking damage 50% of the time.
     * Easier to not use black box testing for this.
     */
    public void testHydraHealsSelf() {
        int iterations = 1_000_000;
        int trueCounter = 0;
        for (int i = 0; i < iterations; i++) {
            Hydra hydra = new Hydra();
            int oldHealth = hydra.getHealth();
            hydra.receiveDamage(10);
            int newHealth = hydra.getHealth();
            if (newHealth >= oldHealth) { //equal to in case we later change it to have no over healing
                trueCounter++;
            }
        }
        assertTrue((double)trueCounter / iterations >= 0.49 && (double)trueCounter / iterations <= 0.51);
    }

    @Test
    /**
     * Check hydra moves randomly
     */
    public void testHydraRandomMove() {
        DungeonManiaController controller = new DungeonManiaController();

        // start game
        DungeonResponse response = controller.newGame("hydra", "hard");

        // do 50 ticks
        for (int i = 0; i < 50; i++) {
            response = controller.tick(null, Direction.NONE);
        }

        // with random movement, if on a small grid the only option will be to move into player
        response = controller.tick(null, Direction.NONE);

        // hydra should be on player
        for (EntityResponse entity : response.getEntities()) {
            if (entity.getType().equals("hydra")) {
                assertEquals(0, entity.getPosition().getX());
                assertEquals(0, entity.getPosition().getY());
            }
        }

        // again there is only one place for the hydra to move to
        response = controller.tick(null, Direction.NONE);

        // hydra should be back to its spawn position
        for (EntityResponse entity : response.getEntities()) {
            if (entity.getType().equals("hydra")) {
                assertEquals(0, entity.getPosition().getX());
                assertEquals(1, entity.getPosition().getY());
            }
        }
    }
}
