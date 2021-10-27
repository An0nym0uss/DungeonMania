package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;

public class FloorSwitchTest {
    @Test
    /**
     * Attempts to blow up a wall that is impeding our movement to exit.
     */
    public void testSwitchBasic() {
        DungeonManiaController controller = new DungeonManiaController();

        // start game
        controller.newGame("switch", "peaceful");

        // collect bomb
        controller.tick(null, Direction.RIGHT);

        // attempt to walk into wall
        DungeonResponse secondTick = controller.tick(null, Direction.RIGHT);

        // wall impassable
        assertTrue(secondTick.getEntities().get(0).getType().equals("player") && secondTick.getEntities().get(0).getPosition().getX() == 1);

        // place bomb
        controller.tick("bomb", Direction.NONE);

        // attempt to walk into wall
        DungeonResponse fourthTick = controller.tick(null, Direction.RIGHT);

        // wall destroyed
        assertTrue(fourthTick.getEntities().get(0).getType().equals("player") && fourthTick.getEntities().get(0).getPosition().getX() == 2);

        // finish game
        controller.tick(null, Direction.RIGHT);
    }
}
