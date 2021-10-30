package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.DungeonManiaController;

public class PortalTest {
    @Test
    /**
     * Checking a player can teleport using portals.
     */
    public void testPortalBasic() {
        DungeonManiaController controller = new DungeonManiaController();

        // start game
        controller.newGame("portals", "peaceful");

        // attempt to walk into portal
        DungeonResponse firstTick = controller.tick(null, Direction.RIGHT);

        // player teleports to corresponding portal
        assertTrue(firstTick.getEntities().get(0).getType().equals("player") && firstTick.getEntities().get(0).getPosition().getX() == 4 && firstTick.getEntities().get(0).getPosition().getY() == 0);
    }
}
