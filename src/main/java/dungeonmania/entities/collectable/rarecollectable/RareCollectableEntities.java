package dungeonmania.entities.collectable.rarecollectable;

import dungeonmania.entities.collectable.CollectableEntity;
import dungeonmania.entities.player.Inventory;

import java.util.Random;


public abstract class RareCollectableEntities extends CollectableEntity {

    protected double dropRate;

    public void spawnnRareCollectableEntities(Inventory inventory) {
        Random random = new Random();
        int draw = random.nextInt(100);
        if (draw + 1 <= this.dropRate) {
            inventory.addItem(this);
        }
    }

    public double getDropRate() {
        return this.dropRate;
    }

    public void setDropRate(double dropRate) {
        this.dropRate = dropRate;
    }
}