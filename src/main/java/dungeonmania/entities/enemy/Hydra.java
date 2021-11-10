package dungeonmania.entities.enemy;

import dungeonmania.Grid;
import dungeonmania.entities.Entity;
import dungeonmania.entities.statics.Boulder;
import dungeonmania.entities.statics.Door;
import dungeonmania.entities.statics.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Hydra extends Enemy {

    public Hydra(Position position, int speed, int health, int damage) {
        super("hydra", position, false, speed, health, damage);
    }

    @Override
    public void move(Grid grid, Direction d) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int damageDealt() {
        return super.getDamage();
    }

    @Override
    public boolean isDead() {
        return super.isdead();
    }

    @Override
    public boolean movingConstraints(Entity e) {
        if (e instanceof Wall) {
            return true;
        }
        if (e instanceof Door && ((Door) e).getIsOpen() == false) {
            return true;
        }
        if (e instanceof Boulder) {
            return true;
        }
        if (e instanceof Enemy) {
            return true;
        }
        return false;
    }
}