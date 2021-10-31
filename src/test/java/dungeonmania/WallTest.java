package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
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

        int playerId = 0;
        int spiderId = 0;

        // get playerId and spider
        for( int i = 0; i < firstTick.getEntities().size(); i++) {
            EntityResponse entResponse = firstTick.getEntities().get(i);
            if (entResponse.getType().equals("player")) {
                playerId = i;
            } else if (entResponse.getType().equals("spider")) {
                spiderId = i;
            }
        }

        // wall impassable
        assertTrue(firstTick.getEntities().get(playerId).getType().equals("player") && firstTick.getEntities().get(playerId).getPosition().getX() == 0);

        // spider can move onto wall
        assertTrue(firstTick.getEntities().get(spiderId).getType().equals("spider") && firstTick.getEntities().get(spiderId).getPosition().getX() == 1 && firstTick.getEntities().get(spiderId).getPosition().getY() == 0);

        // finish game
        controller.tick(null, Direction.DOWN);

        (new Position(1, 2)).getAdjacentPositions();
    }

    @Test
    public void testPushBoulderIntoWall() {
        // Test end
        DungeonManiaController controller = new DungeonManiaController();
        StandardDungeonMaker.RESOURCE_PATH = "src/test/resources/dungeons/";
        controller.newGame("boulders", "peaceful");

        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
    }
}
