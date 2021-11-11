package dungeonmania.entities.enemy;

import dungeonmania.Grid;
import dungeonmania.entities.Battle;
import dungeonmania.entities.Damage;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Health;
import dungeonmania.entities.Moving;
import dungeonmania.util.Position;

import java.util.List;


public abstract class Enemy extends Entity implements Moving, Health, Damage {
    private int speed;
    private int health;
    
    private int damage;

    public Enemy(String type, Position position, Boolean isInteractable, int speed, int health, int damage) {
        super(type, position, isInteractable);
        this.speed = speed;
        this.health = health;
        this.damage = damage;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    // public Moving getMoving() {
    //     return moving;
    // }

    // public void setMoving(Moving moving) {
    //     this.moving = moving;
    // }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    // Checks if the position is free to move into (considering the movementConstraints)
    public boolean canMoveToPosition(Grid grid, Position position) {
        if (position.getX() >= grid.getWidth() || position.getY() >= grid.getHeight()) {
            return false;
        }
        List<Entity> positionEntities = grid.getEntities(position.getX(), position.getY());
        for (Entity positionEntity : positionEntities) {
            if (this.movingConstraints(positionEntity)) {
                return false;
            }
        }

        return true;

    }

    public boolean isDead() {
        if (this.health <= 0) {
            return true;
        }
        return false;
    }

    public int damageDealt() {
        return (getHealth() * getDamage()) / 10;
    }

    @Override
    public void receiveDamage(int damage) {
        setHealth(getHealth() - damage);
    }


    /**
     * Checks if enemy is on the same square as the player. If so, commence battle (see Battle class)
     * @param grid
     * @return
     */
    public boolean shouldCommenceBattle(Grid grid) {
        return grid.getPlayer().getPosition().getX() == this.getPosition().getX() && 
               grid.getPlayer().getPosition().getY() == this.getPosition().getY();
    }

    public void commenceBattle(Grid grid) {
        Battle.battle(grid.getPlayer(), this, grid);
    }

    public void receiveAndruilDamage(int damage) {
        setHealth(getHealth() - damage);
    }
}
