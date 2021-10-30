package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Direction;

public class FloorSwitchTest {
    @Test
    /**
     * Attempts to blow up a wall that is impeding our movement to exit.
     */
    public void testSwitchBasic() {
        DungeonManiaController controller = new DungeonManiaController();

        // Changing where the files are loaded from.
        StandardDungeonMaker.RESOURCE_PATH = "src/test/resources/dungeons/";

        // start game
        controller.newGame("switch", "peaceful");

        // collect bomb
        controller.tick(null, Direction.RIGHT);

        // attempt to walk into wall
        DungeonResponse secondTick = controller.tick(null, Direction.RIGHT);

        // wall impassable
        assertTrue(secondTick.getEntities().get(0).getType().equals("player") && secondTick.getEntities().get(0).getPosition().getX() == 1);

        String bombId = secondTick.getInventory().get(0).getId();

        // place bomb
        controller.tick(bombId, Direction.NONE);

        // attempt to walk into wall
        DungeonResponse fourthTick = controller.tick(null, Direction.RIGHT);

        // wall destroyed
        assertTrue(fourthTick.getEntities().get(0).getType().equals("player") && fourthTick.getEntities().get(0).getPosition().getX() == 2);

        // finish game
        assertEquals("", controller.tick(null, Direction.RIGHT).getGoals());
    }
}
