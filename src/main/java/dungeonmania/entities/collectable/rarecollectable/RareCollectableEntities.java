package dungeonmania.entities.collectable.rarecollectable;

import dungeonmania.entities.collectable.CollectableEntity;
import dungeonmania.entities.player.Inventory;
import dungeonmania.util.Position;

import java.util.Random;

public class RareCollectableEntities extends CollectableEntity {

    private double dropRate;
    protected Random random;

    public RareCollectableEntities(String type, Position position, boolean isInteractable, double dropRate) {
        super(type, position, isInteractable);
        this.dropRate = dropRate;
        this.random = new Random();
    }

    public static void spawnnRareCollectableEntities(Inventory inventory) {
        RareCollectableEntities anduril = new Anduril();
        anduril.spawnnAnduril(inventory);
        RareCollectableEntities oneRing = new TheOneRing();
        oneRing.spawnnOneRing(inventory);
    }

    public void spawnnAnduril(Inventory inventory) {

    }

    public void spawnnOneRing(Inventory inventory) {
        
    }

    public double getDropRate() {
        return this.dropRate;
    }

    public void setDropRate(double dropRate) {
        this.dropRate = dropRate;
    }

    // seeding random not working, reason currently not found
    public void setRandom(long seed) {
        this.random.setSeed(seed);
    }
}
