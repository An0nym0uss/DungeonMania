package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.DungeonManiaController;

public class WallTest {
    @Test
    /**
     * Player attempts to walk into wall and fails, spider succeeds.
     */
    public void testWallBasic() {
        DungeonManiaController controller = new DungeonManiaController();

        // start game
        controller.newGame("wall", "peaceful");

        // attempt to walk into wall
        DungeonResponse firstTick = controller.tick(null, Direction.RIGHT);

        // wall impassable
        assertTrue(firstTick.getEntities().get(0).getType().equals("player") && firstTick.getEntities().get(0).getPosition().getX() == 0);

        // spider can move onto wall
        assertTrue(firstTick.getEntities().get(2).getType().equals("spider") && firstTick.getEntities().get(2).getPosition().getX() == 1 && firstTick.getEntities().get(2).getPosition().getY() == 0);

        // finish game
        controller.tick(null, Direction.DOWN);
    }
}
