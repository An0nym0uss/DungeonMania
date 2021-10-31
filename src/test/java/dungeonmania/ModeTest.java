package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import dungeonmania.modes.*;

public class ModeTest {
    @Test
    /**
     * Tests all the properties of standard mode.
     */
    public void testModeStandard() {
        Mode mode = new Standard();

        assertTrue(mode.canAttackPlayer());
        assertTrue(mode.getZombieSpawnRate() == 20);
        assertTrue(mode.getMaxPlayerHealth() == 100);
        assertTrue(mode.canHaveInvincibilityEffect());
        assertTrue(mode.getJSON().has("mode"));
        assertEquals(mode.getJSON().get("mode"), "standard");
    }

    @Test
    /**
     * Tests all the properties of peaceful mode.
     */
    public void testModePeaceful() {
        Mode mode = new Peaceful();

        assertTrue(!mode.canAttackPlayer());
        assertTrue(mode.getZombieSpawnRate() == 20);
        assertTrue(mode.getMaxPlayerHealth() == 100);
        assertTrue(mode.canHaveInvincibilityEffect());
        assertTrue(mode.getJSON().has("mode"));
        assertEquals(mode.getJSON().get("mode"), "peaceful");
    }

    @Test
    /**
     * Tests all the properties of hard mode.
     */
    public void testModeHard() {
        Mode mode = new Hard();

        assertTrue(mode.canAttackPlayer());
        assertTrue(mode.getZombieSpawnRate() == 15);
        assertTrue(mode.getMaxPlayerHealth() == 75);
        assertTrue(!mode.canHaveInvincibilityEffect());
        assertTrue(mode.getJSON().has("mode"));
        assertEquals(mode.getJSON().get("mode"), "hard");
    }
}