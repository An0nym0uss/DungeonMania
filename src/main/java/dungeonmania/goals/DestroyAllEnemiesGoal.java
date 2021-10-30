package dungeonmania.goals;

import org.json.JSONObject;

import dungeonmania.Grid;
import dungeonmania.entities.Entity;
import dungeonmania.entities.enemy.Enemy;
import dungeonmania.entities.statics.Spawner;

/**
 * A goal for destroying all enemies and spawners in a dungeon.
 * 
 * @author Enoch Kavur (z5258204)
 */
public class DestroyAllEnemiesGoal implements ComponentGoal {

    private int n_enemies = -1; // flaged as not updated

	/**
     * @apiNote Only update the counters for enemy when isAchieved is 
     * called otherwise set -1 to mean uncounted. Reason to only count is after isAchieved 
     * is called is so that we do less computation each tick.
     * 
	 * @pre grid is a copy of the current grid state.
	 * @post returns true if player has destroyed all enemies and spawners have been destroyed.
	 */
	@Override
	public boolean isAchieved(Grid grid) {

        n_enemies = 0;

		// Loop through each cell of the grid.
		for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {
				// Look for a enemies and spawners and count them.
                for (Entity entity : grid.getEntities(x, y)) {
                    if (entity instanceof Enemy)  n_enemies++;
					else if (entity instanceof Spawner) n_enemies++;
                }
            }
        }

		// Return true if there aren't any enemies or spawners left.
		return n_enemies == 0;
	}

	/**
     * 
     * @post return object as a JSONObject representation 
	 * {"goal": "enemies"}
     */
	@Override
	public JSONObject getJSON() {

		JSONObject goal = new JSONObject();
		
		goal.put("goal", "enemies");

		return goal;
	}

	/**
     * 
     * @post convert goal to string for frontend format.
     */
	@Override
	public String toString() {
        if (n_enemies == -1 ) return ":enemy";
        
        return String.format(":enemy(%d)", n_enemies);
	}
}
