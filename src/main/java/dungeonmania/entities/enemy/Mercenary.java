package dungeonmania.entities.enemy;

import dungeonmania.Grid;
import dungeonmania.entities.Damage;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Health;
import dungeonmania.entities.Moving;
import dungeonmania.util.Direction;

public class Mercenary extends Enemy {

    public Mercenary(int speed, Health health, Moving moving, Damage damage) {
        super(speed, health, moving, damage);
    }

    @Override
    public void update(Grid grid) {
        /* TODO:
        * find out which direction it should move by doing something with player's and mercenary's x and y position
        * try move in that direction
        * if it cant, try move in the other direction closer to the player
        */
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
