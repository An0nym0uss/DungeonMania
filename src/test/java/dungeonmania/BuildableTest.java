package dungeonmania;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import dungeonmania.exceptions.InvalidActionException;

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

        // pick up ingredients
        DungeonResponse hasWood = controller.tick(null, Direction.RIGHT);
        assertTrue(hasWood.getBuildables().isEmpty());
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);

        DungeonResponse canBuildBow = controller.tick(null, Direction.RIGHT);
        // now should have 4 items in inventory
        assertEquals(4, canBuildBow.getInventory().size());
        // should be able to build bow
        assertFalse(canBuildBow.getBuildables().isEmpty());
        assertTrue(canBuildBow.getBuildables().get(0) == "bow");

    }
    
    @Test
    public void testBuildBow() {

        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("items", "peaceful");

        // pick up ingredients
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        for (int i = 0; i < 5; i++) {
            controller.tick(null, Direction.RIGHT);
        }

        // should be able to build bow
        DungeonResponse canBuildBow = controller.tick(null, Direction.RIGHT);
        assertEquals(4, canBuildBow.getInventory().size());
        assertFalse(canBuildBow.getBuildables().isEmpty());
        String id = canBuildBow.getBuildables().get(0);

        // build a bow which shoud be added to the inventory and player is
        // no longer able to build a bow
        DungeonResponse hasBow = controller.build(id);
        assertEquals(1, hasBow.getInventory().size());
        assertTrue(hasBow.getInventory().get(0).getType() == "bow");
        assertTrue(hasBow.getBuildables().isEmpty());
    }
    
    @Test
    public void testBuildShield() {

        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("items", "peaceful");

        // pick up ingredients
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        for (int i = 0; i < 4; i++) {
            controller.tick(null, Direction.RIGHT);
        }

        // should be able to build bow
        DungeonResponse canBuildShield = controller.tick(null, Direction.RIGHT);
        assertEquals(4, canBuildShield.getInventory().size());
        String id = canBuildShield.getBuildables().get(0);

        // build a bow which shoud be added to the inventory and player is
        // no longer able to build a bow
        DungeonResponse hasBow = controller.build(id);
        assertEquals(1, hasBow.getInventory().size());
        assertFalse(hasBow.getBuildables().isEmpty());
        assertTrue(hasBow.getInventory().get(0).getType() == "shield");
        assertTrue(hasBow.getBuildables().isEmpty());
    }

    @Test
    public void testBuildShieldAlt() {

        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("items", "peaceful");

        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        for (int i = 0; i < 4; i++) {
            controller.tick(null, Direction.RIGHT);
        }
        controller.tick(null, Direction.DOWN);

        DungeonResponse canBuildShield = controller.tick(null, Direction.RIGHT);
        assertEquals(4, canBuildShield.getInventory().size());
        assertFalse(canBuildShield.getBuildables().isEmpty());
        String id = canBuildShield.getBuildables().get(0);

        DungeonResponse hasBow = controller.build(id);
        assertEquals(1, hasBow.getInventory().size());
        assertTrue(hasBow.getInventory().get(0).getType() == "shield");
        assertTrue(hasBow.getBuildables().isEmpty());
    }

    @Test
    public void testInvalidAction() {

        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("items", "peaceful");

        assertThrows(InvalidActionException.class,() -> controller.build("bow"));
    }

    @Test
    public void testIllegalArgument() {

        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("items", "peaceful");

        assertThrows(IllegalArgumentException.class, () -> controller.build("treasure"));
    }
}