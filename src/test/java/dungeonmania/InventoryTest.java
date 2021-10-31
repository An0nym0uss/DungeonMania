package dungeonmania;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;


import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.exceptions.InvalidActionException;

public class InventoryTest {
    @Test
    public void testInventoryAdd() {

        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("items", "peaceful");

        DungeonResponse noTreasure = controller.tick(null, Direction.RIGHT);
        assertTrue(noTreasure.getInventory().isEmpty());

        // pick up treasure which should be added to inventory
        DungeonResponse hasTreasure = controller.tick(null, Direction.RIGHT);
        assertFalse(hasTreasure.getInventory().isEmpty());
        assertTrue(hasTreasure.getInventory().get(0).getType() == "treasure");

    }
    
    @Test
    public void testInventoryUse() {

        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("items", "peaceful");

        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);

        DungeonResponse hasPotion = controller.tick(null, Direction.RIGHT);
        assertFalse(hasPotion.getInventory().isEmpty());

        // use potio which should then be removed
        String id = hasPotion.getInventory().get(0).getId();
        DungeonResponse noPotion = controller.tick(id, null);
        assertTrue(noPotion.getInventory().isEmpty());

    }

    @Test
    public void testIllegalArgument() {

        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("items", "peaceful");

        controller.tick(null, Direction.RIGHT);

        // item can not be used
        DungeonResponse hasTreasure = controller.tick(null, Direction.RIGHT);
        assertTrue(hasTreasure.getInventory().get(0).getType() == "treasure");
        String id = hasTreasure.getInventory().get(0).getId();
        assertThrows(IllegalArgumentException.class, () -> controller.tick(id, null));

    }

    @Test
    public void testInvalidAction() {

        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("items", "peaceful");

        controller.tick(null, Direction.RIGHT);

        // item is not in the inventory
        assertThrows(InvalidActionException.class, () -> controller.tick("invincibility_potion", null));

    }
}

// for (EntityResponse entity : hasSword.getEntities()) {
// if (entity.getType() == "player") {

// }
// }
