package dungeonmania.entities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Grid;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.entities.enemy.*;
import dungeonmania.entities.statics.ZombieToastSpawner;
import dungeonmania.entities.player.Player;
import dungeonmania.util.Position;

public class Interaction {
    public static void interactMerc(Mercenary entity, Grid grid, Player player) {
        boolean success = false;
        Position position = player.getPosition();
        for (Position adjacentPosition : getPosition(position, grid)) {
            for (Entity adjacentEntity : grid.getEntities(adjacentPosition.getX(), adjacentPosition.getY())) {
                if (adjacentEntity.getType().equals(entity.getType())) {
                    if ((!player.hasTreasure()) && (!player.hasSceptre())) {
                        throw new InvalidActionException("You do not have sceptre or any gold");
                    } else if (player.hasSceptre()) {
                        entity.setMindcontrolDuration(10);
                    } else {
                        entity.setBribed(true);
                        player.getInventory().removeNonSpecificItem("treasure");
                        success = true;
                    }
                }
            }
        }
        if (!success) {
            throw new InvalidActionException("You not within 2 cardinal tiles to the mercenary");
        }
    }

    public static void interactSpawner(ZombieToastSpawner entity, Grid grid, Player player) {
        boolean success = false;
        Position position = player.getPosition();
        for (Position adjacentPosition : position.getAdjacentCardinalPositions()) {
            for (Entity adjacentEntity : grid.getEntities(adjacentPosition.getX(), adjacentPosition.getY())) {
                if (adjacentEntity.getType().equals(entity.getType())) {
                    if (!player.hasSword()) {
                        throw new InvalidActionException("You do not have a weapon");
                    } else {
                        grid.dettach(entity);
                        success = true;
                    }
                }
            }
        }
        if (!success) {
            throw new InvalidActionException("You are not cardinally adjacent to the spawner");
        }
    }

    // return all position within 2 cardianl tiles of the given position, used
    // for finding mercenary within range
    private static List<Position> getPosition(Position position, Grid grid) {
        List<Position> adjacentPositions = new ArrayList<>();
        if (!(position.getY() - 1 < 0)) {
            adjacentPositions.add(new Position(position.getX(), position.getY() - 1));
        }
        if (!(position.getY() - 2 < 0)) {
            adjacentPositions.add(new Position(position.getX(), position.getY() - 2));
        }
        if (!(position.getX() + 1 > grid.getWidth())) {
            adjacentPositions.add(new Position(position.getX() + 1, position.getY()));
        }
        if (!(position.getX() + 2 > grid.getWidth())) {
            adjacentPositions.add(new Position(position.getX() + 2, position.getY()));
        }
        if (!(position.getY() + 1 > grid.getHeight())) {
            adjacentPositions.add(new Position(position.getX(), position.getY() + 1));
        }
        if (!(position.getY() + 2 > grid.getHeight())) {
            adjacentPositions.add(new Position(position.getX(), position.getY() + 2));
        }
        if (!(position.getX() - 1 < 0)) {
            adjacentPositions.add(new Position(position.getX() - 1, position.getY()));
        }
        if (!(position.getX() - 2 < 0)) {
            adjacentPositions.add(new Position(position.getX() - 2, position.getY()));
        }
        return adjacentPositions;
    }
}
