package dungeonmania.entities;

import com.google.gson.JsonObject;

import dungeonmania.entities.statics.*;
import dungeonmania.util.Position;
import dungeonmania.entities.player.Player;

public class StandardEntityFactory implements EntityFactory {

    @Override
    public Entity createEntity(JsonObject entityData) {

        // Get Coordinates.
        int x = entityData.get("x").getAsInt();
        int y = entityData.get("y").getAsInt();

        String entityType = entityData.get("type").getAsString();

        // Static variables.
        if (entityType.equals("wall")) {
            return new Wall(new Position(x, y, 0));
        } else if (entityType.equals("exit")) {
            return new Exit(new Position(x, y, 0));
        } else if (entityType.equals("switch")) {
            return new FloorSwitch(new Position(x, y, 0));
        } else if (entityType.equals("boulder")) {
            return new Boulder(new Position(x, y, 2));
        }
        else if (entityType.equals("player")) {
            return new Player(new Position(x,y, 3), 10, 10);
        }

        return null;
    }
    
    
}
