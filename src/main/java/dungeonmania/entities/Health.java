package dungeonmania.entities;

/**
 * An entity implements this interface if it has health and can receive damage and die.
 */
public interface Health {
    /**
     * Checks if the entity is dead (their health is <= 0).
     * @return True if dead, false if not.
     */
    public boolean isDead();

    /**
     * The entity receives this much damage to their health.
     * @param damage The damage this entity is to receive.
     */
    public void receiveDamage(int damage);
}
