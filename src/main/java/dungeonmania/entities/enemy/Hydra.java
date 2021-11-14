package dungeonmania.entities.enemy;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Grid;
import dungeonmania.constants.Layer;
import dungeonmania.entities.Battle;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Spawner;
import dungeonmania.entities.player.Player;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

/**
 * When attacked by a player or ally, a hydra has a 50% chance of converting damage taken to extra health.
 * @auther Lachlan Kerr
 */
public class Hydra extends RandomMovingEnemy implements Spawner {

    private static final int maxHydras = 1; //assumption
    private static final int spawnRate = 50; //required
    private static int spawnCounter = 0;

    public Hydra(Position position, int speed, int health, int damage) {
        super("hydra", position, false, speed, health, damage);
    }

    public Hydra() {
        this(new Position(0, 0), 1, 30, 10);
    }
    
    /**
     * Resets spawn counter to 0.
     * This is required due to how gradle and static fields work, each test is not run independently.
     * Called on new game.
     */
    public static void resetSpawnCounter() {
        spawnCounter = 0;
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
        return super.isDead();
    }
    
    @Override
    public void receiveDamage(int damage) {
        double healChance = random.nextDouble();
        if (healChance >= 0.5) {
            damage = -damage;
        }
        setHealth(getHealth() - damage);
    }

    @Override
    public void spawn(Entity entity, Grid grid) {
        grid.attach(entity);
    }

    @Override
    public int getSpawnRate() {
        return spawnRate;
    }

    @Override
    public int getMaxEntities() {
        return maxHydras;
    }

    @Override
    public int getSpawnCounter() {
        return spawnCounter;
    }

    @Override
    public void setSpawnCounter(int spawnCounter) {
        Hydra.spawnCounter = spawnCounter;
    }

    @Override
    public Entity getSpawnEntity() {
        return new Hydra();
    }

    @Override
    public Position getSpawnPosition(Grid grid) {
        boolean redo = true;
        Position randomPosition = null;

        List<Position> possiblePositions = new ArrayList<Position>();

        for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {
                possiblePositions.add(new Position(x, y, Layer.ENEMY));
            }
        }

        while (redo && possiblePositions.size() > 0) {
            redo = false;
            randomPosition = possiblePositions.remove(random.nextInt(possiblePositions.size()));

            List<Entity> positionEntities = grid.getEntities(randomPosition.getX(), randomPosition.getY());
            for (Entity positionEntity : positionEntities) {
                if (movingConstraints(positionEntity) || positionEntity instanceof Player) { 
                    redo = true;
                } 
            }
        }
        
        return randomPosition;
    }

    /*public boolean shouldCommenceBattle(Grid grid) {
        // Checks if enemy is on the same square as the player. If so, commence battle (see Battle class)
        return grid.getPlayer().getPosition() == this.getPosition();
    }

    public void commenceBattle(Grid grid) {
        Battle.battle(grid.getPlayer(), this, grid);
    }*/

    @Override
    public Hydra clone() {
        Position oldPos = new Position(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getLayer());
        return new Hydra(oldPos, this.getSpeed(), this.getHealth(), this.getDamage());
    }
}

