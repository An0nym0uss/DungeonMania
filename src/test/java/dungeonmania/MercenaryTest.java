package dungeonmania;

import dungeonmania.constants.Layer;
import dungeonmania.entities.Entity;
import dungeonmania.entities.collectable.Treasure;
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

    @Test
    public void testMercenaryFollowsPlayerWithObstacles2() {
        Mercenary mercenary = new Mercenary(new Position(0, 1), 1, 1, 1);
        Player player = new Player(new Position(2, 1), new Standard());
        Wall wall = new Wall(new Position(1, 0));
        Wall wall2 = new Wall(new Position(1, 1));
        Wall wall3 = new Wall(new Position(1, 2));

        /**
         * [] [W] []
         * [M] [W] [P]
         * [] [W] []
         *
         */
        Entity[][][] map = new Entity[3][3][Layer.LAYER_SIZE];
        map[player.getPosition().getX()][player.getPosition().getY()][player.getPosition().getLayer()] = player;
        map[mercenary.getPosition().getX()][mercenary.getPosition().getY()][mercenary.getPosition().getLayer()] = mercenary;
        map[wall.getPosition().getX()][wall.getPosition().getY()][wall.getPosition().getLayer()] = wall;
        map[wall2.getPosition().getX()][wall2.getPosition().getY()][wall2.getPosition().getLayer()] = wall2;
        map[wall3.getPosition().getX()][wall3.getPosition().getY()][wall3.getPosition().getLayer()] = wall3;

        Grid grid = new Grid(3, 3, map, player);
        mercenary.update(grid);

        assertEquals(0, mercenary.getPosition().getX());
        assertEquals(1, mercenary.getPosition().getY());
    }

    @Test
    public void testMercenaryBribed() {
        Mercenary mercenary = new Mercenary(new Position(2, 1), 1, 1, 1);
        Player player = new Player(new Position(0, 0), new Standard());
        Treasure treasure = new Treasure(new Position(1, 0));

        /**
         * [P] [0] []
         * [] [] [M]
         * [] [] []
         *
         */
        Entity[][][] map = new Entity[3][3][Layer.LAYER_SIZE];
        map[player.getPosition().getX()][player.getPosition().getY()][player.getPosition().getLayer()] = player;
        map[mercenary.getPosition().getX()][mercenary.getPosition().getY()][mercenary.getPosition().getLayer()] = mercenary;
        map[treasure.getPosition().getX()][treasure.getPosition().getY()][treasure.getPosition().getLayer()] = treasure;


        Grid grid = new Grid(3, 3, map, player);
        mercenary.update(grid);

        assertEquals(2, mercenary.getPosition().getX());
        assertEquals(0, mercenary.getPosition().getY());
    }
    
}
