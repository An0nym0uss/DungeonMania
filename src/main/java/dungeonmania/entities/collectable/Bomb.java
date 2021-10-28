package dungeonmania.entities.collectable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dungeonmania.Grid;
import dungeonmania.constants.Layer;
import dungeonmania.entities.Entity;
import dungeonmania.entities.enemy.Enemy;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.statics.Boulder;
import dungeonmania.entities.statics.Exit;
import dungeonmania.entities.statics.FloorSwitch;
import dungeonmania.util.Position;

public class Bomb extends CollectableEntity{
    private int blastRadius;
    private boolean isPlaced;

    public Bomb(Position position) {
        super("bomb", position, false);
        this.blastRadius = 1;
        this.isPlaced = false;
    }

    public Bomb(Position position, int blastRadius) {
        this(position);
        this.blastRadius = blastRadius;
    }

    public boolean hasPlaced() {
        return this.isPlaced;
    }

    @Override
    public void placeBomb(Player player, Grid grid) {
        Position position = player.getPosition();    
        
        if (checkPlaceValid(position, grid)) {
            this.setPosition(new Position(position.getX(), position.getY(), Layer.ENEMY));
            this.isPlaced = true;
            grid.attach(this);

            if (checkSwitchOn(grid)) {
                blast(grid);
            }
        }
    }

    private boolean checkPlaceValid(Position position, Grid grid) {
        List<Entity> entities = grid.getEntities(position.getX(), position.getY());

        for (Entity entity : entities) {
            if (entity instanceof Enemy
            ) {
                return false;
            }
        }

        return true;
    }

    private List<Position> getCardinallyAdjacentPosition(int x, int y) {
        List<Position> adjacentPositions = new ArrayList<>();
        adjacentPositions.add(new Position(x  , y-1));
        adjacentPositions.add(new Position(x+1, y));
        adjacentPositions.add(new Position(x  , y+1));
        adjacentPositions.add(new Position(x-1, y));
        return adjacentPositions;
    }

    public boolean checkSwitchOn(Grid grid) {
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();

        for (Position adjacentPosition : getCardinallyAdjacentPosition(x, y)) {
            if (adjacentPosition.getX() >= 0 && adjacentPosition.getY() >= 0) {
                boolean hasBoulder = false;
                boolean hasSwitch = false;
                for (Entity entity : grid.getEntities(adjacentPosition.getX(), adjacentPosition.getY())) {
                    if (entity instanceof FloorSwitch) {
                        hasSwitch = true;
                    } else if (entity instanceof Boulder) {
                        hasBoulder = true;
                    }
                }
                if (hasBoulder && hasSwitch) {
                    return true;
                }
            }
        }

        return false;
    }

    public void blast(Grid grid) {
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();

        for (int i = 0 - blastRadius; i <= blastRadius; i++) {
            int newX = x + i;
            if (newX >= 0) {
                for (int j = 0 - blastRadius; j <= blastRadius; j++) {
                    int newY = y + j;
                    if (newY >= 0) {
                        Iterator<Entity> itr = grid.getEntities(newX, newY).iterator();
                        while (itr.hasNext()) {
                            Entity entity = itr.next();
                            if (!(entity instanceof Player || entity instanceof Exit)) {
                                grid.dettach(entity);
                            }
                        }
                    }
                }
            }
        }

        grid.dettach(this);
    }

}

