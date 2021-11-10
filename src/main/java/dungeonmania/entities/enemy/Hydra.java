package dungeonmania.entities.enemy;

import dungeonmania.Grid;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

/**
 * 
 * @auther Lachlan Kerr
 */
public class Hydra extends RandomMovingEnemy {

    public Hydra(Position position, int speed, int health, int damage) {
        super("hydra", position, false, speed, health, damage);
    }

    @Override
    public void update(Grid grid) {
        move(grid, Direction.NONE);
    }

    @Override
    public int damageDealt() {
        return super.getDamage();
    }

    @Override
    public boolean isDead() {
        return super.isdead();
    }
}