package dungeonmania.goals;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.Grid;

/**
 * @author Enoch Kavur (z5258204)
 * 
 * @invariant sub goals never change.
 */

public class OrCompositeGoal implements ComponentGoal {

    private List<ComponentGoal> subgoals = new ArrayList<ComponentGoal>();   

    /**
     * 
     * @param componentGoal is subgoal to be added.
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
     * @pre grid is a copy of the current grid state.
     * @post will return true if any subgoal is achieved.
     */
    @Override
    public boolean isAchieved(Grid grid) {

        return subgoals.stream().filter(goal -> goal.isAchieved(grid)).findAny().isPresent();
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

        goal.put("goal", "OR");
        goal.put("subgoals", subgoals);

        return goal;
    }

    /**
     * 
     * @post convert goal to string for frontend format.
     */
    @Override
    public String toString() {

        // Would just be smarter to have an interface for leaf and composite, bad code right here.
        Function<ComponentGoal, String> toString =
            (subgoal) -> {
                if (subgoal instanceof OrCompositeGoal || subgoal instanceof AndCompositeGoal){
                    return "(" + subgoal.toString() + ")";
                } else {
                    return subgoal.toString();
                }
        };
        
        return String.join(" OR ", getSubgoals().stream().map(toString).collect(Collectors.toList()));
    }

}