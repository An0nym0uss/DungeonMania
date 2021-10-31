package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.google.gson.JsonObject;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import dungeonmania.goals.*;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;


public class GoalsTest {
    
    @Test
    public void testGetJson() {

        ComponentGoal exitGoal = new ReachedExitGoal();

        assertEquals("exit", exitGoal.getJSON().get("goal"));

        ComponentGoal treasureGoal = new CollectAllTreasureGoal();

        assertEquals("treasure", treasureGoal.getJSON().get("goal"));

    }

    @Test
    public void testCollectAllTreasure() {
        // Test end
        DungeonManiaController controller = new DungeonManiaController();
        StandardDungeonMaker.RESOURCE_PATH = "src/test/resources/dungeons/";
        controller.newGame("items", "peaceful");

        DungeonResponse noTreasure = controller.tick(null, Direction.RIGHT);
        assertTrue(noTreasure.getInventory().isEmpty());
        assertEquals(":treasure(2)", noTreasure.getGoals());

        // pick up treasure which should be added to inventory
        DungeonResponse hasTreasure = controller.tick(null, Direction.RIGHT);
        assertFalse(hasTreasure.getInventory().isEmpty());
        assertTrue(hasTreasure.getInventory().get(0).getType() == "treasure");

        // Inputs to get last treasure;
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        assertEquals("", controller.tick(null, Direction.DOWN).getGoals());

    }

    @Test
    public void testAnd1() {
        // Test end
        DungeonManiaController controller = new DungeonManiaController();
        StandardDungeonMaker.RESOURCE_PATH = "src/test/resources/dungeons/";
        controller.newGame("advanced", "peaceful");

        DungeonResponse noTreasure = controller.tick(null, Direction.RIGHT);
        assertTrue(noTreasure.getInventory().isEmpty());
        /*
        assertEquals(":treasure(2)", noTreasure.getGoals());

        // pick up treasure which should be added to inventory
        DungeonResponse hasTreasure = controller.tick(null, Direction.RIGHT);
        assertFalse(hasTreasure.getInventory().isEmpty());
        assertTrue(hasTreasure.getInventory().get(0).getType() == "treasure");

        // Inputs to get last treasure;
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        assertEquals("", controller.tick(null, Direction.DOWN).getGoals());
        */
    }

    @Test
    public void testAnd2() {
        // Test end
        DungeonManiaController controller = new DungeonManiaController();
        StandardDungeonMaker.RESOURCE_PATH = "src/test/resources/dungeons/";
        controller.newGame("advanced-2", "peaceful");

        DungeonResponse noTreasure = controller.tick(null, Direction.RIGHT);

    }


}
