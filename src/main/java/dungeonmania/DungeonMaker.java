package dungeonmania;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import dungeonmania.entities.Entity;
import dungeonmania.goals.ComponentGoal;
import dungeonmania.modes.Mode;

public interface DungeonMaker {
    
    public Dungeon createNewDungeon(String dungeonName, String gameMode) throws IllegalArgumentException;

    public Mode createGameMode(String gameMode) throws IllegalArgumentException;
    
    public Grid createGrid(JsonArray gridData, int height, int width);

    public ComponentGoal createGoal(JsonObject goalData);

    public Entity createEntity(JsonObject entityData);
}
