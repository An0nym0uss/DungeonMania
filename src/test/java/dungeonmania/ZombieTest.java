package dungeonmania;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ZombieTest {
    @Test
    /**
     * Tests zombies not moving where they shouldn't, and also that they can be killed by the player
     */
    public void testZombieBasic() {
        DungeonManiaController controller = new DungeonManiaController();
        // Changing where the files are loaded from.
        StandardDungeonMaker.RESOURCE_PATH = "src/test/resources/dungeons/";

        // start game
        controller.newGame("zombie", "standard");

        // see if zombies stay put
        DungeonResponse firstTick = controller.tick(null, Direction.NONE);

        /*for( int i = 0; i < firstTick.getEntities().size(); i++) {
            EntityResponse entResponse = firstTick.getEntities().get(i);
            if (entResponse.getType().equals("zombie_toast")) {
                assertEquals(1, entResponse.getPosition().getX());
                assertTrue(entResponse.getPosition().getY() >= 1);
                assertTrue(entResponse.getPosition().getY() <= 2);
            }
        }*/

        // move boulder
        DungeonResponse secondTick = controller.tick(null, Direction.RIGHT);

        int zombieCount = 0;

        for( int i = 0; i < secondTick.getEntities().size(); i++) {
            EntityResponse entResponse = secondTick.getEntities().get(i);
            if (entResponse.getType().equals("zombie_toast")) {
                zombieCount++;
            }
        }

        // zombie walked into player after boulder move
        assertTrue(zombieCount == 2);

        controller.tick(null, Direction.DOWN);

        // move to exit
        DungeonResponse fourthTick = controller.tick(null, Direction.DOWN);

        zombieCount = 0;

        for( int i = 0; i < fourthTick.getEntities().size(); i++) {
            EntityResponse entResponse = fourthTick.getEntities().get(i);
            if (entResponse.getType().equals("zombie_toast")) {
                zombieCount++;
            }
        }

        // player killed another zombie on the walk to exit
        assertTrue(zombieCount == 1);

        // finished game
        assertEquals("", fourthTick.getGoals());
    }

//    @Test
//    public void testZomWhenNoObstaclesbieMovement() {
//        // Setup (initialise variables)
//        Player player = new Player(new Position(0, 0), Mode.Easy);
//        Entity[][][] map = new ArrayList<>(); // zombie
//        Grid grid = new Grid(10, 10, map, player);
//        Zombie zombie = new Zombie(new Position(1, 1), 1, 1, 1);
//
//        // Call the method (the method you want to test)
//        zombie.update(grid);
//
//        // Check
//        Position newPosition = zombie.getPosition();
//        assertTrue()
//        zombie.getPosition() is not (1,1);
//    }
//
//    public void testZombieMovementBlockedByWall() {
//        // TODO (eg, throw 4 wall around the zombie and the zombie should not be able to move)
//
//        // Setup (initialise variables)
//        Player player = new Player(new Position(), Mode.Easy)
//        Entity[][][] map = new ArrayList<>(); // zombie
//        Grid grid = new Grid(10, 10, map, player);
//        Zombie zombie = new Zombie(new Position(1, 1), 1, 1, 1);
//
//        // Call the method (the method you want to test)
//        zombie.update(grid);
//
//        // Check
//        Position newPosition = zombie.getPosition();
//        assertTrue()
//        zombie.getPosition() is not (1,1)
//    }
//
//    @Test
//    public void testZombieAttacked() {
//        // Check zombie's health (if it gets attacked by the player)
//    }
         
    
}
