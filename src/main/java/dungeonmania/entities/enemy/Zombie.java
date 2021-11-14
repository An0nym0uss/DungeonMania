package dungeonmania.entities.enemy;

import dungeonmania.Grid;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

/**
 * 
 * @author Lachlan Kerr, William Wong
 */
public class Zombie extends RandomMovingEnemy {

    public Zombie(Position position, int speed, int health, int damage) {
        super("zombie_toast", position, false, speed, health, damage);
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
        return super.isDead();
    }

    @Override
    public Zombie clone() {
        Position oldPos = new Position(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getLayer());
        return new Zombie(oldPos, this.getSpeed(), this.getHealth(), this.getDamage());
    }
}
