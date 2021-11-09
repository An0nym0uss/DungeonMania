package dungeonmania.entities.enemy.boss;

import dungeonmania.Grid;
import dungeonmania.entities.Damage;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Health;
import dungeonmania.entities.Moving;
import dungeonmania.entities.enemy.Enemy;
import dungeonmania.entities.statics.Spawner;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Bosses extends Enemy implements Moving, Health, Damage, Spawner {
    public Bosses(String type, Position position, Boolean isInteractable, int speed, int health, int damage) {
        super(type, position, isInteractable, speed, health, damage);
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

    @Override
    public void spawn(Entity entity, Grid grid) {

    }
}
