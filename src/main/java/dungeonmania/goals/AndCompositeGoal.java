package dungeonmania.goals;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

/**
 * @invarient sub goals never change.
 */
public class AndCompositeGoal implements ComponentGoal {

    private List<ComponentGoal> subgoals = new ArrayList<ComponentGoal>();   
    
    public void addSubgoal(ComponentGoal componentGoal) {
        subgoals.add(componentGoal);
    }

    public List<ComponentGoal> getSubgoals() {
        return subgoals;
    }

    @Override
    public boolean isAchieved() {

        return !subgoals.stream().filter(goal -> !goal.isAchieved()).findAny().isPresent();
    }

    @Override
    public JSONObject getJSON() {
        // TODO Auto-generated method stub
        return null;
    }

}