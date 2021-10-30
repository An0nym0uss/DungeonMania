package dungeonmania.entities;

import com.google.gson.JsonObject;

public interface EntityFactory {
    
    public Entity createEntity(JsonObject entityData);
    
}
