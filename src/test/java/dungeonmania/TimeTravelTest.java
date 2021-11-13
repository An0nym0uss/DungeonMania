package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.Entity;
import dungeonmania.entities.player.Player;
import dungeonmania.modes.Peaceful;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class TimeTravelTest {
    @Test
    public void testInvalidRewind() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("time-travel", "peaceful");

        // collect time turner
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);

        assertThrows(IllegalArgumentException.class, () -> {
            controller.rewind(-1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            controller.rewind(-2);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            controller.rewind(-100);
        });
    }

    @Test
    public void testTimeTurner() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse first = controller.newGame("time-travel", "peaceful");

        // collect time turner
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);

        DungeonResponse response = controller.rewind(5);

        // olderSelf has created
        // items are appear on the floor again
        assertTrue(response.getEntities().size() == first.getEntities().size() + 1);
    }

    @Test
    public void testOlderSelfMovement() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse response = controller.newGame("time-travel", "peaceful");
        int playerId = getPlayerId(response);
        Position pos1 = response.getEntities().get(playerId).getPosition();
        // collect time turner
        response = controller.tick(null, Direction.RIGHT);
        playerId = getPlayerId(response);
        Position pos2 = response.getEntities().get(playerId).getPosition();
        response = controller.tick(null, Direction.RIGHT);
        playerId = getPlayerId(response);
        Position pos3 = response.getEntities().get(playerId).getPosition();
        response = controller.tick(null, Direction.DOWN);
        playerId = getPlayerId(response);
        Position pos4 = response.getEntities().get(playerId).getPosition();
        response = controller.tick(null, Direction.DOWN);
        playerId = getPlayerId(response);
        Position pos5 = response.getEntities().get(playerId).getPosition();
        response = controller.tick(null, Direction.LEFT);
        playerId = getPlayerId(response);
        Position pos6 = response.getEntities().get(playerId).getPosition();

        response = controller.rewind(5);
        int olderSelfId = getOlderSelfId(response);
        assertTrue(response.getEntities().get(olderSelfId).getPosition().equals(pos1));

        response = controller.tick(null, Direction.DOWN);
        olderSelfId = getOlderSelfId(response);
        assertTrue(response.getEntities().get(olderSelfId).getPosition().equals(pos2));

        response = controller.tick(null, Direction.DOWN);
        olderSelfId = getOlderSelfId(response);
        assertTrue(response.getEntities().get(olderSelfId).getPosition().equals(pos3));

        response = controller.tick(null, Direction.DOWN);
        olderSelfId = getOlderSelfId(response);
        assertTrue(response.getEntities().get(olderSelfId).getPosition().equals(pos4));

        response = controller.tick(null, Direction.DOWN);
        olderSelfId = getOlderSelfId(response);
        assertTrue(response.getEntities().get(olderSelfId).getPosition().equals(pos5));

        response = controller.tick(null, Direction.DOWN);
        olderSelfId = getOlderSelfId(response);
        assertTrue(response.getEntities().get(olderSelfId).getPosition().equals(pos6));

        response = controller.tick(null, Direction.DOWN);
        olderSelfId = getOlderSelfId(response);
        assertTrue(olderSelfId == -1);

    }

    @Test
    public void testOlderSelfBattle() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse first = controller.newGame("time-travel", "peaceful");

        // collect time turner
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.UP);

        controller.rewind(5);

        controller.tick(null, Direction.UP);
        // collides with olderSelf
        controller.tick(null, Direction.LEFT);

        assertFalse(controller.getGrid().getPlayer().getCurrentHealth() == controller.getGrid().getPlayer().getMaxHealth());
    }

    private int getPlayerId(DungeonResponse response) {
        for (int i = 0; i < response.getEntities().size(); i++) {
            if (response.getEntities().get(i).getType().equals("player")) {
                return i;
            }
        }
        return 0;
    }

    private int getOlderSelfId(DungeonResponse response) {
        for (int i = 0; i < response.getEntities().size(); i++) {
            if (response.getEntities().get(i).getType().equals("older_self")) {
                return i;
            }
        }
        return -1;
    }
}
