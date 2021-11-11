package dungeonmania;

import java.time.LocalDateTime;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.enemy.Spider;
import dungeonmania.entities.player.Player;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.goals.ComponentGoal;
import dungeonmania.modes.Mode;
import dungeonmania.util.Direction;

public class Dungeon implements GameToJSON {
    
    private String dungeonId; // Dungeon game file.
    private String dungeonName; // Type of dungeon
    private Mode gameMode = null;
    private ComponentGoal goal = null;
    private Grid grid = null;
    private List<Grid> prevGrids = new ArrayList<>();

    public Dungeon(String name, Mode mode) {
        this.dungeonId = name + " (" + LocalDateTime.now().toString() + ")";
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
        grid.getPlayer().getPrevTicks().add(new Tick(itemUsed, movementDirection));
        addPrevGrid(grid);
        
        if (movementDirection != null) {
            grid.movePlayer(movementDirection);
        }
        if (itemUsed != null) {
            grid.getPlayer().useItem(itemUsed, grid);
        }

        if (this.goal != null && goal.isAchieved(grid)) {
            this.goal = null;
        }

        // older selves move or use item
        Iterator<Player> itr = grid.getOlderSelves().iterator();
        while (itr.hasNext()) {
            Player olderSelf = itr.next();
            // older self does not have movement, rewind has stopped
            System.out.println(olderSelf);
            if (olderSelf.getPrevTicks().isEmpty()) {
                grid.dettach(olderSelf);
                itr.remove();
            } else {
                // Tick tick = olderSelf.getPrevTicks().remove(0);
                // if (tick.getMovementDirection() != null) {
                //     olderSelf.move(grid, tick.getMovementDirection());
                // }
                // if (tick.getItemUsed() != null) {
                //     olderSelf.useItem(tick.getItemUsed(), grid);
                // }
            }
        }

        grid.notifyObserverEntities();
        Spider.checkForNextSpawn(grid);

    }

    /**
     * make current grid a copy and add the copy to the prevGrids list
     * @param grid
     */
    private void addPrevGrid(Grid grid) {
        Grid copy = grid.clone();
        prevGrids.add(copy);
    }

    public void interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        grid.getPlayer().interact(entityId, grid);
    }

    public void build(String buildable) throws IllegalArgumentException, InvalidActionException {
        grid.getPlayer().craftItem(buildable, grid);
    }

    public void rewind(int ticks) throws IllegalArgumentException {
        if (ticks <= 0) {
            throw new IllegalArgumentException("Can only rewind positive integer ticks.");
        }
        // find the grid in the past
        int targetIndex = prevGrids.size() - ticks;
        if (targetIndex < 0) {
            targetIndex = 0;
        }
        Grid prevGrid = prevGrids.get(targetIndex);

        // copy previous movements to older self
        List<Tick> prevTicks = new ArrayList<>();
        int start = grid.getPlayer().getPrevTicks().size() - ticks;
        if (start < 0) {
            start = 0;
        }
        for (int i = start; i < grid.getPlayer().getPrevTicks().size(); i++) {
            prevTicks.add(grid.getPlayer().getPrevTicks().get(i));
        }
        prevGrid.getPrevPlayer().setPrevTicks(prevTicks);

        // add player at that time into the list of older selves
        prevGrid.getOlderSelves().add(prevGrid.getPrevPlayer());

        // set current player to previous grid
        prevGrid.setPrevPlayer(this.grid.getPlayer().clone());
        prevGrid.setPlayer(this.grid.getPlayer());
        prevGrid.attach(this.grid.getPlayer());
        this.grid = prevGrid;

        // delete grids after current (rewinded) tick
        int size = prevGrids.size() - 1;
        for (int i = size; i > targetIndex; i--) {
            prevGrids.remove(i);
        }
    }

    @Override
    public JSONObject getJSON() {
        JSONObject dungeon = new JSONObject();

        dungeon.put("name", dungeonName);

        if (grid != null) {
            dungeon.put("width", grid.getWidth());
            dungeon.put("height", grid.getHeight());
            dungeon.put("entities", grid.getJSON().getJSONArray("entities"));
        }

        if (goal != null) {
            dungeon.put("goal-condition", goal.getJSON());
        }

        if (gameMode != null) {
            dungeon.put("mode", gameMode.getJSON().getString("mode"));
        }

        return dungeon;
    }  
}
