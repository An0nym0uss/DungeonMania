package dungeonmania.entities.enemy;

import dungeonmania.Grid;
import dungeonmania.entities.Battle;
import dungeonmania.entities.Entity;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.statics.Boulder;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 
 * 
 * @author Lachlan Kerr, William Wong
 */
public class Spider extends Enemy {
    private List<Direction> movementArray = new ArrayList<Direction>();
    private int directionCount;
    private boolean isReverse;
    private int maxSpiders = 4; //assumption
    private int spawnRate = 30; //assumption
    private int spawnCounter = 0;

    public Spider(Position position,int speed, int health, int damage){
        super("spider", position, false, speed, health, damage);
        this.movementArray.addAll(Arrays.asList(Direction.UP, Direction.RIGHT,
                Direction.DOWN, Direction.DOWN,
                Direction.LEFT, Direction.LEFT,
                Direction.UP,  Direction.UP,
                Direction.RIGHT));
        this.directionCount = 0;
        this.isReverse = false;
    }

    @Override
    public void update(Grid grid) {
        if (getNumSpidersOnGrid(grid) < maxSpiders)
        {
            spawnCounter++;
            if (spawnCounter == spawnRate) {
                spawnCounter = 0;
            }
            Position spawnPosition = getRandomValidSpotOnGrid(grid);

        }
        move(grid, movementArray.get(directionCount));
    }

    /**
     * 
     * @param grid
     * @return
     * @post returned position is within the grid bounds with a 1 space buffer, and nothing at the position constrains a spider
     */
    private Position getRandomValidSpotOnGrid(Grid grid) {
        Position randomPosition = new Position(1, 1);
        Random random = new Random();

        //todo

        return randomPosition;
    }

    /**
     * Gets the number of spiders on the supplied Grid object.
     * @param grid The grid to check for spiders.
     * @return The number of spiders that were found.
     */
    private int getNumSpidersOnGrid(Grid grid) {
        int num = 0;
        Entity[][][] map = grid.getMap();
        for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {
                for (int z = 0; z < grid.getLayerSize(); z++) {
                    if (map[x][y][z] instanceof Spider) {
                        num++;
                    }
                }
            }
        }
        return num;
    }

    @Override
    public void spawn(Entity entity, Grid grid) {
        grid.attach(entity);
    }

    @Override
    public int damageDealt() {
        return super.getDamage();
    }

    @Override
    public boolean isDead() {
        return super.isdead();
    }

    @Override
    public void move(Grid grid, Direction d) {
        // tries to move to next position
        Position newPosition = this.getPosition().translateBy(isReverse ? Direction.getOppositeDirection(d) : d);
        
        int x = newPosition.getX();
        int y = newPosition.getY();
        if (x >= 0 && x < grid.getWidth() &&
            y >= 0 && y < grid.getHeight()
        ) { // check grid boundary
            List<Entity> positionEntities = grid.getEntities(newPosition.getX(), newPosition.getY());
            for (Entity positionEntity : positionEntities) {
                if (positionEntity instanceof Player) { // do a battle with player
                    Battle.battle((Player)positionEntity, (Enemy)this, grid);
                }
                else if (movingConstraints(positionEntity)) { // if can't move there, reverse direction
                    if (directionCount == 0) { // boulder in first position, do nothing till removed
                        return;
                    }
                    newPosition = reversePosition();
                }
            }
        } 
        else { // if can't move there, reverse direction
            if (directionCount == 0) { // spider at top of boundary, do nothing
                return;
            }
            newPosition = reversePosition();
        }
        // move to new positiond
        grid.dettach(this);
        setPosition(newPosition);
        grid.attach(this);

        nextDirection();
    }

    /**
     * Increments or decremenets the direction counter.
     */
    private void nextDirection() {
        if (!isReverse) {
            if (directionCount == 8) {
                directionCount = 1;
            }
            else {
                directionCount++;
            }
        }
        else {
            if (directionCount == 1) {
                directionCount = 8;
            }
            else {
                directionCount--;
            }
        }
    }

    /**
     * Reverses the spiders position.
     * Call if we hit a boulder or grid boundary.
     * @return The new position to move to.
     */
    private Position reversePosition() {
        isReverse = !isReverse;
        nextDirection();
        Direction d = movementArray.get(directionCount);
        return getPosition().translateBy(isReverse ? Direction.getOppositeDirection(d) : d);
    }

    @Override
    public boolean movingConstraints(Entity e) {
        if (e instanceof Boulder) {
            return true;
        }
        return false;
    }
}
