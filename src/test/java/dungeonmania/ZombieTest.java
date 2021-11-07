package dungeonmania;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ZombieTest {
    @Test

    public void testZombieBasic() {
        DungeonManiaController controller = new DungeonManiaController();
        int totalZombies = 0;
        // start game
        DungeonResponse response = controller.newGame("zombie", "peaceful");

        // attempt to walk into wall
        DungeonResponse firstTick = controller.tick(null, Direction.RIGHT);

        // zombie can't move onto wall
        
        assertTrue(firstTick.getEntities().get(2).getType().equals("zombie_toast") && firstTick.getEntities().get(2).getPosition().getX() == 1 && firstTick.getEntities().get(2).getPosition().getY() == 0);

        // count any zombies in dungeon
        for (EntityResponse entity : response.getEntities()) {
            if (entity.getType() == "zombie_toast") {
                totalZombies++;
            }
        }

        // no Zombies after 15 on standard
        assertTrue(totalZombies == 0);
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
