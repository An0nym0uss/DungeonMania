package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.util.Direction;
import dungeonmania.DungeonManiaController;

public class ExitTest {
    @Test
    /**
     * Attempts to finish a game by reaching the exit.
     */
    public void testExitBasic() {
        DungeonManiaController controller = new DungeonManiaController();
        // Changing where the files are loaded from.
        StandardDungeonMaker.RESOURCE_PATH = "src/test/resources/dungeons/";

        // start game
        assertEquals(":exit", controller.newGame("exit", "peaceful").getGoals());

        // finish game
        assertEquals("", controller.tick(null, Direction.RIGHT).getGoals());
        
    }
}
