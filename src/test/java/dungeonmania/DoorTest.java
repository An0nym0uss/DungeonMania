package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
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

        int playerId = 0;

        // get playerId
        for( int i = 0; i < firstTick.getEntities().size(); i++) {
            EntityResponse entResponse = firstTick.getEntities().get(i);
            if (entResponse.getType().equals("player")) {
                playerId = i;
            }
        }

        // door locked
        assertTrue(firstTick.getEntities().get(playerId).getType().equals("player") && firstTick.getEntities().get(playerId).getPosition().getX() == 0 && firstTick.getEntities().get(playerId).getPosition().getY() == 0);

        // get key
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.UP);

        // attempt to unlock door
        DungeonResponse fouthTick = controller.tick(null, Direction.RIGHT);

        // get playerId
        for( int i = 0; i < fouthTick.getEntities().size(); i++) {
            EntityResponse entResponse = fouthTick.getEntities().get(i);
            if (entResponse.getType().equals("player")) {
                playerId = i;
            }
        }

        // door unlocked
        assertTrue(fouthTick.getEntities().get(playerId).getType().equals("player") && fouthTick.getEntities().get(playerId).getPosition().getX() == 1 && fouthTick.getEntities().get(playerId).getPosition().getY() == 0);

        // make sure can still walk into an unlocked door
        controller.tick(null, Direction.DOWN);
        DungeonResponse fifthTick = controller.tick(null, Direction.UP);

        // get playerId
        for( int i = 0; i < fifthTick.getEntities().size(); i++) {
            EntityResponse entResponse = fifthTick.getEntities().get(i);
            if (entResponse.getType().equals("player")) {
                playerId = i;
            }
        }

        // in open door
        assertTrue(fifthTick.getEntities().get(playerId).getType().equals("player") && fifthTick.getEntities().get(playerId).getPosition().getX() == 1 && fifthTick.getEntities().get(playerId).getPosition().getY() == 0);

        // finish game
        assertEquals("", controller.tick(null, Direction.RIGHT).getGoals());
    }

    public static void main(String[] args) {
        DungeonManiaController controller = new DungeonManiaController();

        // start game
        controller.newGame("door", "peaceful");
    }
}
