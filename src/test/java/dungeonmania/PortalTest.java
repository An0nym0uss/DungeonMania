package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
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

        int playerId = 0;

        // get playerId
        for( int i = 0; i < firstTick.getEntities().size(); i++) {
            EntityResponse entResponse = firstTick.getEntities().get(i);
            if (entResponse.getType().equals("player")) {
                playerId = i;
            }
        }

        // player teleports to corresponding portal
        assertTrue(firstTick.getEntities().get(playerId).getType().equals("player") && firstTick.getEntities().get(playerId).getPosition().getX() == 4 && firstTick.getEntities().get(playerId).getPosition().getY() == 0);
    }

    @Test
    public void testPortalNoColourSpecified() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> {
            controller.newGame("portals-2", "standard");
        });
    }

    @Test
    public void testMultipleColourPortals() {
        DungeonManiaController controller = new DungeonManiaController();

        // start game
        controller.newGame("portals-3", "peaceful");

        // attempt to walk into a red portal
        DungeonResponse response = controller.tick(null, Direction.RIGHT);

        int playerId = 0;

        // get playerId
        for( int i = 0; i < response.getEntities().size(); i++) {
            EntityResponse entResponse = response.getEntities().get(i);
            if (entResponse.getType().equals("player")) {
                playerId = i;
            }
        }
        assertTrue(response.getEntities().get(playerId).getType().equals("player") && 
                   response.getEntities().get(playerId).getPosition().getX() == 5 && 
                   response.getEntities().get(playerId).getPosition().getY() == 1
        );
        
        controller.tick(null, Direction.DOWN);
        // player enters a blue portal
        response = controller.tick(null, Direction.DOWN);

        // get playerId
        for( int i = 0; i < response.getEntities().size(); i++) {
            EntityResponse entResponse = response.getEntities().get(i);
            if (entResponse.getType().equals("player")) {
                playerId = i;
            }
        }

        assertTrue(response.getEntities().get(playerId).getType().equals("player") && 
                   response.getEntities().get(playerId).getPosition().getX() == 2 && 
                   response.getEntities().get(playerId).getPosition().getY() == 3
        );
    }
}
