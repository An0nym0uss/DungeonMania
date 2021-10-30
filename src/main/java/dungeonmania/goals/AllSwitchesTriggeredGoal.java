package dungeonmania.goals;

import org.json.JSONObject;

import dungeonmania.Grid;
import dungeonmania.entities.Entity;
import dungeonmania.entities.statics.Boulder;
import dungeonmania.entities.statics.FloorSwitch;

/**
 * A goal for triggering all the switches in a dungeon.
 * 
 * @author Enoch Kavur (z5258204)
 * 
 */
public class AllSwitchesTriggeredGoal implements ComponentGoal {

    private int n_switches = -1; // flaged as not updated
    private int n_boulders = -1;

	/**
     * @apiNote Only update the counters for switches and boulders when isAchieved is 
     * called otherwise set -1 to mean uncounted. Reason to only count is after isAchieved 
    * is called is so that we do less computation each tick.
     * 
	 * @post returns true if player has triggered all the floor switches in the dungeon.
	 */
	@Override
	public boolean isAchieved(Grid grid) {

        n_switches = 0;
        n_boulders = 0;

		// Loop through each cell of the grid.
		for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {
				boolean hasSwitch = false;
				boolean hasBoulder = false;

				// Look for a floor switch and a boulder being in the same cell.
                for (Entity entity : grid.getEntities(x, y)) {
                    if (entity instanceof FloorSwitch) {hasSwitch = true; n_switches++;}
					else if (entity instanceof Boulder) {hasBoulder = true; n_boulders++;}
                }

				// Return false if there is floor switch without a boulder on it
				if (hasSwitch && !hasBoulder) return false;
            }
        }

		// Return true if there doesn't exist a floor switch without a boulder.
		return true;
	}

	/**
	 * 
     * @pre grid is a copy of the current grid state.
     * @post return object as a JSONObject representation 
	 * {"goal": "switch"}
     */
	@Override
	public JSONObject getJSON() {

		JSONObject goal = new JSONObject();
		
		goal.put("goal", "boulders");

		return goal;
	}

	/**
     * 
     * @post convert goal to string for frontend format.
     */
	@Override
	public String toString() {
        if (n_switches == -1 || n_boulders == -1) return ":boulder";
        
        return String.format(":boulder(%d)/:switch(%d)", n_boulders, n_switches);
	}
}