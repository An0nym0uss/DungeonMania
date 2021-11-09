package dungeonmania;

import dungeonmania.constants.Layer;
import dungeonmania.entities.Entity;
import dungeonmania.entities.enemy.Mercenary;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.statics.Wall;
import dungeonmania.modes.Mode;
import dungeonmania.modes.Standard;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

public class MercenaryTest {
    // Test movement 
    //  - testMercenaryFollowsPlayer (in an empty map)
    //  - testMercenaryFollowsPlayerWithObstacles           
    /** (Mercenary finds best path without obstacles)
     P . .
     . M .
     . . .
     */
    /** (Mercenary finds best path with obstacles)
     . . . 
     M W P
     . . .
     */
    /** (Mercenary stays)
     . W . 
     M W P
     W . .
     */
    DungeonManiaController controller = new DungeonManiaController();
    
    @Test
    public void testMercenaryFollowsPlayer() {
        
    Mercenary mercenary = new Mercenary(new Position(1, 1), 1, 1, 1);
    Player player = new Player(new Position(0, 0), new Standard());

        /**
         * [P] [] []
         * [] [M] []
         * [] [] []
         */
    Entity[][][] map = new Entity[3][3][Layer.LAYER_SIZE];
    map[player.getPosition().getX()][player.getPosition().getY()][player.getPosition().getLayer()] = player;
    map[mercenary.getPosition().getX()][mercenary.getPosition().getY()][mercenary.getPosition().getLayer()] = mercenary;

    Grid grid = new Grid(3, 3, map, player);
    mercenary.update(grid);

    assertEquals(1, mercenary.getPosition().getX());
    assertEquals(0, mercenary.getPosition().getY());
    }
    
    @Test
    public void testMercenaryFollowsPlayerWithObstacles() {
        Mercenary mercenary = new Mercenary(new Position(0, 1), 1, 1, 1);
        Player player = new Player(new Position(2, 1), new Standard());
        Wall wall = new Wall(new Position(1, 1));

        /**
         * [] [] []
         * [M] [W] [P]
         * [] [] []
         */
        Entity[][][] map = new Entity[3][3][Layer.LAYER_SIZE];
        map[player.getPosition().getX()][player.getPosition().getY()][player.getPosition().getLayer()] = player;
        map[mercenary.getPosition().getX()][mercenary.getPosition().getY()][mercenary.getPosition().getLayer()] = mercenary;
        map[wall.getPosition().getX()][wall.getPosition().getY()][wall.getPosition().getLayer()] = wall;

        Grid grid = new Grid(3, 3, map, player);
        mercenary.update(grid);

        assertEquals(0, mercenary.getPosition().getX());
        assertEquals(0, mercenary.getPosition().getY());
    }
}
