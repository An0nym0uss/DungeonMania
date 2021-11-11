package dungeonmania.entities.enemy;

import dungeonmania.Grid;
import dungeonmania.entities.Battle;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

/**
 * When attacked by a player or ally, a hydra has a 50% chance of converting damage taken to extra health.
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
        return super.isDead();
    }
    
    @Override
    public void receiveDamage(int damage) {
        double healChance = random.nextDouble();
        if (healChance >= 0.5) {
            damage = -damage;
        }
        setHealth(getHealth() - damage);
    }

    public boolean shouldCommenceBattle(Grid grid) {
        // Checks if enemy is on the same square as the player. If so, commence battle (see Battle class)
        return grid.getPlayer().getPosition() == this.getPosition();
    }

    public void commenceBattle(Grid grid) {
        Battle.battle(grid.getPlayer(), this, grid);
    }
}
