package dungeonmania;

import org.junit.jupiter.api.Test;

import dungeonmania.util.Direction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MazeGeneratorTest {

    @Test
    public void testWithValidInputs() {
        DungeonManiaController controller = new DungeonManiaController();

        assertEquals(":exit", controller.generateDungeon(1, 1, 1, 1, "peaceful").getGoals());

    }

    @Test
    public void testCorrectStartAndEnd() {
        DungeonManiaController controller = new DungeonManiaController();

        assertEquals(":exit", controller.generateDungeon(1, 1, 1, 1, "hard").getGoals());

        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        assertEquals("", controller.tick(null, Direction.UP).getGoals());


    }

    @Test
    public void testIllegalArguments() {
        DungeonManiaController controller = new DungeonManiaController();

        // Illegal game mode
        assertThrows(IllegalArgumentException.class, () -> {
            controller.generateDungeon(1, 1, 3, 3, "invalid-mode").getGoals();
            }
        );

        // Illegal xStart
        assertThrows(IllegalArgumentException.class, () -> {
            controller.generateDungeon(154514, 1, 3, 3, "peaceful").getGoals();
            }
        );

        // Illegal yStart
        assertThrows(IllegalArgumentException.class, () -> {
            controller.generateDungeon(1, -1, 3, 3, "peaceful").getGoals();
            }
        );

        // Illegal xEnd
        assertThrows(IllegalArgumentException.class, () -> {
            controller.generateDungeon(1, 1, -3, 3, "peaceful").getGoals();
            }
        );

        // Illegal yEnd
        assertThrows(IllegalArgumentException.class, () -> {
            controller.generateDungeon(1, 1, 3, 51, "peaceful").getGoals();
            }
        );

    }
}
