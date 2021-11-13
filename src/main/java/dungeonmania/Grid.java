package dungeonmania;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.constants.Layer;
import dungeonmania.entities.Entity;
import dungeonmania.entities.ObserverEntity;
import dungeonmania.entities.enemy.Mercenary;
import dungeonmania.entities.enemy.Zombie;
import dungeonmania.entities.player.OlderSelf;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.statics.Portal;
import dungeonmania.entities.statics.SwampTile;
import dungeonmania.entities.statics.TimeTravellingPortal;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

/**
 * Represents the grid of the dungeon and the entities it contains.
 * 
 * @author Enoch Kavur, Hoy Wang
 * 
 * @invariant 
 */
public class Grid implements GridSubject, GameToJSON {

    private final int HEIGHT;
    private final int WIDTH;
    private final int LAYER_SIZE = Layer.LAYER_SIZE;
    private Entity[][][] map;
    private Player player;
    private Map<String, Position> portalMapping = new HashMap<>();
    private List<OlderSelf> olderSelves = new ArrayList<>();
    private OlderSelf prevPlayer;

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

    public void setPlayer(Player player) {
        this.player = player;
    }
    
    /**
     * Getter for player.
     * 
     */
    public Player getPlayer() {
        return this.player;
    }

    public List<OlderSelf> getOlderSelves() {
        return this.olderSelves;
    }

    public OlderSelf getPrevPlayer() {
        return this.prevPlayer;
    }

    public void setPrevPlayer(OlderSelf prevPlayer) {
        this.prevPlayer = prevPlayer;
    }

    public Grid(int height, int width, Entity[][][] map, Player player) {
        this.HEIGHT = height;
        this.WIDTH = width;
        this.map = map;
        this.player = player;
    }

    public Grid(Grid that) {
        this.HEIGHT = that.getHeight();
        this.WIDTH = that.getWidth();
        this.map = new Entity[WIDTH][HEIGHT][LAYER_SIZE];
        this.player = that.getPlayer();
        this.olderSelves = new ArrayList<>();

        for (int x = 0; x < this.getWidth(); x++) {
            for (int y = 0; y < this.getHeight(); y++) {
                for (int z = 0; z < this.getLayerSize(); z++) {
                    Entity entity = that.getMap()[x][y][z];
                    if (entity != null) {
                        if (entity.getType().equalsIgnoreCase("player")) {
                            OlderSelf older = new OlderSelf((Player)entity);
                            this.prevPlayer = older;
                        } else if (entity.getType().equalsIgnoreCase("older_self")) {
                            OlderSelf older = new OlderSelf((OlderSelf)entity, true);
                            this.olderSelves.add(older);
                            map[x][y][z] = older;
                        } else {
                            map[x][y][z] = entity.clone();
                        }
                    }
                }
            }
        }
    }

    /**
     * Notify all Observer Entites and call update on them.
     * 
     * @post game state goes to next tick with all entities performing their correct behaviour.
     */
    @Override
    public void notifyObserverEntities() {
        //otherwise we run into issues if any Entity.update() modifies the map[][][]
        Entity[][][] clonedMap = new Entity[getWidth()][getHeight()][getLayerSize()]; 
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                for (int z = 0; z < getLayerSize(); z++) {
                    clonedMap[x][y][z] = map[x][y][z];
                }
            }
        }

        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                for (int z = 0; z < getLayerSize(); z++) {
                    if (clonedMap[x][y][z] != null) {
                        if (z == 0 || !(clonedMap[x][y][Layer.STATIC] instanceof SwampTile && !((SwampTile)clonedMap[x][y][Layer.STATIC]).canMoveOff(clonedMap[x][y][z]))) {
                            clonedMap[x][y][z].update(this);
                        }
                    }
                }
            }
        }
    }


    /**
     * returns entities on the cell at given position
     * @pre 0 <= x < WIDTH
     * @pre 0 <= y < HEIGHT
     * 
     * @post List of entities at x, y;
     */
    public List<Entity> getEntities(int x, int y) {
        return Arrays.asList(map[x][y]).stream().filter(e -> e != null).collect(Collectors.toList());
    }

    /**
     * check if zombie is present in the map
     * @return
     */
    public boolean hasZombie() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                for (Entity entity : getEntities(x, y)) {
                    if (entity instanceof Zombie) {
                        return true;
                    }
                }
            }
            
        }
        return false;
    }

    public boolean isPlayerOnTimeTravelPortal() {
        Position playerPos = this.player.getPosition();
        Position portalPos = null;
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                for (Entity entity : getEntities(x, y)) {
                    if (entity instanceof TimeTravellingPortal) {
                        portalPos = entity.getPosition();
                        if (playerPos.equals(portalPos)) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
            
        }
        return false;
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

        if (e.getType().equalsIgnoreCase("player")) {
            setPlayer((Player) e);
        // For linking portals
        } else if (e instanceof Portal) {
            String colour = ((Portal) e).getColour();

            // If already added a portal of same colour then create link between
            if (portalMapping.containsKey(colour)) {
                Position other_portal = portalMapping.get(colour);

                int x_other = other_portal.getX();
                int y_other = other_portal.getY();
                int layer_other = other_portal.getLayer();

                ((Portal) e).setCorrespondingPortal((Portal) map[x_other][y_other][layer_other]);
                ((Portal) map[x_other][y_other][layer_other]).setCorrespondingPortal((Portal) e);
            
            // Else mark that we have seen that portal of colour
            } else {
                portalMapping.put(colour, e.getPosition());
            }
        }
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

        /*
        if (e instanceof Player) {
            setPlayer(null);
        }
        */
    }

    public void movePlayer(Direction d) {
        player.move(this, d);
    }

    @Override
    public JSONObject getJSON() {
        JSONObject grid = new JSONObject();

        JSONArray entities = new JSONArray();

        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                for (int z = 0; z < getLayerSize(); z++) {
                    if (map[x][y][z] != null) entities.put(map[x][y][z].getJSON());
                }
            }
        }

        grid.put("entities", entities);

        return grid;
    }

    public Grid clone() {
        return new Grid(this);
    }

}