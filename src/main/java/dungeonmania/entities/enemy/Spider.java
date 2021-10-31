package dungeonmania.entities.enemy;

import dungeonmania.Grid;
import dungeonmania.entities.Entity;
import dungeonmania.entities.statics.Boulder;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.*;

public class Spider extends Enemy {
    private List<Direction> movesUpWhenSpawns = new ArrayList<Direction>();
    private List<Direction> movementArray = new ArrayList<Direction>();
    private int directionCount;
    private boolean isReverse;

    public Spider(Position position,int speed, int health, int damage){
        super("spider", position, false, speed, health, damage);
        this.movesUpWhenSpawns.addAll(Arrays.asList(Direction.UP));
        this.movementArray.addAll(Arrays.asList(Direction.RIGHT,
                Direction.DOWN, Direction.DOWN,
                Direction.LEFT, Direction.LEFT,
                Direction.UP,  Direction.UP,
                Direction.RIGHT));
        this.directionCount = 0;
        this.isReverse = false;
    }

    @Override
    public void update(Grid grid) {
        //spawns
        Direction upDirection = movesUpWhenSpawns.get(directionCount);
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
        this.setPosition(newPosition);

    }

    @Override
    public void spawn(Entity entity, Grid grid) {
        grid.attach(entity);
    }

    @Override
    public int damageDealt() {
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
