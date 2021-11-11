package dungeonmania.entities;

/**
 * An entity implements this interface if it can do damage to another entity.
 */
public interface Damage {
    /**
     * @return The amount of damage the entity can deal to another entity.
     */
    public int damageDealt();
}
