package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.Entity;
import dungeonmania.entities.player.Player;
import dungeonmania.modes.Peaceful;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
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
    public void testOlderSelfMovement2() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse response = controller.newGame("time-travel-2", "peaceful");
        int playerId = getPlayerId(response);
        Position pos1 = response.getEntities().get(playerId).getPosition();

        response = controller.tick(null, Direction.DOWN);
        playerId = getPlayerId(response);
        Position pos2 = response.getEntities().get(playerId).getPosition();

        response = controller.tick(null, Direction.RIGHT);
        playerId = getPlayerId(response);
        Position pos3 = response.getEntities().get(playerId).getPosition();

        response = controller.tick(null, Direction.RIGHT);
        playerId = getPlayerId(response);
        Position pos4 = response.getEntities().get(playerId).getPosition();

        response = controller.tick(null, Direction.UP);
        playerId = getPlayerId(response);
        Position pos5 = response.getEntities().get(playerId).getPosition();

        response = controller.tick(null, Direction.RIGHT);
        playerId = getPlayerId(response);
        Position pos6 = response.getEntities().get(playerId).getPosition();

        response = controller.tick(null, Direction.UP);
        playerId = getPlayerId(response);
        Position pos7 = response.getEntities().get(playerId).getPosition();

        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.UP);

        int olderSelfId = getOlderSelfId(response);
        assertTrue(response.getEntities().get(olderSelfId).getPosition().equals(pos1));

        response = controller.tick(null, Direction.LEFT);
        olderSelfId = getOlderSelfId(response);
        assertTrue(response.getEntities().get(olderSelfId).getPosition().equals(pos2));

        response = controller.tick(null, Direction.UP);
        olderSelfId = getOlderSelfId(response);
        assertTrue(response.getEntities().get(olderSelfId).getPosition().equals(pos3));

        response = controller.tick(null, Direction.UP);
        olderSelfId = getOlderSelfId(response);
        assertTrue(response.getEntities().get(olderSelfId).getPosition().equals(pos4));

        response = controller.tick(null, Direction.UP);
        olderSelfId = getOlderSelfId(response);
        assertTrue(response.getEntities().get(olderSelfId).getPosition().equals(pos5));

        response = controller.tick(null, Direction.UP);
        olderSelfId = getOlderSelfId(response);
        assertTrue(response.getEntities().get(olderSelfId).getPosition().equals(pos6));

        response = controller.tick(null, Direction.UP);
        olderSelfId = getOlderSelfId(response);
        assertTrue(response.getEntities().get(olderSelfId).getPosition().equals(pos7));
    }

    @Test
    public void testSunStoneUnclockDoor() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse response = controller.newGame("time-travel-2", "peaceful");
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.UP);
        // unlock door with sunstone
        response = controller.tick(null, Direction.UP);
        int playerId = getPlayerId(response);
        Position pos1 = response.getEntities().get(playerId).getPosition();
        // can move into unlocked door
        controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.UP);
        playerId = getPlayerId(response);
        Position pos2 = response.getEntities().get(playerId).getPosition();

        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.LEFT);
        for (int i = 0; i < 12; i++) {
            controller.tick(null, Direction.UP);
        }

        // older self unlock door with sun stone
        response = controller.tick(null, Direction.UP);
        int olderSelfId = getOlderSelfId(response);
        assertTrue(response.getEntities().get(olderSelfId).getPosition().equals(pos1));

        controller.tick(null, Direction.UP);
        response = controller.tick(null, Direction.UP);
        olderSelfId = getOlderSelfId(response);
        assertTrue(response.getEntities().get(olderSelfId).getPosition().equals(pos2));
    }

    @Test
    public void testOlderSelfBattle() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("time-travel", "standard");

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

    @Test
    public void testOlderSelfBattleTillDeath() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("time-travel", "standard");
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);

        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);

        for (int i = 0; i < 30; i++) {
            controller.tick(null, Direction.RIGHT);
        }

        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);

        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);

        DungeonResponse response = null;
        // keep colliding with older self
        for (int i = 0; i < 10; i++) {
            response = controller.tick(null, Direction.RIGHT);
        }


        for (EntityResponse er : response.getEntities()) {
            assertFalse(er.getType().equalsIgnoreCase("older_self"));
        }

    }

    @Test
    public void testBattleWithSunStone() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("time-travel-2", "standard");
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);

        // collect sun stone
        for (int i = 0; i < 15; i++) {
            controller.tick(null, Direction.RIGHT);
        }

        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);

        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);

        
        for (int i = 0; i < 5; i++) {
            controller.tick(null, Direction.RIGHT);
            assertTrue(controller.getGrid().getPlayer().getCurrentHealth() == controller.getGrid().getPlayer().getMaxHealth());
        }
    }

    @Test
    public void testOpenDoorPushBoulder() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse response = controller.newGame("time-travel-2", "peaceful");
        int playerId = getPlayerId(response);
        Position pos1 = response.getEntities().get(playerId).getPosition();
        // collect key
        response = controller.tick(null, Direction.RIGHT);
        playerId = getPlayerId(response);
        // open locked door
        Position pos2 = response.getEntities().get(playerId).getPosition();
        response = controller.tick(null, Direction.RIGHT);
        playerId = getPlayerId(response);
        // push boulder
        Position pos3 = response.getEntities().get(playerId).getPosition();
        response = controller.tick(null, Direction.RIGHT);
        playerId = getPlayerId(response);
        Position pos4 = response.getEntities().get(playerId).getPosition();
        response = controller.tick(null, Direction.DOWN);
        playerId = getPlayerId(response);
        Position pos5 = response.getEntities().get(playerId).getPosition();
        response = controller.tick(null, Direction.RIGHT);
        playerId = getPlayerId(response);
        Position pos6 = response.getEntities().get(playerId).getPosition();
        response = controller.tick(null, Direction.RIGHT);
        playerId = getPlayerId(response);
        Position pos7 = response.getEntities().get(playerId).getPosition();
        response = controller.tick(null, Direction.UP);
        playerId = getPlayerId(response);
        // push back boulder
        Position pos8 = response.getEntities().get(playerId).getPosition();
        response = controller.tick(null, Direction.LEFT);
        playerId = getPlayerId(response);
        Position pos9 = response.getEntities().get(playerId).getPosition();
        response = controller.tick(null, Direction.RIGHT);
        playerId = getPlayerId(response);
        Position pos10 = response.getEntities().get(playerId).getPosition();
        response = controller.tick(null, Direction.RIGHT);
        playerId = getPlayerId(response);
        Position pos11 = response.getEntities().get(playerId).getPosition();

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
        assertTrue(response.getEntities().get(olderSelfId).getPosition().equals(pos7));

        response = controller.tick(null, Direction.DOWN);
        olderSelfId = getOlderSelfId(response);
        assertTrue(response.getEntities().get(olderSelfId).getPosition().equals(pos8));

        response = controller.tick(null, Direction.DOWN);
        olderSelfId = getOlderSelfId(response);
        assertTrue(response.getEntities().get(olderSelfId).getPosition().equals(pos9));

        response = controller.tick(null, Direction.DOWN);
        olderSelfId = getOlderSelfId(response);
        assertTrue(response.getEntities().get(olderSelfId).getPosition().equals(pos10));

        response = controller.tick(null, Direction.DOWN);
        olderSelfId = getOlderSelfId(response);
        assertTrue(response.getEntities().get(olderSelfId).getPosition().equals(pos11));

        // older self disappears
        response = controller.tick(null, Direction.DOWN);
        olderSelfId = getOlderSelfId(response);
        assertTrue(olderSelfId == -1);
    }

    @Test
    public void testPortal() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("time-travel-2", "peaceful");
        int playerId = getPlayerId(response);
        Position pos1 = response.getEntities().get(playerId).getPosition();

        response = controller.tick(null, Direction.DOWN);
        playerId = getPlayerId(response);
        Position pos2 = response.getEntities().get(playerId).getPosition();

        response = controller.tick(null, Direction.DOWN);
        playerId = getPlayerId(response);
        Position pos3 = response.getEntities().get(playerId).getPosition();

        response = controller.tick(null, Direction.DOWN);
        playerId = getPlayerId(response);
        Position pos4 = response.getEntities().get(playerId).getPosition();

        response = controller.tick(null, Direction.UP);
        playerId = getPlayerId(response);
        Position pos5 = response.getEntities().get(playerId).getPosition();

        response = controller.tick(null, Direction.UP);
        playerId = getPlayerId(response);
        Position pos6 = response.getEntities().get(playerId).getPosition();

        // goes in time travel portal
        response = controller.tick(null, Direction.UP);
        playerId = getPlayerId(response);
        Position pos7 = response.getEntities().get(playerId).getPosition();

        int olderSelfId = getOlderSelfId(response);
        assertTrue(response.getEntities().get(olderSelfId).getPosition().equals(pos1));

        response = controller.tick(null, Direction.LEFT);
        olderSelfId = getOlderSelfId(response);
        assertTrue(response.getEntities().get(olderSelfId).getPosition().equals(pos2));

        response = controller.tick(null, Direction.UP);
        olderSelfId = getOlderSelfId(response);
        assertTrue(response.getEntities().get(olderSelfId).getPosition().equals(pos3));

        response = controller.tick(null, Direction.UP);
        olderSelfId = getOlderSelfId(response);
        assertTrue(response.getEntities().get(olderSelfId).getPosition().equals(pos4));

        response = controller.tick(null, Direction.UP);
        olderSelfId = getOlderSelfId(response);
        assertTrue(response.getEntities().get(olderSelfId).getPosition().equals(pos5));

        response = controller.tick(null, Direction.UP);
        olderSelfId = getOlderSelfId(response);
        assertTrue(response.getEntities().get(olderSelfId).getPosition().equals(pos6));

        response = controller.tick(null, Direction.UP);
        olderSelfId = getOlderSelfId(response);
        assertTrue(response.getEntities().get(olderSelfId).getPosition().equals(pos7));
    }

    @Test
    public void testOlderSelfInventory() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("time-travel-2", "peaceful");

        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);

        controller.build("bow");
        controller.build("shield");
        controller.build("sceptre");
        controller.build("midnight_armour");
        
        controller.tick(null, Direction.RIGHT);
        for (int i = 0; i < 6; i++) {   
            controller.tick(null, Direction.UP);
        }

        for (int i = 0; i < 30; i++) {
            controller.tick(null, Direction.RIGHT);
        }

        DungeonResponse response = controller.tick(null, Direction.UP);

        assertTrue(response.getInventory().size() >= 5);

        assertTrue(controller.getGrid().getOlderSelves().get(0).getInventory().getItems().size() >= 5);
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
