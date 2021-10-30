package dungeonmania.goals;

import org.json.JSONObject;

import dungeonmania.Grid;
import dungeonmania.entities.Entity;
import dungeonmania.entities.collectable.Treasure;

/**
 * A goal for collecting all treasure in a dungeon.
 * 
 * @author Enoch Kavur (z5258204)
 */
public class CollectAllTreasureGoal implements ComponentGoal {
    private int n_treasure = -1; // flaged as not updated

	/**
     * @apiNote Only update the counters for treasure when isAchieved is 
     * called otherwise set -1 to mean uncounted. Reason to only count is after isAchieved 
     * is called is so that we do less computation each tick.
     * 
	 * @pre grid is a copy of the current grid state.
	 * @post returns true if player has collected all treasure in dungeon.
	 */
	@Override
	public boolean isAchieved(Grid grid) {

        n_treasure = 0;

		// Loop through each cell of the grid.
		for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {
				// Look for treasure and count them.
                for (Entity entity : grid.getEntities(x, y)) {
                    if (entity instanceof Treasure)  n_treasure++;
                }
            }
        }

		// Return true if there isn't any treasure left.
		return n_treasure == 0;
	}

	/**
     * 
     * @post return object as a JSONObject representation 
	 * {"goal": "treasure"}
     */
	@Override
	public JSONObject getJSON() {

		JSONObject goal = new JSONObject();
		
		goal.put("goal", "treasure");

		return goal;
	}

	/**
     * 
     * @post convert goal to string for frontend format.
     */
	@Override
	public String toString() {
        if (n_treasure == -1 ) return ":treasure";
        
        return String.format(":treasure(%d)", n_treasure);
	}
}
