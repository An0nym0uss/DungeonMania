package dungeonmania.entities;

import dungeonmania.util.Position;

public abstract class Entity {
    private String id;
    private String type;
    private Position position;
    private boolean isInteractable;
}