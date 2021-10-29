package dungeonmania.entities.collectable.rarecollectable;

import dungeonmania.entities.collectable.CollectableEntity;
import dungeonmania.entities.player.Inventory;
import dungeonmania.util.Position;

import java.util.Random;


public abstract class RareCollectableEntities extends CollectableEntity {

    public RareCollectableEntities(String type, Position position, boolean isInteractable) {
        super(type, position, isInteractable);
    }

    private double dropRate;
    

    public RareCollectableEntities(String type, Position position, boolean isInteractable, double dropRate) {
        super(type, position, isInteractable);
        this.dropRate = dropRate;
    }

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