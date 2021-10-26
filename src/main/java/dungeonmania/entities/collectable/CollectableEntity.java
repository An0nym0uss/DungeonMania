package dungeonmania.entities.collectable;

import dungeonmania.response.models.ItemResponse;

public abstract class CollectableEntity {
    protected String id;
    protected String type;

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public ItemResponse createItemResponse() {
        return new ItemResponse(id, type);
    }
}