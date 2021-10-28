package dungeonmania;

import com.google.gson.JsonObject;

import dungeonmania.entities.Entity;
import dungeonmania.goals.ComponentGoal;

public interface DungeonMaker {
    
    public Dungeon createNewDungeon(String dungeonName, String gameMode) throws IllegalArgumentException;

    public ComponentGoal createGoal(JsonObject goalData);

    public Entity createEntity(JsonObject entityData);
}
