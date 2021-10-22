package dungeonmania.modes;

/**
 * Standard game rules.
 * @author Lachlan Kerr (z5118613)
 */
public class Standard implements Mode {

    private boolean canAttack;
    private int zombieSpawnRate;
    private int maxPlayerHealth;
    private boolean invincibilityEffect;

    public Standard()
    {
        canAttack = true;
        zombieSpawnRate = 20;
        maxPlayerHealth = 100;
        invincibilityEffect = true;
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
}