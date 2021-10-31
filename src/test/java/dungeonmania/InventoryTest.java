package dungeonmania;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.entities.collectable.Sword;
import dungeonmania.entities.player.Inventory;
import dungeonmania.util.Position;
import dungeonmania.exceptions.InvalidActionException;

public class InventoryTest {
    @Test
    public void testInventoryAdd() {

        DungeonManiaController controller = new DungeonManiaController();
        StandardDungeonMaker.RESOURCE_PATH = "src/test/resources/dungeons/";
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
        StandardDungeonMaker.RESOURCE_PATH = "src/test/resources/dungeons/";
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
        StandardDungeonMaker.RESOURCE_PATH = "src/test/resources/dungeons/";
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
        StandardDungeonMaker.RESOURCE_PATH = "src/test/resources/dungeons/";
        controller.newGame("items", "peaceful");

        controller.tick(null, Direction.RIGHT);

        // item is not in the inventory
        assertThrows(InvalidActionException.class, () -> controller.tick("invincibility_potion", null));

    }

    @Test
    public void testRemoveWrongItem() {
        Inventory inventory = new Inventory();

        Sword sword = new Sword(new Position(1, 1), 1, 1);

        inventory.addItem(sword);

        assertNull(inventory.getItem("invalid item"));
        inventory.getItem("sword");
        inventory.checkItem("sword");
        inventory.removeNonSpecificItem("sword");
        inventory.removeNonSpecificItem("invalid item");


        
    }

    @Test
    public void testSword() {

        Sword sword = new Sword(new Position(1, 1), 1, 1);
        
        assertTrue(sword.getAttack() == 1);
        assertTrue(sword.getDurability() == 1);
        assertFalse(sword.isBroken());
        sword.setDurability(0);
        assertTrue(sword.isBroken());
    }
}

// for (EntityResponse entity : hasSword.getEntities()) {
// if (entity.getType() == "player") {

// }
// }
