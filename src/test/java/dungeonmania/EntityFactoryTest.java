package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.google.gson.JsonObject;

import org.junit.jupiter.api.Test;

//import collectable.*;
import dungeonmania.entities.*;
import dungeonmania.entities.collectable.Armour;
import dungeonmania.entities.collectable.HealthPotion;
import dungeonmania.entities.collectable.InvisibilityPotion;
import dungeonmania.entities.collectable.Sword;
import dungeonmania.entities.enemy.*;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.statics.*;
import dungeonmania.modes.Peaceful;


public class EntityFactoryTest {
    @Test
    public void testInvalidInputWithNoModeSet() {
        EntityFactory factory = new StandardEntityFactory();

        JsonObject player = new JsonObject();

        player.addProperty("x", 0);
        player.addProperty("y", 0);
        player.addProperty("type", "player");

        // try create player
        assertThrows(InternalError.class, () -> {
            factory.createEntity(player);
            }
        );

        JsonObject zombieSpawner = new JsonObject();

        zombieSpawner.addProperty("x", 0);
        zombieSpawner.addProperty("y", 0);
        zombieSpawner.addProperty("type", "zombie_toast_spawner");

        // try create zombie toast spawner
        assertThrows(InternalError.class, () -> {
            factory.createEntity(zombieSpawner);
            }
        );

    }

    @Test
    public void testCreatePlayer() {
        EntityFactory factory = new StandardEntityFactory(new Peaceful());

        JsonObject player = new JsonObject();

        player.addProperty("x", 0);
        player.addProperty("y", 0);
        player.addProperty("type", "player");

        // try create player
        assertTrue(factory.createEntity(player) instanceof Player);
    }

    @Test
    public void testCreateStatic() {
        EntityFactory factory = new StandardEntityFactory(new Peaceful());

        JsonObject wall = new JsonObject();

        wall.addProperty("x", 0);
        wall.addProperty("y", 0);
        wall.addProperty("type", "wall");

        // try create wall
        assertTrue(factory.createEntity(wall) instanceof Wall);

        JsonObject boulder = new JsonObject();

        boulder.addProperty("x", 0);
        boulder.addProperty("y", 0);
        boulder.addProperty("type", "boulder");

        // try create boulder
        assertTrue(factory.createEntity(boulder) instanceof Boulder);

        JsonObject zombieSpawner = new JsonObject();

        zombieSpawner.addProperty("x", 0);
        zombieSpawner.addProperty("y", 0);
        zombieSpawner.addProperty("type", "zombie_toast_spawner");

        // try create zombieSpawner
        assertTrue(factory.createEntity(zombieSpawner) instanceof ZombieToastSpawner);
    }

    @Test
    public void testCreateEnemy() {
        EntityFactory factory = new StandardEntityFactory(new Peaceful());

        JsonObject mercenary = new JsonObject();

        mercenary.addProperty("x", 0);
        mercenary.addProperty("y", 0);
        mercenary.addProperty("type", "mercenary");

        // try create mercenary
        assertTrue(factory.createEntity(mercenary) instanceof Mercenary);

        JsonObject zombie = new JsonObject();

        zombie.addProperty("x", 0);
        zombie.addProperty("y", 0);
        zombie.addProperty("type", "zombie_toast");

        // try create zombie
        assertTrue(factory.createEntity(zombie) instanceof Zombie);
    }

    @Test
    public void testNonExistingEntity() {

        EntityFactory factory = new StandardEntityFactory(new Peaceful());

        JsonObject creeper = new JsonObject();

        creeper.addProperty("x", 0);
        creeper.addProperty("y", 0);
        creeper.addProperty("type", "creeper");

        assertNull(factory.createEntity(creeper));

    }

    @Test
    public void testCreateCollectables() {
        EntityFactory factory = new StandardEntityFactory(new Peaceful());

        JsonObject sword = new JsonObject();

        sword.addProperty("x", 0);
        sword.addProperty("y", 0);
        sword.addProperty("type", "sword");

        // try create sword
        assertTrue(factory.createEntity(sword) instanceof Sword);

        JsonObject armour = new JsonObject();

        armour.addProperty("x", 0);
        armour.addProperty("y", 0);
        armour.addProperty("type", "armour");

        // try create armour
        assertTrue(factory.createEntity(armour) instanceof Armour);

        JsonObject invisibility_potion = new JsonObject();

        invisibility_potion.addProperty("x", 0);
        invisibility_potion.addProperty("y", 0);
        invisibility_potion.addProperty("type", "invisibility_potion");

        // try create invisibility_potion
        assertTrue(factory.createEntity(invisibility_potion) instanceof InvisibilityPotion);

        JsonObject health_potion = new JsonObject();

        health_potion.addProperty("x", 0);
        health_potion.addProperty("y", 0);
        health_potion.addProperty("type", "health_potion");

        // try create health_potion
        assertTrue(factory.createEntity(health_potion) instanceof HealthPotion);
    }

}
