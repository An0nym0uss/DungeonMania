package dungeonmania.entities.enemy;

import dungeonmania.Grid;
import dungeonmania.entities.Damage;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Health;
import dungeonmania.entities.Moving;
import dungeonmania.entities.statics.Boulder;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.Arrays;
import java.util.List;

public class Spider extends Enemy {

    private List<Direction> movementArray;
    private int directionCount;
    private boolean isReverse;

    public Spider(int speed, Health health, Moving moving, Damage damage) {
        super(speed, health, moving, damage);
        this.movementArray.addAll(Arrays.asList(Direction.RIGHT, Direction.DOWN, Direction.DOWN, Direction.LEFT,
            Direction.LEFT, Direction.UP,  Direction.UP,  Direction.RIGHT));
        this.directionCount = 0;
        this.isReverse = false;
    }

    @Override
    public void update(Grid grid) {
        // tries to move to next position
        Direction nextDirection = movementArray.get(directionCount);
        Position newPosition = this.getPosition().translateBy(isReverse ? Direction.getOppositeDirection(nextDirection) : nextDirection);
        // if can't move there, reverse direction
        // TODO need to add grid constraint too
        List<Entity> positionEntities = grid.getEntities(newPosition.getX(), newPosition.getY());
        for (Entity positionEntity : positionEntities) {
            if (this.movingConstraints(positionEntity)) {
                isReverse = !isReverse;
                return;
            }
        }
        // move to new position
        // probably should use move()
        this.setPosition(newPosition.getX(), newPosition.getY());

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
        if (e instanceof Boulder) {
            return true;
        }
        return false;
    }
}
