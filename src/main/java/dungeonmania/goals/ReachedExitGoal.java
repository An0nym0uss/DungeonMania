package dungeonmania.goals;

import org.json.JSONObject;

import dungeonmania.Grid;
import dungeonmania.entities.Entity;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.statics.Exit;

/**
 * A goal for reaching the exit of a dungeon.
 * 
 * @author Enoch Kavur (z5258204)
 * 
 */
public class ReachedExitGoal implements ComponentGoal {

	/**
	 * 
	 * @pre grid is a copy of the current grid state.
	 * @post returns true if player has reached the exit.
	 */
	@Override
	public boolean isAchieved(Grid grid) {

		// Loop through each cell of the grid.
		for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {
				boolean hasPlayer = false;
				boolean hasExit = false;

				// Look for a player and an exit being in the same cell.
                for (Entity entity : grid.getEntities(x, y)) {
                    if (entity instanceof Player) hasPlayer = true;
					else if (entity instanceof Exit) hasExit = true;
                }

				// Return true if there is both player and exit occupying the same cell.
				if (hasPlayer && hasExit) return true;
            }
        }

		// Return false if there is not a player occupying the same cell as an exit.
		return false;
	}

	/**
     * 
     * @post return object as a JSONObject representation 
	 * {"goal": "exit"}
     */
	@Override
	public JSONObject getJSON() {

		JSONObject goal = new JSONObject();
		
		goal.put("goal", "exit");

		return goal;
	}

	/**
     * 
     * @post convert goal to string for frontend format.
     */
	@Override
	public String toString() {
		
		return ":exit";
	}
}