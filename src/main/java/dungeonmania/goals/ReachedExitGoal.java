package dungeonmania.goals;

import org.json.JSONObject;

import dungeonmania.Grid;

/**
 * A goal for reaching the exit of a dungeon.
 * 
 * @author Enoch Kavur (z5258204)
 * 
 * @invarient grid is always a reference that current dungeon.
 */
public class ReachedExitGoal implements ComponentGoal {

	private Grid grid;

	public ReachedExitGoal(Grid grid) {
		this.grid = grid;
	}

	@Override
	public boolean isAchieved() {

		return false;
	}

	@Override
	public JSONObject getJSON() {

		JSONObject goal = new JSONObject();
		
		goal.put("goal", "exit");

		return goal;
	}
}