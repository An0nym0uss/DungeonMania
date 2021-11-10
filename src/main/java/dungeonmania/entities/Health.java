package dungeonmania.entities;

/**
 * An entity implements this interface if it has health and can receive damage and die.
 */
public interface Health {
    boolean isDead();

    /**
     * The entity receives this much damage to their health.
     * @param damage The damage this entity is to receive.
     * @pre damage > 0
     * @post health >= 0
     */
    public void receiveDamage(int damage);
}
