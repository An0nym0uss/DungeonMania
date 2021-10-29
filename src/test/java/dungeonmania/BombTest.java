package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;

public class BombTest {
    @Test
    public void testBombBasic() {
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("bomb-1", "standard");

        DungeonResponse response = controller.tick(null, Direction.RIGHT);

        assertEquals(1, response.getInventory().size());

        String bombId = response.getInventory().get(0).getId();

        response = controller.tick(bombId, null);

        assertEquals(0, response.getInventory().size());
    }

    @Test
    public void testBomb() {

    }

    public static void main(String[] args) {
        BombTest test = new BombTest();
        test.testBombBasic();
    }
    
}
