package dungeonmania.entities.collectable;

public interface Durable {
    int getDurability();

    void setDurability(int durability);

    boolean isBroken();
}
