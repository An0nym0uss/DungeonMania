package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.ResponseFactory;
import dungeonmania.response.models.StandardResponseFactory;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

public class DungeonManiaController {

    Dungeon currentGame = null;
    DungeonMaker dungeonMaker = new StandardDungeonMaker();
    ResponseFactory dungeonResponseFactory = new StandardResponseFactory();


    public DungeonManiaController() {
    }

    public String getSkin() {
        return "default";
    }

    public String getLocalisation() {
        return "en_US";
    }

    public List<String> getGameModes() {
        return Arrays.asList("Standard", "Peaceful", "Hard");
    }

    /**
     * /dungeons
     * 
     * Done for you.
     */
    public static List<String> dungeons() {
        try {
            return FileLoader.listFileNamesInResourceDirectory("/dungeons");
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public DungeonResponse newGame(String dungeonName, String gameMode) throws IllegalArgumentException {
        currentGame = dungeonMaker.createNewDungeon(dungeonName, gameMode);
        return createDungeonResponse();
    }
    
    public DungeonResponse saveGame(String name) throws IllegalArgumentException {

        JSONObject gameData = currentGame.getJSON();

        try {
            // If you get an error, you need ot make a saves folder in resources
            File saves = new File(FileLoader.class.getResource("/").getPath() + "saves");
            if (!saves.exists()) {
                saves.mkdirs();
            }
            File file = new File(FileLoader.class.getResource("/saves").getPath() + "/" + name + ".json");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(gameData.toString());
            fileWriter.close();

        } catch (IOException e) {

            e.printStackTrace();

            throw new IllegalArgumentException();
        }

        return createDungeonResponse();
    }

    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        
        currentGame = dungeonMaker.loadDungeon(name);

        return createDungeonResponse();
    }

    public List<String> allGames() {
        
        try {
            return FileLoader.listFileNamesInResourceDirectory("saves");
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<String>();
        }
    }

    public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {

        currentGame.tick(itemUsed, movementDirection);

        return createDungeonResponse();
    }

    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        
        currentGame.interact(entityId);
        
        return createDungeonResponse();
    }

    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        
        currentGame.build(buildable);

        return createDungeonResponse();
    }

    public DungeonResponse generateDungeon(int xStart, int yStart, int xEnd, int yEnd, String gameMode) throws IllegalArgumentException {

        currentGame = dungeonMaker.generateRandomDungeon(xStart, yStart, xEnd, yEnd, gameMode);

        return createDungeonResponse();
    }

    private DungeonResponse createDungeonResponse() {

        return dungeonResponseFactory.createDungeonResponse(currentGame);
    }
}
