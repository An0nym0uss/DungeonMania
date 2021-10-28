package dungeonmania;

import java.time.LocalDateTime;
import java.util.UUID;

import org.json.JSONObject;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.goals.ComponentGoal;
import dungeonmania.modes.Mode;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;

public class Dungeon {
    
    private String dungeonId; // Dungeon game file.
    private String dungeonName; // Type of dungeon
    private Mode gameMode = null;
    private ComponentGoal goal = null;
    private Grid grid = null;

    public Dungeon(String name, Mode mode) {
        this.dungeonId = name + LocalDateTime.now().toString();
        this.dungeonName = name;
        this.gameMode = mode;

    }

    public String getDungeonId() {
        return this.dungeonId;
    }

    public String getDungeonName() {
        return this.dungeonName;
    }

    public void setGameMode(Mode gameMode) {
        this.gameMode = gameMode;
    }

    public void setGoal(ComponentGoal goal) {
        this.goal = goal;
    }

    public Mode getGameMode() {
        return this.gameMode;
    }

    public ComponentGoal getGoal() {
        return this.goal;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public Grid getGrid() {
        return this.grid;
    }

    public void readMap(JSONObject file) {

    }

    public void tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
        grid.movePlayer(movementDirection);
    }

    /**
     * player movement
     */
    private void move(Direction movementDirection) {
    }

    /**
     * enemies movement
     */
    private void moveEntities() {
    }

    /**
     * battle starts when player and enemy appear in the same cell
     */
    private void battle() {
    }

    /**
     * uses item in inventory
     */
    private void useItem(String itemUsed) {
    }

    public void interact(String entityId) throws IllegalArgumentException, InvalidActionException {

    }

    public void build(String buildable) throws IllegalArgumentException, InvalidActionException {

    }   
}
