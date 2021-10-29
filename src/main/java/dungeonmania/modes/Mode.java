package dungeonmania.modes;

import dungeonmania.GameToJSON;

/**
 * An interface containing required methods for each game mode.
 * @author Lachlan Kerr (z5118613)
 */
public interface Mode extends GameToJSON {
    
    /**
     * Can an enemy attack the player character.
     * @return False if peaceful, otherwise true.
     */
    public boolean canAttackPlayer();

    /**
     * The amount of ticks between spawns from a zombie toast spawner.
     * @return 15 if hard, otherwise 20.
     */
    public int getZombieSpawnRate();

    /**
     * How much health the player starts with, its maximum value.
     * @return 75 if hard, otherwise 100.
     */
    public int getMaxPlayerHealth();

    /**
     * When using an invincibility potion, does it have any effect.
     * @return False if hard, otherwise true.
     */
    public boolean canHaveInvincibilityEffect();
}