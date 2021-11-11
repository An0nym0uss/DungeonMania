package dungeonmania.entities.enemy;

import dungeonmania.Grid;
import dungeonmania.entities.*;
import dungeonmania.entities.player.Player;
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

    // TODO temporary code for testing battle
    public boolean isdead() {
        if (this.health <= 0) {
            return true;
        }
        return false;
    }

    // TODO temporary code for testing battle
    public int damageDealt() {
        return this.getDamage();
    }

    public boolean shouldCommenceBattle(Grid grid) {
        // Checks if enemy is on the same square as the player. If so, commence battle (see Battle class)
        return grid.getPlayer().getPosition() == this.getPosition();
    }

    public void commenceBattle(Grid grid) {
        Battle.battle(grid.getPlayer(), this, grid);
    }
}