package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;

public class BoulderTest {
    @Test
    public void testBoulderBasic() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("boulders-2", "standard");

        DungeonResponse response = controller.tick(null, Direction.RIGHT);

        int playerId = 0;
        // get playerId
        for( int i = 0; i < response.getEntities().size(); i++) {
            EntityResponse entResponse = response.getEntities().get(i);
            if (entResponse.getType().equals("player")) {
                playerId = i;
            }
        }

        // boulder behind a boulder, player cannot push
        assertTrue(response.getEntities().get(playerId).getType().equals("player") && 
                   response.getEntities().get(playerId).getPosition().getX() == 1 &&
                   response.getEntities().get(playerId).getPosition().getY() == 1
        );

        controller.tick(null, Direction.DOWN);

        response = controller.tick(null, Direction.RIGHT);
        // get playerId
        for( int i = 0; i < response.getEntities().size(); i++) {
            EntityResponse entResponse = response.getEntities().get(i);
            if (entResponse.getType().equals("player")) {
                playerId = i;
            }
        }
        int prevX = response.getEntities().get(playerId).getPosition().getX();
        int prevY = response.getEntities().get(playerId).getPosition().getY();

        response = controller.tick(null, Direction.UP);
        // get playerId
        for( int i = 0; i < response.getEntities().size(); i++) {
            EntityResponse entResponse = response.getEntities().get(i);
            if (entResponse.getType().equals("player")) {
                playerId = i;
            }
        }
        // wall behind a boulder, player cannot push
        assertTrue(response.getEntities().get(playerId).getType().equals("player"));
        assertEquals(prevX, response.getEntities().get(playerId).getPosition().getX());
        assertEquals(prevY, response.getEntities().get(playerId).getPosition().getY());

        response = controller.tick(null, Direction.RIGHT);
        // get playerId
        for( int i = 0; i < response.getEntities().size(); i++) {
            EntityResponse entResponse = response.getEntities().get(i);
            if (entResponse.getType().equals("player")) {
                playerId = i;
            }
        }
        prevX = response.getEntities().get(playerId).getPosition().getX();
        prevY = response.getEntities().get(playerId).getPosition().getY();

        response = controller.tick(null, Direction.UP);
        // get playerId
        for( int i = 0; i < response.getEntities().size(); i++) {
            EntityResponse entResponse = response.getEntities().get(i);
            if (entResponse.getType().equals("player")) {
                playerId = i;
            }
        }
        // collectable behind a boulder, player CAN push
        assertTrue(response.getEntities().get(playerId).getType().equals("player"));
        assertEquals(prevX, response.getEntities().get(playerId).getPosition().getX());
        assertEquals(prevY - 1, response.getEntities().get(playerId).getPosition().getY());


    }

    @Test
    public void testBoulderBasic2() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("boulders-3", "standard");

        // collect bomb
        DungeonResponse response = controller.tick(null, Direction.RIGHT);
        String bombId = response.getInventory().get(0).getId();
        //place bomb
        controller.tick(bombId, null);

        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);

        response = controller.tick(null, Direction.RIGHT);
        int playerId = 0;
        // get playerId
        for( int i = 0; i < response.getEntities().size(); i++) {
            EntityResponse entResponse = response.getEntities().get(i);
            if (entResponse.getType().equals("player")) {
                playerId = i;
            }
        }
        int prevX = response.getEntities().get(playerId).getPosition().getX();
        int prevY = response.getEntities().get(playerId).getPosition().getY();

        response = controller.tick(null, Direction.UP);
        // get playerId
        for( int i = 0; i < response.getEntities().size(); i++) {
            EntityResponse entResponse = response.getEntities().get(i);
            if (entResponse.getType().equals("player")) {
                playerId = i;
            }
        }
        // placed bomb behind a boulder, player cannot push
        assertTrue(response.getEntities().get(playerId).getType().equals("player"));
        assertEquals(prevX, response.getEntities().get(playerId).getPosition().getX());
        assertEquals(prevY, response.getEntities().get(playerId).getPosition().getY());

        controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.UP);
        // get playerId
        for( int i = 0; i < response.getEntities().size(); i++) {
            EntityResponse entResponse = response.getEntities().get(i);
            if (entResponse.getType().equals("player")) {
                playerId = i;
            }
        }
        prevX = response.getEntities().get(playerId).getPosition().getX();
        prevY = response.getEntities().get(playerId).getPosition().getY();

        response = controller.tick(null, Direction.RIGHT);
        for( int i = 0; i < response.getEntities().size(); i++) {
            EntityResponse entResponse = response.getEntities().get(i);
            if (entResponse.getType().equals("player")) {
                playerId = i;
            }
        }
        // locked door behind a boulder, player cannot push
        assertTrue(response.getEntities().get(playerId).getType().equals("player"));
        assertEquals(prevX, response.getEntities().get(playerId).getPosition().getX());
        assertEquals(prevY, response.getEntities().get(playerId).getPosition().getY());

        prevX = response.getEntities().get(playerId).getPosition().getX();
        prevY = response.getEntities().get(playerId).getPosition().getY();

        // unlock door
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.UP);

        boolean isOpen = false;
        for (int i = 0; i < response.getEntities().size(); i++) {
            EntityResponse entResponse = response.getEntities().get(i);
            if (entResponse.getType().equals("door_unlocked")) {
                isOpen = true;
                break;
            }
        }
        assertTrue(isOpen);

        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.UP);

        // push boulder into unlocked door
        response = controller.tick(null, Direction.RIGHT);
        for( int i = 0; i < response.getEntities().size(); i++) {
            EntityResponse entResponse = response.getEntities().get(i);
            if (entResponse.getType().equals("player")) {
                playerId = i;
            }
        }
        // unlocked door behind a boulder, player CAN push
        assertTrue(response.getEntities().get(playerId).getType().equals("player"));
        assertEquals(prevX + 1, response.getEntities().get(playerId).getPosition().getX());
        assertEquals(prevY, response.getEntities().get(playerId).getPosition().getY());


    }
}
