package dungeonmania.entities;

import java.util.Iterator;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dungeonmania.entities.statics.*;
import dungeonmania.modes.Mode;
import dungeonmania.util.Position;
import dungeonmania.constants.Layer;
import dungeonmania.entities.collectable.Armour;
import dungeonmania.entities.collectable.Arrow;
import dungeonmania.entities.collectable.Bomb;
import dungeonmania.entities.collectable.CollectableEntity;
import dungeonmania.entities.collectable.HealthPotion;
import dungeonmania.entities.collectable.InvincibilityPotion;
import dungeonmania.entities.collectable.InvisibilityPotion;
import dungeonmania.entities.collectable.Key;
import dungeonmania.entities.collectable.SunStone;
import dungeonmania.entities.collectable.Sword;
import dungeonmania.entities.collectable.TimeTurner;
import dungeonmania.entities.collectable.Treasure;
import dungeonmania.entities.collectable.Wood;
import dungeonmania.entities.collectable.buildable.Bow;
import dungeonmania.entities.collectable.buildable.MidnightArmour;
import dungeonmania.entities.collectable.buildable.Sceptre;
import dungeonmania.entities.collectable.buildable.Shield;
import dungeonmania.entities.collectable.rarecollectable.*;
import dungeonmania.entities.enemy.*;
import dungeonmania.entities.player.Inventory;
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
                e.printStackTrace();
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
        // Swamp tile
        } else if (entityType.equalsIgnoreCase("swamp_tile")) {
            int movementFactor = entityData.get("movement_factor").getAsInt();
            return new SwampTile(new Position(x, y, Layer.STATIC), movementFactor);
        // Enemies TODO
        // Mercancy TODO
        } else if (entityType.equalsIgnoreCase("mercenary")) {

            try {
                int speed = entityData.get("speed").getAsInt();
                int health = entityData.get("health").getAsInt();
                int damage = entityData.get("damage").getAsInt();
                int max_health = entityData.get("max_health").getAsInt();
                boolean bribed = entityData.get("bribed").getAsBoolean();
                int mindcontrolDuration = entityData.get("mindcontrolDuration").getAsInt();

                Mercenary mercenary = new Mercenary(new Position(x, y, Layer.ENEMY), speed, health, damage);

                mercenary.setMaxHealth(max_health);
                mercenary.setBribed(bribed);
                mercenary.setMindcontrolDuration(mindcontrolDuration);

                return mercenary;

            } catch (NullPointerException e) {
                e.printStackTrace();
                Random rand = new Random();

                double prob = rand.nextDouble();
                if (prob < 0.3) {
                    return new Assassin(new Position(x, y, Layer.ENEMY), 1,100,3);
                } else {
                    return new Mercenary(new Position(x, y, Layer.ENEMY), 1, 50, 2);
                }
            }


        } else if (entityType.equalsIgnoreCase("assassin")) {
            try {
                int speed = entityData.get("speed").getAsInt();
                int health = entityData.get("health").getAsInt();
                int damage = entityData.get("damage").getAsInt();
                int max_health = entityData.get("max_health").getAsInt();
                boolean bribed = entityData.get("bribed").getAsBoolean();
                int mindcontrolDuration = entityData.get("mindcontrolDuration").getAsInt();

                Assassin assassin = new Assassin(new Position(x, y, Layer.ENEMY), speed, health, damage);

                assassin.setMaxHealth(max_health);
                assassin.setBribed(bribed);
                assassin.setMindcontrolDuration(mindcontrolDuration);

                return assassin;

            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            return new Assassin(new Position(x, y, Layer.ENEMY), 1, 100, 3);
        } else if (entityType.equalsIgnoreCase("spider")) {

            try {
                int speed = entityData.get("speed").getAsInt();
                int health = entityData.get("health").getAsInt();
                int damage = entityData.get("damage").getAsInt();
                int max_health = entityData.get("max_health").getAsInt();

                Enemy spider = new Spider(new Position(x, y, Layer.ENEMY), speed, health, damage);

                spider.setMaxHealth(max_health);

                return spider;

            } catch (NullPointerException e) {
                e.printStackTrace();
            }


            return new Spider(new Position(x, y, Layer.SPIDER), 1, 50, 1);
        } else if (entityType.equalsIgnoreCase("zombie_toast")) {

            try {
                int speed = entityData.get("speed").getAsInt();
                int health = entityData.get("health").getAsInt();
                int damage = entityData.get("damage").getAsInt();
                int max_health = entityData.get("max_health").getAsInt();

                Enemy zombie = new Zombie(new Position(x, y, Layer.ENEMY), speed, health, damage);

                zombie.setMaxHealth(max_health);

                return zombie;

            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            return new Zombie(new Position(x, y, Layer.ENEMY), 1, 100, 1);
        // Collectables
        } else if (entityType.equalsIgnoreCase("bomb")) {
            return new Bomb(new Position(x, y, Layer.COLLECTABLE));
        } else if (entityType.equalsIgnoreCase("treasure")) {
            return new Treasure(new Position(x, y, Layer.COLLECTABLE));
        } else if (entityType.equalsIgnoreCase("wood")) {
            return new Wood(new Position(x, y, Layer.COLLECTABLE));
        } else if (entityType.equalsIgnoreCase("arrow")) {
            return new Arrow(new Position(x, y, Layer.COLLECTABLE));
        // Sword
        } else if (entityType.equalsIgnoreCase("sword")) {

            Sword sword = new Sword(new Position(x, y, Layer.COLLECTABLE));

            try {
                int durability = entityData.get("durability").getAsInt();

                sword.setDurability(durability);

            } catch (NullPointerException e) {
                //e.printStackTrace();
            }

            return sword;

        // Armour
        } else if (entityType.equalsIgnoreCase("armour")) {
            Armour armour = new Armour(new Position(x, y, Layer.COLLECTABLE));

            
            try {
                int durability = entityData.get("durability").getAsInt();

                armour.setDurability(durability);

            } catch (NullPointerException e) {
                //e.printStackTrace();
            }

            return armour;

        // Bow
        } else if (entityType.equalsIgnoreCase("bow")) {
            // Since buildable can assume always has a durability.
            return new Bow(new Position(x, y, Layer.COLLECTABLE), entityData.get("durability").getAsInt());
        } else if (entityType.equalsIgnoreCase("shield")) {
            // Since buildable can assume always has a durability.
            return new Shield(new Position(x, y, Layer.COLLECTABLE), entityData.get("durability").getAsInt(), 10);
        } else if (entityType.equalsIgnoreCase("midnight_armour")) {
            return new MidnightArmour(new Position(x, y, Layer.COLLECTABLE));
        } else if (entityType.equalsIgnoreCase("sceptre")) {
            return new Sceptre(new Position(x, y, Layer.COLLECTABLE));
        } else if (entityType.equalsIgnoreCase("one_ring")) {
            return new TheOneRing(new Position(x, y, Layer.COLLECTABLE));
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
        } else if (entityType.equalsIgnoreCase("anduril")) {
            return new Anduril(new Position(x, y, Layer.COLLECTABLE));
        } else if (entityType.equalsIgnoreCase("time_turner")) {
            return new TimeTurner(new Position(x, y, Layer.COLLECTABLE));
        } else if (entityType.equalsIgnoreCase("time_travelling_portal")) {
            return new TimeTravellingPortal(new Position(x, y, Layer.STATIC));
        }

        else if (entityType.equalsIgnoreCase("player")) {

            if (mode == null) {throw new InternalError("tried to create a player without selecting a mode.");}

            Inventory inventory = new Inventory();

            // Try get inventory from json file
            try {
                JsonArray JsonInvent = entityData.get("inventory").getAsJsonArray();

                Iterator<JsonElement> itemIter = JsonInvent.iterator();

                while(itemIter.hasNext()) {
                    // Should only return item entity.
                    CollectableEntity e = (CollectableEntity) createEntity(itemIter.next().getAsJsonObject());

                    inventory.addItem(e);
                }
            } catch (NullPointerException e) {
                // No inventory saved.
            }

            Player player = new Player(new Position(x,y, Layer.PLAYER), mode);

            player.setInventory(inventory);

            return player;

        }
        if (entityType != null) {
            System.out.println(entityType);
        }
        return null;
    }
    
    
}
