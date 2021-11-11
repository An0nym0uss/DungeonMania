package dungeonmania.entities.enemy;

import dungeonmania.Grid;
import dungeonmania.constants.Layer;
import dungeonmania.entities.Battle;
import dungeonmania.entities.Entity;
import dungeonmania.entities.collectable.CollectableEntity;
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
 * @author Lachlan Kerr, William Wong
 */
public class Spider extends Enemy {
    private List<Direction> movementArray = new ArrayList<Direction>();
    private int directionCount;
    private boolean isReverse;

    private static final int maxSpiders = 4; //assumption
    private static final int spawnRate = 30; //assumption
    private static int spawnCounter = 0;
    private static Random random = new Random(0);

    public Spider(Position position, int speed, int health, int damage){
        super("spider", position, false, speed, health, damage);
        this.movementArray.addAll(Arrays.asList(Direction.UP, Direction.RIGHT,
                Direction.DOWN, Direction.DOWN,
                Direction.LEFT, Direction.LEFT,
                Direction.UP,  Direction.UP,
                Direction.RIGHT));
        this.directionCount = 0;
        this.isReverse = false;
    }

    public static void checkForNextSpawn(Grid grid) {
        if (getNumSpidersOnGrid(grid) < maxSpiders)
        {
            spawnCounter++;
            if (spawnCounter == spawnRate) {
                spawnCounter = 0;
                Position spawnPosition = getRandomValidSpotOnGrid(grid);

                if (spawnPosition != null) {
                    Spider newSpider = new Spider(spawnPosition, 1, 1, 1);
                    grid.attach(newSpider);
                }
            }
        }
    }

    @Override
    public void update(Grid grid) {
        move(grid, movementArray.get(directionCount));
    }

    /**
     * 
     * @param grid
     * @param seed
     * @return Potentially null if there is no valid spots to spawn on.
     * @post returned position is within the grid bounds with a 1 space buffer, and nothing at the position constrains a spider
     */
    private static Position getRandomValidSpotOnGrid(Grid grid) {
        //Random random = new Random(seed);

        // the amount of padding to use when selecting a position. 
        // a padding of 1 allows the spider to always stay inside the grid while moving, however is not possible when the grid is too small
        int widthBorderPadding = grid.getWidth() >= 3 ? 1 : 0;
        int heightBorderPadding = grid.getHeight() >= 3 ? 1 : 0;

        boolean redo = true;
        Position randomPosition = null;

        while (redo) {
            redo = false;
            int newX = random.nextInt(grid.getWidth() - (widthBorderPadding != 0 ? 1 + widthBorderPadding : 0));
            int newY = random.nextInt(grid.getHeight() - (heightBorderPadding != 0 ? 1 + heightBorderPadding : 0));

            randomPosition = new Position(widthBorderPadding + newX, heightBorderPadding + newY, Layer.SPIDER);

            List<Entity> positionEntities = grid.getEntities(randomPosition.getX(), randomPosition.getY());
            for (Entity positionEntity : positionEntities) {
                if (positionEntity instanceof Player) { // return null as if player defeated spider instantly
                    randomPosition = null;
                }
                else if (positionEntity instanceof Boulder || positionEntity instanceof CollectableEntity) { // we can't use movingConstraints() because we're in a static function
                    redo = true;
                } 
            }
        }
        
        return randomPosition;
    }

    /**
     * Gets the number of spiders on the supplied Grid object.
     * @param grid The grid to check for spiders.
     * @return The number of spiders that were found.
     */
    private static int getNumSpidersOnGrid(Grid grid) {
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
    public int damageDealt() {
        return super.getDamage();
    }

    @Override
    public boolean isDead() {
        return super.isDead();
    }

    @Override
    public void move(Grid grid, Direction d) {
        // tries to move to next position
        Position newPosition = null; 
        Entity doBattle = null;
        int reverseCount = 0; // how many times we have reversed
        
        while (newPosition == null && reverseCount < 2) {
            d = movementArray.get(directionCount);
            newPosition = this.getPosition().translateBy(isReverse ? Direction.getOppositeDirection(d) : d);

            int x = newPosition.getX();
            int y = newPosition.getY();
            if (x >= 0 && x < grid.getWidth() &&
                y >= 0 && y < grid.getHeight()
            ) { // check grid boundary
                List<Entity> positionEntities = grid.getEntities(newPosition.getX(), newPosition.getY());
                for (Entity positionEntity : positionEntities) {
                    if (this.shouldCommenceBattle(grid)) {
                        this.commenceBattle(grid);
                    }
//                    if (positionEntity instanceof Player) { // do a battle with player
//                        doBattle = positionEntity;
//                    }
                    else if (movingConstraints(positionEntity)) { // if can't move there, reverse direction
                        if (directionCount == 0) { // boulder in first position, do nothing till removed
                            return;
                        }
                        reversePosition();
                        reverseCount++;
                        newPosition = null;
                    }
                }
            } 
            else { // if can't move there, reverse direction
                if (directionCount == 0) { // spider at top of boundary, do nothing
                    return;
                }
                reversePosition();
                reverseCount++;
                newPosition = null;
            }
        }
        
        if (newPosition != null) {
            grid.dettach(this);
            setPosition(new Position(newPosition.getX(), newPosition.getY(), Layer.SPIDER));
            grid.attach(this);
        }

//        if (doBattle != null) {
//            Battle.battle((Player)doBattle, (Enemy)this, grid);
//        }

        nextDirection();
    }

    /**
     * Increments or decremenets the direction counter, depending on reverse status.
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
     */
    private void reversePosition() {
        isReverse = !isReverse;
        nextDirection();
    }

    @Override
    public boolean movingConstraints(Entity e) {
        if (e instanceof Boulder) {
            return true;
        }
        else if (e instanceof CollectableEntity) {
            return true;
        } 
        else if (e instanceof Enemy) {
            return true;
        }
        return false;
    }
}
