package dungeonmania.entities.enemy;

import dungeonmania.Grid;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Health;
import dungeonmania.entities.Moving;
import dungeonmania.util.Position;
import dungeonmania.entities.Damage;

public abstract class Enemy extends Entity implements Moving, Health, Damage {
    private int speed;
    private Health health;
    private Moving moving;
    private Damage damage;

    public Enemy(String type, Position position, Boolean isInteractable, int speed, Health health, Moving moving, Damage damage) {
        super(type, position, isInteractable);
        this.speed = speed;
        this.health = health;
        this.moving = moving;
        this.damage = damage;
    }

    public Enemy(String type, Position position, Boolean isInteractable, int speed) {
        super(type, position, isInteractable);
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Health getHealth() {
        return health;
    }

    public void setHealth(Health health) {
        this.health = health;
    }

    public Moving getMoving() {
        return moving;
    }

    public void setMoving(Moving moving) {
        this.moving = moving;
    }

    public Damage getDamage() {
        return damage;
    }

    public void setDamage(Damage damage) {
        this.damage = damage;
    }

}