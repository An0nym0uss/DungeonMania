package dungeonmania.entities.enemy;

import dungeonmania.Grid;
import dungeonmania.constants.Layer;
import dungeonmania.entities.Battle;
import dungeonmania.entities.Entity;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.statics.Boulder;
import dungeonmania.entities.statics.Door;
import dungeonmania.entities.statics.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.List;
import java.util.Random;

public class Zombie extends Enemy {

    private static Random random = new Random(0);

    public Zombie(Position position, int speed, int health, int damage) {
        super("zombie_toast", position, false, speed, health, damage);
    }

    @Override
    public void update(Grid grid) {
        move(grid, Direction.NONE);
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
        List<Position> adjacentSquares = this.getPosition().getAdjacentCardinalPositions();
        Position newPosition = null;
        Entity doBattle = null;

        while (newPosition == null && adjacentSquares.size() > 0) { // pick random directions until none are left
            int nextMove = random.nextInt(adjacentSquares.size());
            newPosition = adjacentSquares.remove(nextMove);

            int x = newPosition.getX();
            int y = newPosition.getY();
            if (x >= 0 && x < grid.getWidth() &&
                y >= 0 && y < grid.getHeight()
            ) { // check grid boundary
                List<Entity> positionEntities = grid.getEntities(newPosition.getX(), newPosition.getY());
                for (Entity positionEntity : positionEntities) {
                    if (positionEntity instanceof Player) { // do a battle with player
                        doBattle = positionEntity;
                    }
                    else if (movingConstraints(positionEntity)) { // can't move here
                        newPosition = null;
                    }
                }
            }
            else {
                newPosition = null;
            }
        }

        if (newPosition != null) {
            grid.dettach(this);
            setPosition(new Position(newPosition.getX(), newPosition.getY(), Layer.ENEMY));
            grid.attach(this);
        }

        if (doBattle != null) {
            Battle.battle((Player)doBattle, (Enemy)this, grid);
        }
    }
    
    /**
     * Determines if the specified entity constrains the movement of this class
     */
    @Override
    public boolean movingConstraints(Entity e) {
        if (e instanceof Wall) {
            return true;
        }
        if (e instanceof Door && ((Door) e).getIsOpen() == false) {
            return true;
        }
        if (e instanceof Boulder) {
            return true;
        }
        if (e instanceof Enemy) {
            return true;
        }
        return false;
    }
}
