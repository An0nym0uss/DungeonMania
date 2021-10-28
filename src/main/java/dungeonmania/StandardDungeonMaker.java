package dungeonmania;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;


import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.StandardEntityFactory;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.statics.Exit;
import dungeonmania.entities.statics.Wall;
import dungeonmania.goals.*;
import dungeonmania.modes.*;
import dungeonmania.util.Position;

/**
 * Responsible for making dungeon and grid.
 */
public class StandardDungeonMaker implements DungeonMaker {

    public static final String RESOURCE_PATH = "src\\main\\resources\\dungeons\\";

    private EntityFactory entityFactory = null;

    /**
     * Creates dungeon.
     * 
     * @param dungeonName
     * @param gameMode
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Dungeon createNewDungeon(String dungeonName, String gameMode) throws IllegalArgumentException {
        
        JsonObject dungeonData;

        // Try to open resource file and read in dungeon type json file.
        try {
            dungeonData = JsonParser.parseReader(new FileReader(RESOURCE_PATH + dungeonName + ".json")).getAsJsonObject();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            throw new IllegalArgumentException("dungeon name {" + dungeonName +"} given doesn't exist");
        } catch (JsonParseException e) {
            System.err.println(e.getMessage());
            throw new IllegalArgumentException("dungeon name {" + dungeonName + "} given doesn't exist");
        
        }

        // Create mode
        Mode mode = createGameMode(gameMode);

        this.entityFactory = new StandardEntityFactory(mode);
        
        // Create dungeon.
        Dungeon dungeon = new Dungeon(dungeonName, mode);

        // Getting Dimensions for creating grid.
        int height;

        try {
            height = dungeonData.get("height").getAsInt();
        } catch (NullPointerException e) {
            height = 20;
        }


        int width;

        try {
            width = dungeonData.get("width").getAsInt();
        } catch (NullPointerException e) {
            width = 20;
        }

        // Setting dungeon's components
        dungeon.setGrid(createGrid(dungeonData.getAsJsonArray("entities"), height, width));
        dungeon.setGoal(createGoal(dungeonData.getAsJsonObject("goal-condition")));

        return dungeon;
    }

    @Override
    public Mode createGameMode(String gameMode) throws IllegalArgumentException {

        if (gameMode.equalsIgnoreCase("peaceful")) {
            return new Peaceful();
        } else if (gameMode.equalsIgnoreCase("standard")) {
            return new Standard();
        } else if (gameMode.equalsIgnoreCase("hard")) {
            return new Hard();
        }

        throw new IllegalAccessError("IllegalAccessError: game mode given doesn't exist");
    }

    @Override
    public Grid createGrid(JsonArray gridData, int height, int width) {
        Grid grid = new Grid(height, width, new Entity[width][height][4], null);

        Iterator<JsonElement> gridDataIter = gridData.iterator();

        while(gridDataIter.hasNext()) {
            grid.attach(createEntity(gridDataIter.next().getAsJsonObject()));
        }
        return grid;
    }

    @Override
    public Entity createEntity(JsonObject entityData) {
        return entityFactory.createEntity(entityData);
    }

    /**
     * @apiNote returns null if doesn't goal type in json file doesn't match to pre-existing ones.
     * This is purely for extension reasons.
     */
    public ComponentGoal createGoal(JsonObject goalData) {

        String goalType = goalData.get("goal").getAsString();

        if (goalType.equalsIgnoreCase("AND")) {

            Iterator<JsonElement> subgoals = goalData.getAsJsonArray("subgoals").iterator();
            AndCompositeGoal goal = new AndCompositeGoal();
            while(subgoals.hasNext()) {
                goal.addSubgoal(createGoal(subgoals.next().getAsJsonObject()));
            }

            return goal;

        } else if (goalType.equalsIgnoreCase("OR")) {

            Iterator<JsonElement> subgoals = goalData.getAsJsonArray("subgoals").iterator();
            OrCompositeGoal goal = new OrCompositeGoal();
            while(subgoals.hasNext()) {
                goal.addSubgoal(createGoal(subgoals.next().getAsJsonObject()));
            }

            return goal;

        } else if (goalType.equalsIgnoreCase("enemies")) {
            return new DestroyAllEnemiesGoal();
        } else if (goalType.equalsIgnoreCase("treasure")) {
            return new CollectAllTreasureGoal();
        } else if (goalType.equalsIgnoreCase("boulders")) {
            return new AllSwitchesTriggeredGoal();
        } else if (goalType.equalsIgnoreCase("exit")) {
            return new ReachedExitGoal();
        }

        return null;
    }
}
