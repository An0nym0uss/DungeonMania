package dungeonmania.entities.collectable;

import org.json.JSONObject;

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

    @Override
    public JSONObject getJSON() {
        JSONObject key = super.getJSON();

        key.put("key", keyNumber);

        return key;
    }

    @Override
    public Key clone() {
        return new Key(this.getType(), this.getPosition(), this.getKeyNumber());
    }
}

