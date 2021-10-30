package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;

public class ExitTest {
    @Test
    /**
     * Attempts to finish a game by reaching the exit.
     */
    public void testExitBasic() {
        DungeonManiaController controller = new DungeonManiaController();

        // start game
        controller.newGame("exit", "peaceful");

        // finish game
        controller.tick(null, Direction.RIGHT);

        // how do we check game is actually finished?
    }
}
