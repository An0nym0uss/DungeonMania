package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.Test;

public class SwampTileTest {
    /**
     * Tests to make sure the swamp tile slows down enemies.
     */
    @Test
    public void testSwampTileEnemies() {
        DungeonManiaController controller = new DungeonManiaController();
        // Changing where the files are loaded from.
        StandardDungeonMaker.RESOURCE_PATH = "src/test/resources/dungeons/";

        // start game
        DungeonResponse response = controller.newGame("swamp_tile", "standard");
        
        // wait a tick to see if the enemies have moved
        response = controller.tick(null, Direction.NONE);

        // check no enemies have moved
        for (EntityResponse entity : response.getEntities()) {
            if (entity.getType().equals("spider")) {
                assertEquals(3, entity.getPosition().getX());
                assertEquals(1, entity.getPosition().getY());
            }
            else if (entity.getType().equals("zombie_toast")) {
                assertEquals(6, entity.getPosition().getX());
                assertEquals(1, entity.getPosition().getY());
            }
            else if (entity.getType().equals("mercenary")) {
                assertEquals(9, entity.getPosition().getX());
                assertEquals(1, entity.getPosition().getY());
            }
        }

        // wait another tick to see if enemies can move
        response = controller.tick(null, Direction.NONE);

        // only spider should be allowed to move
        for (EntityResponse entity : response.getEntities()) {
            if (entity.getType().equals("spider")) {
                assertFalse(entity.getPosition().getX() == 3 && entity.getPosition().getY() == 1);
            }
            else if (entity.getType().equals("zombie_toast")) {
                assertEquals(6, entity.getPosition().getX());
                assertEquals(1, entity.getPosition().getY());
            }
            else if (entity.getType().equals("mercenary")) {
                assertEquals(9, entity.getPosition().getX());
                assertEquals(1, entity.getPosition().getY());
            }
        }
    }

    /**
     * Makes sure the player is not slowed down by the swamp tile.
     */
    @Test
    public void testSwampTilePlayer() {
        DungeonManiaController controller = new DungeonManiaController();
        // Changing where the files are loaded from.
        StandardDungeonMaker.RESOURCE_PATH = "src/test/resources/dungeons/";

        // start game
        DungeonResponse response = controller.newGame("swamp_tile", "standard");

        // attempt to move off the swamp tile
        response = controller.tick(null, Direction.DOWN);

        // check we have move one cell down
        for (EntityResponse entity : response.getEntities()) {
            if (entity.getType().equals("player")) {
                assertEquals(0, entity.getPosition().getX());
                assertEquals(2, entity.getPosition().getY());
            }
        }
    }

    /**
     * Makes sure a high movement factor 
     */
    @Test
    public void testSwampTileMovementFactors() {
        DungeonManiaController controller = new DungeonManiaController();
        // Changing where the files are loaded from.
        StandardDungeonMaker.RESOURCE_PATH = "src/test/resources/dungeons/";

        // start game
        DungeonResponse response = controller.newGame("swamp_tile", "standard");
        
        // wait a tick to see if the enemies have moved
        response = controller.tick(null, Direction.NONE);

        // check no enemies have moved
        for (EntityResponse entity : response.getEntities()) {
            if (entity.getType().equals("spider")) {
                assertEquals(3, entity.getPosition().getX());
                assertEquals(1, entity.getPosition().getY());
            }
            else if (entity.getType().equals("zombie_toast")) {
                assertEquals(6, entity.getPosition().getX());
                assertEquals(1, entity.getPosition().getY());
            }
            else if (entity.getType().equals("mercenary")) {
                assertEquals(9, entity.getPosition().getX());
                assertEquals(1, entity.getPosition().getY());
            }
        }

        // wait another tick to see if enemies can move
        response = controller.tick(null, Direction.NONE);

        // only spider should be allowed to move
        for (EntityResponse entity : response.getEntities()) {
            if (entity.getType().equals("spider")) {
                assertFalse(entity.getPosition().getX() == 3 && entity.getPosition().getY() == 1);
            }
            else if (entity.getType().equals("zombie_toast")) {
                assertEquals(6, entity.getPosition().getX());
                assertEquals(1, entity.getPosition().getY());
            }
            else if (entity.getType().equals("mercenary")) {
                assertEquals(9, entity.getPosition().getX());
                assertEquals(1, entity.getPosition().getY());
            }
        }

        // wait another tick to see if enemies can move
        response = controller.tick(null, Direction.NONE);

        // zombie can now move
        for (EntityResponse entity : response.getEntities()) {
            if (entity.getType().equals("zombie_toast")) {
                assertFalse(entity.getPosition().getX() == 6 && entity.getPosition().getY() == 1);
            }
            else if (entity.getType().equals("mercenary")) {
                assertEquals(9, entity.getPosition().getX());
                assertEquals(1, entity.getPosition().getY());
            }
        }

        // wait another tick to see if enemies can move
        response = controller.tick(null, Direction.NONE);

        // mercenary can now move
        for (EntityResponse entity : response.getEntities()) {
            if (entity.getType().equals("mercenary")) {
                assertFalse(entity.getPosition().getX() == 9 && entity.getPosition().getY() == 1);
            }
        }
    }
}
