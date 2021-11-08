package dungeonmania.entities;

import javax.sound.sampled.SourceDataLine;

import com.google.gson.JsonObject;

import dungeonmania.entities.statics.*;
import dungeonmania.modes.Mode;
import dungeonmania.util.Position;
import dungeonmania.constants.Layer;
import dungeonmania.entities.collectable.*;
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
            try {
                String colour = entityData.get("colour").getAsString();

                if (colour.equalsIgnoreCase("blue")) {
                    return new Portal("portal_blue", new Position(x, y, Layer.STATIC), colour);
                } else if (colour.equalsIgnoreCase("red")) {
                    return new Portal("portal_red", new Position(x, y, Layer.STATIC), colour);
                } else if (colour.equalsIgnoreCase("orange")) {
                    return new Portal("portal_orange", new Position(x, y, Layer.STATIC), colour);
                } else if (colour.equalsIgnoreCase("gray")) {
                    return new Portal("portal_gray", new Position(x, y, Layer.STATIC), colour);
                }
            // Set to blue if no colour specified.
            } catch (NullPointerException e) {
                return new Portal("portal_blue", new Position(x, y, Layer.STATIC), null);
            }
            
        // Zombie toast spawner TODO
        } else if (entityType.equalsIgnoreCase("zombie_toast_spawner")) {
            if (mode == null) throw new InternalError("tried to create a player without selecting a mode.");

            return new ZombieToastSpawner(new Position(x, y, Layer.STATIC), mode);
        // Door
        } else if (entityType.equalsIgnoreCase("door_locked_silver")) {
            int keyNumber = entityData.get("key").getAsInt();
            return new Door("door_locked_silver", new Position(x, y, Layer.STATIC), keyNumber, false);
        } else if (entityType.equalsIgnoreCase("door_unlocked")) {
            int keyNumber = entityData.get("key").getAsInt();
            return new Door("door_unlocked", new Position(x, y, Layer.STATIC), keyNumber, true);
        // Enemies TODO
        // Mercancy TODO
        } else if (entityType.equalsIgnoreCase("mercenary")) {
            return new Mercenary(new Position(x, y, Layer.ENEMY), 1, 1, 1);
        } else if (entityType.equalsIgnoreCase("spider")) {
            return new Spider(new Position(x, y, Layer.ENEMY), 1, 1, 1);
        } else if (entityType.equalsIgnoreCase("zombie_toast")) {
            return new Zombie(new Position(x, y, Layer.ENEMY), 1, 1, 1);
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
        } else if (entityType.equalsIgnoreCase("key_silver")) {
            int keyNumber = entityData.get("key").getAsInt();
            return new Key("key_silver", new Position(x, y, Layer.COLLECTABLE), keyNumber);
        } else if (entityType.equalsIgnoreCase("key_gold")) {
            int keyNumber = entityData.get("key").getAsInt();
            return new Key("key_gold", new Position(x, y, Layer.COLLECTABLE), keyNumber);
        } else if (entityType.equalsIgnoreCase("invincibility_potion")) {
            return new InvincibilityPotion(new Position(x, y, Layer.COLLECTABLE));
        } else if (entityType.equalsIgnoreCase("invisibility_potion")) {
            return new InvisibilityPotion(new Position(x, y, Layer.COLLECTABLE));
        } else if (entityType.equalsIgnoreCase("health_potion")) {
            return new HealthPotion(new Position(x, y, Layer.COLLECTABLE));
        } else if (entityType.equalsIgnoreCase("sun_stone")) {
            return new SunStone(new Position(x, y, Layer.COLLECTABLE));
        }

        else if (entityType.equalsIgnoreCase("player")) {

            if (mode == null) {throw new InternalError("tried to create a player without selecting a mode.");}

            return new Player(new Position(x,y, Layer.PLAYER), mode);
        }
        if (entityType != null) {
            System.out.println(entityType);
        }
        return null;
    }
    
    
}
