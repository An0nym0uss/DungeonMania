package dungeonmania.entities;

import java.util.Map;

import java.util.HashMap;

import com.google.gson.JsonObject;

import dungeonmania.entities.statics.*;
import dungeonmania.modes.Mode;
import dungeonmania.util.Position;
import dungeonmania.entities.enemy.*;
import dungeonmania.entities.player.Player;

/**
 * @author Enoch Kavur (z5258204)
 */
public class StandardEntityFactory implements EntityFactory {

    private Mode mode = null;

    public StandardEntityFactory() {
        // Exits so you can have a null mode;
    }

    public StandardEntityFactory(Mode mode) {
        this.mode = mode;
    }

    @Override
    public Entity createEntity(JsonObject entityData) {

        // Get Coordinates.
        int x = entityData.get("x").getAsInt();
        int y = entityData.get("y").getAsInt();

        String entityType = entityData.get("type").getAsString();

        // Static variables.
        // Wall
        if (entityType.equalsIgnoreCase("wall")) {
            return new Wall(new Position(x, y, 0));
        // Exit
        } else if (entityType.equalsIgnoreCase("exit")) {
            return new Exit(new Position(x, y, 0));
        // Switch
        } else if (entityType.equalsIgnoreCase("switch")) {
            return new FloorSwitch(new Position(x, y, 0));
        // Boulder
        } else if (entityType.equalsIgnoreCase("boulder")) {
            return new Boulder(new Position(x, y, 2));
        // Portal
        } else if (entityType.equalsIgnoreCase("portal")) {

            String colour = entityData.get("colour").getAsString();

            return new Portal(new Position(x, y, 2), colour);
        // Zombie toast spawner TODO
        } else if (entityType.equalsIgnoreCase("zombietoastspawner")) {
            return new ZombieToastSpawner(new Position(x, y, 0));
        // Door TODO
        } else if (entityType.equalsIgnoreCase("door")) {
            return new Door(new Position(x, y, 2), null, false);
        // Enemies TODO
        // Mercancy TODO
        } else if (entityType.equalsIgnoreCase("mercenary")) {
        // Spider TODO
        } else if (entityType.equalsIgnoreCase("spider")) {
        // zombie TODO
        } else if (entityType.equalsIgnoreCase("zombie")) {
        // Collectables
        }


        else if (entityType.equalsIgnoreCase("player")) {

            if (mode == null) throw new InternalError("tried to create a player without selecting a mode.");

            return new Player(new Position(x,y, 3), mode, 10);
        }

        return null;
    }
    
    
}
