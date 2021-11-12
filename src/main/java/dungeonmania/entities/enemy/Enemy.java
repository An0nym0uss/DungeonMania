package dungeonmania.entities.enemy;

import java.util.Arrays;

import dungeonmania.Grid;
import dungeonmania.entities.Battle;
import dungeonmania.entities.Damage;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Health;
import dungeonmania.entities.Moving;
import dungeonmania.response.models.AnimationQueue;
import dungeonmania.util.Position;

import java.util.List;


public abstract class Enemy extends Entity implements Moving, Health, Damage {
    private int speed;
    private int maxHealth;
    private int health;
    private int prev_health;
    
    private int damage;

    public Enemy(String type, Position position, Boolean isInteractable, int speed, int health, int damage) {
        super(type, position, isInteractable);
        this.speed = speed;
        this.prev_health = health;
        this.maxHealth = health;
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
        this.prev_health = health;
        this.health = health;
        if (health > maxHealth) {
            this.maxHealth = health;
        }
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

    @Override
    public AnimationQueue getAnimation() {

        if (health <= 0) return null;

        Double percentage_prev = prev_health/((double)maxHealth);
        Double percentage_curr = health/((double)maxHealth);
        String colour_1;
        String colour_2;
        String prev_healthbar = String.format("healthbar set %2.1f", percentage_prev);
        String curr_healthbar = String.format("healthbar set %2.1f, over 1.5s", percentage_curr);

        if (percentage_prev >= 0.8) {
            colour_1 = "healthbar tint 0x00ff00";
        } else {
            colour_1 = "healthbar tint 0xff0000";
        }

        if (percentage_curr >= 0.8) {
            colour_2 = "healthbar tint 0x00ff00, over 0.5s";
        } else {
            colour_2 = "healthbar tint 0xff0000, over 0.5s";
        }

        return new AnimationQueue("PostTick", getId(), Arrays.asList(
            prev_healthbar, colour_1, curr_healthbar, colour_2
        ), false, -1);
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
