package dungeonmania;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;

public class BuildableTest {
    @Test
    public void testShowBuildable() {

        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("items", "peaceful");
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);

        DungeonResponse hasWood = controller.tick(null, Direction.RIGHT);
        assertTrue(hasWood.getBuildables().isEmpty());

        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);

        DungeonResponse canBuildBow = controller.tick(null, Direction.RIGHT);
        assertEquals(4, canBuildBow.getInventory().size());
        assertFalse(canBuildBow.getBuildables().isEmpty());
        assertTrue(canBuildBow.getBuildables().get(0) == "bow");

    }
    
    @Test
    public void testBuildBow() {

        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("items", "peaceful");

        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        for (int i = 0; i < 4; i++) {
            controller.tick(null, Direction.RIGHT);
        }

        DungeonResponse canBuildBow = controller.tick(null, Direction.RIGHT);
        assertEquals(4, canBuildBow.getInventory().size());
        String id = canBuildBow.getBuildables().get(0);

        DungeonResponse hasBow = controller.build(id);
        assertEquals(1, hasBow.getInventory().size());
        assertTrue(hasBow.getInventory().get(0).getType() == "bow");
        assertTrue(hasBow.getBuildables().isEmpty());
    }
}