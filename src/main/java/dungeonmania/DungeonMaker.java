package dungeonmania;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import dungeonmania.entities.Entity;
import dungeonmania.goals.ComponentGoal;
import dungeonmania.modes.Mode;

public interface DungeonMaker {

    public Dungeon generateRandomDungeon(int xStart, int yStart, int xEnd, int yEnd, String gameMode) throws IllegalArgumentException;
    
    public Dungeon createNewDungeon(String dungeonName, String gameMode) throws IllegalArgumentException;

    public Dungeon loadDungeon(String dungeonName);

    public Mode createGameMode(String gameMode) throws IllegalArgumentException;
    
    public Grid createGrid(JsonArray gridData, int height, int width);

    public ComponentGoal createGoal(JsonObject goalData);

    public Entity createEntity(JsonObject entityData);
}
