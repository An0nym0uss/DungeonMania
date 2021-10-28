package dungeonmania;

import dungeonmania.modes.*;
import dungeonmania.response.models.*;
import dungeonmania.goals.*;
import dungeonmania.util.*;
import dungeonmania.exceptions.*;
import org.json.JSONObject;

public class Dungeon {
    
    private String dungeonId; // Dungeon game file.
    private String dungeonName; // Type of dungeon
    private Mode gameMode;
    private ComponentGoal goal;
    private Grid grid;

    public Dungeon(String id, String name, String mode) {
        this.dungeonId = id;
        this.dungeonName = name;
        //this.gameMode = new Mode(mode);

    }

    public String getDungeonId() {
        return this.dungeonId;
    }

    public String getDungeonName() {
        return this.dungeonName;
    }

    public Mode getGameMode() {
        return this.gameMode;
    }

    public ComponentGoal getGoal() {
        return this.goal;
    }

    public Grid getGrid() {
        return this.grid;
    }

    public void readMap(JSONObject file) {

    }

    public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
        return null;
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

    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        return null;
    }

    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        return null;
    }   
}
