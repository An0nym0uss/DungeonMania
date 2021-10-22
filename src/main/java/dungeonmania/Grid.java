package dungeonmania;

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

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    
    ppublic Player getPlayer() {
        return this.player
    }rivate final int HEIGHT;
    private final int WIDTH;
    private Entity[][][] map;
    private Player player;

    public Grid(int height, int width, Entity[][][] map, Player player) {
        this.HEIGHT = height;
        this.WIDTH = width;
        this.map = map;
        this.player = player;
    }



    /**
     * returns entities on the cell at given position
     */
    public List<Entity> getEntities(int x, int y) {
        return null;
    }
}