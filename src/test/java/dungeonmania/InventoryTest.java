package dungeonmania;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.entities.collectable.Sword;
import dungeonmania.entities.collectable.Armour;
import dungeonmania.entities.player.Inventory;
import dungeonmania.util.Position;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.entities.player.Player;
import dungeonmania.modes.Standard;
import dungeonmania.constants.Layer;
import dungeonmania.entities.Entity;

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

    @Test
    public void testMultipleSword() {

        Player player = new Player(new Position(1, 1, Layer.PLAYER), new Standard());
        Sword sword1 = new Sword(new Position(2, 1));
        Sword sword2 = new Sword(new Position(3, 1));
        Grid grid = new Grid(10, 10, new Entity[10][10][Layer.LAYER_SIZE], null);
        grid.attach(sword1);
        grid.attach(sword2);

        assertTrue(player.getInventory().getItems().size() == 0);

        // pick up first sword, inventory has one item
        player.move(grid, Direction.RIGHT);
        assertTrue(player.getInventory().getItems().size() == 1);

        // cant pick up duplicate sword, item count remain at one
        player.move(grid, Direction.RIGHT);
        assertTrue(player.getInventory().getItems().size() == 1);
    }

    @Test
    public void testMultipleArmour() {

        Player player = new Player(new Position(1, 1, Layer.PLAYER), new Standard());
        Armour armour1 = new Armour(new Position(2, 1));
        Armour armour2 = new Armour(new Position(3, 1));
        Grid grid = new Grid(10, 10, new Entity[10][10][Layer.LAYER_SIZE], null);
        grid.attach(armour1);
        grid.attach(armour2);

        assertTrue(player.getInventory().getItems().size() == 0);

        // pick up first armour, inventory has one item
        player.move(grid, Direction.RIGHT);
        assertTrue(player.getInventory().getItems().size() == 1);

        // cant pick up duplicate armour, item count remain at one
        player.move(grid, Direction.RIGHT);
        assertTrue(player.getInventory().getItems().size() == 1);
    }
}

// for (EntityResponse entity : hasSword.getEntities()) {
// if (entity.getType() == "player") {

// }
// }
