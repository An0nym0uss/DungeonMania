package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonObject;

import org.junit.jupiter.api.Test;

import dungeonmania.util.Direction;
import dungeonmania.DungeonManiaController;
import dungeonmania.entities.*;
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

        assertTrue(factory.createEntity(zombieSpawner) instanceof ZombieToastSpawner);



    }

    @Test
    public void testCreateStatic1() {
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

        assertTrue(factory.createEntity(zombieSpawner) instanceof ZombieToastSpawner);


        
    }
}
