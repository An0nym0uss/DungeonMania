package dungeonmania.entities.enemy;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Health;
import dungeonmania.entities.Moving;
import dungeonmania.entities.Damage;

public abstract class Enemy extends Entity {
    private int speed;

    public Enemy(int speed) {
        this.speed = speed;
    }




}