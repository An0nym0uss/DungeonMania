package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpiderTest {

    @Test
    /**
     * Spider attempts to walk into wall, spider succeeds.
     * Spider moves up then around a circle clockwise
     */
    public void testSpiderBasic() {
        DungeonManiaController controller = new DungeonManiaController();
        // Changing where the files are loaded from.
        StandardDungeonMaker.RESOURCE_PATH = "src/test/resources/dungeons/";

        // start game
        controller.newGame("wall", "standard");

        // attempt to kill first spider
        DungeonResponse firstTick = controller.tick(null, Direction.RIGHT);

        int playerId = 0;
        int spiderId = 0;
        int spiderCount = 0;

        // get playerId and spider
        for( int i = 0; i < firstTick.getEntities().size(); i++) {
            EntityResponse entResponse = firstTick.getEntities().get(i);
            if (entResponse.getType().equals("player")) {
                playerId = i;
            } else if (entResponse.getType().equals("spider")) {
                spiderId = i;
                spiderCount++;
            }
        }

        // only 1 spider left
        assertEquals(1, spiderCount);

        // wall impassable
        //assertTrue(firstTick.getEntities().get(playerId).getType().equals("player") && firstTick.getEntities().get(playerId).getPosition().getX() == 0);

        // spider can move onto wall
        assertTrue(firstTick.getEntities().get(spiderId).getType().equals("spider") && firstTick.getEntities().get(spiderId).getPosition().getX() == 4 && firstTick.getEntities().get(spiderId).getPosition().getY() == 0);

        // attempt spider reverse direction
        DungeonResponse secondTick = controller.tick(null, Direction.DOWN);

        // get playerId and spider
        for( int i = 0; i < secondTick.getEntities().size(); i++) {
            EntityResponse entResponse = secondTick.getEntities().get(i);
            if (entResponse.getType().equals("player")) {
                playerId = i;
            } else if (entResponse.getType().equals("spider")) {
                spiderId = i;
            }
        }

        // spider reversed direction
        assertTrue(secondTick.getEntities().get(spiderId).getType().equals("spider") && secondTick.getEntities().get(spiderId).getPosition().getX() == 3 && secondTick.getEntities().get(spiderId).getPosition().getY() == 0);

        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.UP);
        DungeonResponse seventhTick = controller.tick(null, Direction.RIGHT);

        // get playerId and spider
        for( int i = 0; i < seventhTick.getEntities().size(); i++) {
            EntityResponse entResponse = seventhTick.getEntities().get(i);
            if (entResponse.getType().equals("player")) {
                playerId = i;
            } else if (entResponse.getType().equals("spider")) {
                spiderId = i;
            }
        }

        // wall impassable
        assertTrue(seventhTick.getEntities().get(playerId).getType().equals("player") && seventhTick.getEntities().get(playerId).getPosition().getX() == 3 && seventhTick.getEntities().get(playerId).getPosition().getY() == 0);

        DungeonResponse eighthTick = controller.tick(null, Direction.DOWN);

        // get playerId and spider
        for( int i = 0; i < eighthTick.getEntities().size(); i++) {
            EntityResponse entResponse = eighthTick.getEntities().get(i);
            if (entResponse.getType().equals("player")) {
                playerId = i;
            } else if (entResponse.getType().equals("spider")) {
                spiderId = i;
            }
        }

        // spider reversed direction again
        assertTrue(eighthTick.getEntities().get(spiderId).getType().equals("spider") && eighthTick.getEntities().get(spiderId).getPosition().getX() == 5 && eighthTick.getEntities().get(spiderId).getPosition().getY() == 2);

        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);

        // finish game
        assertEquals("", controller.tick(null, Direction.LEFT).getGoals());

        // finish game
        //assertEquals("", controller.tick(null, Direction.DOWN).getGoals());

        //(new Position(1, 2)).getAdjacentPositions();
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
