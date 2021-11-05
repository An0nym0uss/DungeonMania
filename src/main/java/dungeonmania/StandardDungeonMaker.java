package dungeonmania;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import dungeonmania.constants.Layer;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.StandardEntityFactory;
import dungeonmania.goals.*;
import dungeonmania.modes.*;
import dungeonmania.util.FileLoader;

/**
 * Responsible for making dungeon and grid.
 */
public class StandardDungeonMaker implements DungeonMaker {

    public static String RESOURCE_PATH = "src/main/resources/dungeons/";

    private EntityFactory entityFactory = null;

    /**
     * Creates dungeon.
     * 
     * @param dungeonName
     * @param gameMode
     * @param FileLoader 
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Dungeon createNewDungeon(String dungeonName, String gameMode) throws IllegalArgumentException {
        
        JsonObject dungeonData;

        // Try to open resource file and read in dungeon type json file.
        try {
            dungeonData = JsonParser.parseString( FileLoader.loadResourceFile("/dungeons/" + dungeonName + ".json")).getAsJsonObject();
        } catch (IOException e) {
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

        ComponentGoal goal = null;

        try {
            goal = createGoal(dungeonData.getAsJsonObject("goal-condition"));
        } catch (NullPointerException e) {
            goal = null;
        }

        // Setting dungeon's components
        dungeon.setGrid(createGrid(dungeonData.getAsJsonArray("entities"), height, width));
        dungeon.setGoal(goal);

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

        throw new IllegalArgumentException("game mode given doesn't exist");
    }

    @Override
    public Grid createGrid(JsonArray gridData, int height, int width) {
        Grid grid = new Grid(height, width, new Entity[width][height][Layer.LAYER_SIZE], null);

        Iterator<JsonElement> gridDataIter = gridData.iterator();

        while(gridDataIter.hasNext()) {
            Entity e = createEntity(gridDataIter.next().getAsJsonObject());
            grid.attach(e);
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
