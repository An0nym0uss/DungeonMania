package dungeonmania;

import java.util.Arrays;
import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.player.Player;

/**
 * Represents the grid of the dungeon and the entities it contains.
 * 
 * @author Enoch Kavur, Hoy Wang
 * 
 * @invariant 
 */
public class Grid {

    private final int HEIGHT;
    private final int WIDTH;
    private Entity[][][] map;
    private Player player;


    public int getHeight() {
        return this.HEIGHT;
    }

    public int getWidth() {
        return this.WIDTH;
    }

    
    public Player getPlayer() {
        return this.player;
    }

    public Grid(int height, int width, Entity[][][] map, Player player) {
        this.HEIGHT = height;
        this.WIDTH = width;
        this.map = map;
        this.player = player;
    }



    /**
     * returns entities on the cell at given position
     * @pre 0 <= x < WIDTH
     * @pre 0 <= y < HEIGHT
     */
    public List<Entity> getEntities(int x, int y) {
        return Arrays.asList(map[x][y]);
    }
}