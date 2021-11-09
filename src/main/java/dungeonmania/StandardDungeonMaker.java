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
            throw new IllegalArgumentException("filename {" + dungeonName +"} isn't formatted correctly");
        
        }
        // Create mode

        dungeonData.addProperty("mode", gameMode);
        dungeonData.addProperty("name", dungeonName);
        return generateDungeon(dungeonData);
    }

    @Override
    public Dungeon loadDungeon(String dungeonName) {
        
        JsonObject dungeonData;

        // Try to open resource file and read in dungeon type json file.
        try {
            dungeonData = JsonParser.parseString( FileLoader.loadResourceFile("/saves/" + dungeonName + ".json")).getAsJsonObject();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new IllegalArgumentException("filename {" + dungeonName +"} given doesn't exist");
        } catch (JsonParseException e) {
            System.err.println(e.getMessage());
            throw new IllegalArgumentException("filename {" + dungeonName +"} isn't formatted correctly");
        
        }

        return generateDungeon(dungeonData);
    }

    private Dungeon generateDungeon(JsonObject dungeonData) {
        
        Mode mode = createGameMode(dungeonData.get("mode").getAsString());


        this.entityFactory = new StandardEntityFactory(mode);
        
        // Create dungeon.
        Dungeon dungeon = new Dungeon(dungeonData.get("name").getAsString(), mode);

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

    @Override
    public Dungeon generateRandomDungeon(int xStart, int yStart, int xEnd, int yEnd, String gameMode)
            throws IllegalArgumentException {
        
        if (xStart < 0 || xStart >= 50) {
            throw new IllegalAccessError("xStart is out of bounds");
        } else if (yStart < 0 || yStart >= 50) {
            throw new IllegalAccessError("yStart is out of bounds");
        } else if (xEnd < 0 || xEnd >= 50) {
            throw new IllegalAccessError("xEnd is out of bounds");
        } else if (yEnd < 0 || yEnd >= 50) {
            throw new IllegalAccessError("yEnd is out of bounds");
        }

        xStart = (xStart-1)/2;
        xEnd = (xEnd)/2;
        yStart = (yStart-1)/2;
        yEnd = (yEnd-1)/2;
        

        Mode mode = createGameMode(gameMode);

        // Setting dungeon's components
        Dungeon dungeon = new Dungeon("generated", mode);
        dungeon.setGrid((new MazeGenerator(yStart*25+xStart, yEnd*25+xEnd, mode)).toGrid());
        
        JsonObject goal = new JsonObject();

        goal.addProperty("goal", "exit");
        dungeon.setGoal(createGoal(goal));

        return dungeon;
    }

}
