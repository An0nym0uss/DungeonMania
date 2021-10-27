package dungeonmania.entities.enemy;

import dungeonmania.Grid;
import dungeonmania.entities.Damage;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Health;
import dungeonmania.entities.Moving;
import dungeonmania.util.Direction;

public class Spider extends Enemy {

    public Spider(int speed, Health health, Moving moving, Damage damage) {
        super(speed, health, moving, damage);
    }

    @Override
    public void update(Grid grid) {
        super.update(grid);
    }

    @Override
    public int damageDealt(Entity e) {
        return 0;
    }

    @Override
    public boolean isDead() {
        return false;
    }

    @Override
    public void move(Grid grid, Direction d) {

    }

    @Override
    public boolean movingConstraints(Entity e) {
        return false;
    }
}
