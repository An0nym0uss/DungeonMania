package dungeonmania;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;

public class InventoryTest {
    @Test
    public void testInventoryAdd() {

        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("items", "peaceful");

        DungeonResponse noTreasure = controller.tick(null, Direction.RIGHT);
        assertTrue(noTreasure.getInventory().isEmpty());

        DungeonResponse hasTreasure = controller.tick(null, Direction.RIGHT);
        assertFalse(hasTreasure.getInventory().isEmpty());
        assertTrue(hasTreasure.getInventory().get(0).getType() == "treasure");

    }
    
    @Test
    public void testInventoryRemove() {

        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("items", "peaceful");

        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);

        DungeonResponse hasPotion = controller.tick(null, Direction.RIGHT);
        assertFalse(hasPotion.getInventory().isEmpty());

        String id = hasPotion.getInventory().get(0).getId();
        DungeonResponse noPotion = controller.tick(id, null);
        assertTrue(noPotion.getInventory().isEmpty());

    }
}

// for (EntityResponse entity : hasSword.getEntities()) {
// if (entity.getType() == "player") {

// }
// }
