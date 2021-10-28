package dungeonmania.goals;

import org.json.JSONObject;

/**
 * Interface for composite pattern with goals.
 * 
 * @author Enoch Kavur (z5258204)
 */
public interface ComponentGoal {
    
    /**
     * 
     * @post will return true if goal is achieved
     */
    public boolean isAchieved();

    /**
     * 
     * @post return object as a JSONObject representation
     */
    public JSONObject getJSON();

    /**
     * 
     * @post convert goal to string for frontend format.
     */
    public String toString();
}
