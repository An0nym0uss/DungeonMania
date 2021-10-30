package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.DungeonManiaController;

public class SpawnerTest {
    @Test
    /**
     * Tests that a zombie spawns after 20 ticks on standard mode
     */
    public void testSpawnerBasic() {
        DungeonManiaController controller = new DungeonManiaController();
        int totalZombies = 0;

        // start game
        DungeonResponse response = controller.newGame("spawner", "standard");

        // do 15 ticks
        for (int i = 0; i < 15; i++) {
            response = controller.tick(null, Direction.NONE);
        }

        // count any zombies in dungeon
        for (EntityResponse entity : response.getEntities()) {
            if (entity.getType().equals("zombie_toast")) {
                totalZombies++;
            }
        }

        // no zombies after 15 on standard
        assertTrue(totalZombies == 0);

        // do 5 ticks
        for (int i = 0; i < 5; i++) {
            response = controller.tick(null, Direction.NONE);
        }

        // count any zombies in dungeon
        for (EntityResponse entity : response.getEntities()) {
            if (entity.getType().equals("zombie_toast")) {
                totalZombies++;
            }
        }

        // must be only 1 zombie spawned after 20 ticks
        assertTrue(totalZombies == 1);
    }
}
