package dungeonmania.modes;

import org.json.JSONObject;

/**
 * Zombies spawn every 15 ticks.
 * The player has less health points to begin with (75).
 * Invincibility potions have no effect.
 * Hydras can spawn.
 * @author Lachlan Kerr (z5118613)
 */
public class Hard implements Mode {

    private boolean canAttack;
    private int zombieSpawnRate;
    private int maxPlayerHealth;
    private boolean invincibilityEffect;
    private boolean canHydra;

    public Hard()
    {
        canAttack = true;
        zombieSpawnRate = 15;
        maxPlayerHealth = 75;
        invincibilityEffect = false;
        canHydra = true;
    }

    @Override
    public boolean canAttackPlayer() {
        return canAttack;
    }

    @Override
    public int getZombieSpawnRate() {
        return zombieSpawnRate;
    }

    @Override
    public int getMaxPlayerHealth() {
        return maxPlayerHealth;
    }

    @Override
    public boolean canHaveInvincibilityEffect() {
        return invincibilityEffect;
    }

    @Override
    public boolean canSpawnHydra() {
        return canHydra;
    }

    @Override
    public JSONObject getJSON() {
        JSONObject mode = new JSONObject();

        mode.put("mode", "hard");
        
        return mode;
    }
}