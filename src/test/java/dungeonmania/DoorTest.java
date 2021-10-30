package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.DungeonManiaController;

public class DoorTest {
    @Test
    /**
     * Attempts to walk into a locked door, then gets key and unlocks it.
     */
    public void testDoorBasic() {
        DungeonManiaController controller = new DungeonManiaController();

        // start game
        controller.newGame("door", "peaceful");

        // attempt to walk into door
        DungeonResponse firstTick = controller.tick(null, Direction.RIGHT);

        // door locked
        assertTrue(firstTick.getEntities().get(0).getType().equals("player") && firstTick.getEntities().get(0).getPosition().getX() == 0);

        // get key
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.DOWN);

        // attempt to unlock door
        DungeonResponse fouthTick = controller.tick(null, Direction.RIGHT);

        // door unlocked
        assertTrue(fouthTick.getEntities().get(0).getType().equals("player") && fouthTick.getEntities().get(0).getPosition().getX() == 1);

        // finish game
        controller.tick(null, Direction.RIGHT);
    }
}
