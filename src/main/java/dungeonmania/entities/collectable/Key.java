package dungeonmania.entities.collectable;

import dungeonmania.util.Position;

public class Key extends CollectableEntity{
    private int keyNumber;

    public Key(String type, Position position, int keyNumber) {
        super(type, position, false);
        this.keyNumber = keyNumber;
    }

    public int getKeyNumber() {
        return this.keyNumber;
    }
}

