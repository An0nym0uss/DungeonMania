package dungeonmania;

import java.util.Arrays;
import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.ObserverEntity;
import dungeonmania.entities.player.Player;
import dungeonmania.util.Position;

/**
 * Represents the grid of the dungeon and the entities it contains.
 * 
 * @author Enoch Kavur, Hoy Wang
 * 
 * @invariant 
 */
public class Grid implements GridSubject {

    private final int HEIGHT;
    private final int WIDTH;
    private final int LAYER_SIZE = 4;
    private Entity[][][] map;
    private Player player;

    /**
     * Getter for height.
     * 
     */
    public int getHeight() {
        return this.HEIGHT;
    }

    /**
     * Getter for width.
     * 
     */
    public int getWidth() {
        return this.WIDTH;
    }

    /**
     * Getter for layer size.
     * 
     */
    public int getLayerSize() {
        return this.LAYER_SIZE;
    }

    /**
     * Getter for map.
     * 
     */
    public Entity[][][] getMap() {
        return this.map;
    }
    
    /**
     * Getter for player.
     * 
     */
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
     * Notify all Observer Entites and call update on them.
     * 
     * @post game state goes to next tick with all entities performing their correct behaviour.
     */
    @Override
    public void notifyObserverEntities() {
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                for (int z = 0; z < getLayerSize(); z++) {
                    if (map[x][y][z] != null) map[x][y][z].update();
                }
            }
        }
    }


    /**
     * returns entities on the cell at given position
     * @pre 0 <= x < WIDTH
     * @pre 0 <= y < HEIGHT
     */
    public List<Entity> getEntities(int x, int y) {
        return Arrays.asList(map[x][y]);
    }

    /**
     * Add an entity to grid.
     * 
     * @param o entity to add.
     * 
     * @pre the position of o are in bounds of grid size.
     * @pre o is a subclass of entity.
     * @pre there is not already entity of the same type (static, enemy, etc.) already at that position.
     * @post entity is added to grid.
     * 
     */
    @Override
    public void attach(ObserverEntity o) {

        Entity e = (Entity) o;
        
        Position entity_position = e.getPosition();

        int x = entity_position.getX();
        int y = entity_position.getY();
        int layer = entity_position.getLayer();

        map[x][y][layer] = e;
    }

    /**
     * 
     * @param o is entity to be removed.
     * @pre the position of o are in bounds of grid size.
     * @pre o is a subclass of entity.
     * @pre there is not already entity of the same type (static, enemy, etc.) already at that position.
     * @post entity is removed from grid.
     */
    @Override
    public void dettach(ObserverEntity o) {
        Entity e = (Entity) o;
        
        Position entity_position = e.getPosition();

        int x = entity_position.getX();
        int y = entity_position.getY();
        int layer = entity_position.getLayer();

        map[x][y][layer] = null;
    }

}