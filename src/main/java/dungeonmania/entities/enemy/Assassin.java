package dungeonmania.entities.enemy;

import dungeonmania.entities.collectable.CollectableEntity;
import org.json.JSONObject;

import dungeonmania.Grid;
import dungeonmania.entities.Entity;
import dungeonmania.entities.statics.Boulder;
import dungeonmania.entities.statics.Door;
import dungeonmania.entities.statics.Wall;
import dungeonmania.util.Direction;
import dungeonmania.entities.player.Player;
import dungeonmania.util.Position;
//import dungeonmania.util.Mercenary;

import java.util.List;
import java.util.*;

public class Assassin extends Enemy {

    public Assassin(String type, Position position, Boolean isInteractable, int speed, int health, int damage) {
        super("assassin", position, false, speed, health, damage);
    }

    @Override
    public void move(Grid grid, Direction d) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean movingConstraints(Entity e) {
        // TODO Auto-generated method stub
        return false;
    }
}
