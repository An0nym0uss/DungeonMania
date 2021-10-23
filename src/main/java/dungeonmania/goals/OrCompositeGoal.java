package dungeonmania.goals;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Enoch Kavur (z5258204)
 * 
 * @invarient sub goals never change.
 */

public class OrCompositeGoal implements ComponentGoal {

    private List<ComponentGoal> subgoals = new ArrayList<ComponentGoal>();   

    /**
     * 
     * @param componentGoal is subgoal to be added
     * @pre componentGoal is not null
     */
    public void addSubgoal(ComponentGoal componentGoal) {
        subgoals.add(componentGoal);
    }

    /**
     * 
     * @post returns list of subgoals.
     */
    public List<ComponentGoal> getSubgoals() {
        return subgoals;
    }

    /**
     * 
     * @post will return true if any subgoal is achieved.
     */
    @Override
    public boolean isAchieved() {

        return subgoals.stream().filter(goal -> goal.isAchieved()).findAny().isPresent();
    }

    /**
     * 
     * @post return JSON Object with an array of subgoals in the form 
     * {"goal": "OR", "subgoals" : [...]}
     */
    @Override
    public JSONObject getJSON() {
        
        JSONObject goal = new JSONObject();
        JSONArray subgoals = new JSONArray();
 
        this.subgoals.stream().forEach(g -> subgoals.put(g.getJSON()));

        goal.put("goal", "AND");
        goal.put("subgoals", subgoals);

        return goal;
    }

}