package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dungeonmania.util.Direction;
import dungeonmania.DungeonManiaController;

public class DungeonMakerTest {
    @Test
    public void testIllegalArgument() {
        DungeonManiaController controller = new DungeonManiaController();
        // Changing where the files are loaded from.
        StandardDungeonMaker.RESOURCE_PATH = "src/test/resources/dungeons/";

        // Put in an incorrect file name.
        assertThrows(IllegalArgumentException.class, () -> {
            controller.newGame("noexisted_file", "peaceful").getGoals();
            }
        );

        // Put in an incorrect mode name.
        assertThrows(IllegalArgumentException.class, () -> {
            controller.newGame("maze", "creative_mode").getGoals();
            }
        );

        // Correct dungeon however the json file itself is formatted wrong.
        assertThrows(IllegalArgumentException.class, () -> {
            controller.newGame("incorrect_format", "peaceful").getGoals();
            }
        );

    }
}
