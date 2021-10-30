package dungeonmania.entities;

import java.util.Map;

import java.util.HashMap;

import com.google.gson.JsonObject;

import dungeonmania.entities.statics.*;
import dungeonmania.modes.Mode;
import dungeonmania.util.Position;
import dungeonmania.constants.Layer;
import dungeonmania.entities.collectable.Armour;
import dungeonmania.entities.collectable.Arrow;
import dungeonmania.entities.collectable.Bomb;
import dungeonmania.entities.collectable.Sword;
import dungeonmania.entities.collectable.Treasure;
import dungeonmania.entities.collectable.Wood;
import dungeonmania.entities.collectable.buildable.Shield;
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
            return new Wall(new Position(x, y, Layer.STATIC));
        // Exit
        } else if (entityType.equalsIgnoreCase("exit")) {
            return new Exit(new Position(x, y, Layer.STATIC));
        // Switch
        } else if (entityType.equalsIgnoreCase("switch")) {
            return new FloorSwitch(new Position(x, y, Layer.STATIC));
        // Boulder
        } else if (entityType.equalsIgnoreCase("boulder")) {
            return new Boulder(new Position(x, y, Layer.ENEMY));
        // Portal
        } else if (entityType.equalsIgnoreCase("portal")) {

            String colour = entityData.get("colour").getAsString();

            return new Portal(new Position(x, y, Layer.STATIC), colour);
        // Zombie toast spawner TODO
        } else if (entityType.equalsIgnoreCase("zombietoastspawner")) {
            return new ZombieToastSpawner(new Position(x, y, Layer.STATIC));
        // Door TODO
        } else if (entityType.equalsIgnoreCase("door")) {
            return new Door(new Position(x, y, Layer.STATIC), null, false);
        // Enemies TODO
        // Mercancy TODO
        } else if (entityType.equalsIgnoreCase("mercenary")) {
        // Spider TODO
        } else if (entityType.equalsIgnoreCase("spider")) {
        // zombie TODO
        } else if (entityType.equalsIgnoreCase("zombie")) {
        // Collectables
        } else if (entityType.equalsIgnoreCase("bomb")) {
            return new Bomb(new Position(x, y, Layer.COLLECTABLE));
        } else if (entityType.equalsIgnoreCase("treasure")) {
            return new Treasure(new Position(x, y, Layer.COLLECTABLE));
        } else if (entityType.equalsIgnoreCase("wood")) {
            return new Wood(new Position(x, y, Layer.COLLECTABLE));
        } else if (entityType.equalsIgnoreCase("arrow")) {
            return new Arrow(new Position(x, y, Layer.COLLECTABLE));
        } else if (entityType.equalsIgnoreCase("sword")) {
            return new Sword(new Position(x, y, Layer.COLLECTABLE));
        } else if (entityType.equalsIgnoreCase("armour")) {
            return new Armour(new Position(x, y, Layer.COLLECTABLE));
        } 


        else if (entityType.equalsIgnoreCase("player")) {

            if (mode == null) throw new InternalError("tried to create a player without selecting a mode.");

            return new Player(new Position(x,y, Layer.PLAYER), mode, 10);
        }

        return null;
    }
    
    
}
