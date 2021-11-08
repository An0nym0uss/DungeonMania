package dungeonmania.entities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Grid;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.entities.enemy.*;
import dungeonmania.entities.statics.Spawner;
import dungeonmania.entities.statics.ZombieToastSpawner;
import dungeonmania.entities.player.Player;
import dungeonmania.util.Position;

public class Interaction {
    public static void interactMerc (Mercenary entity, Grid grid, Player player) {
        boolean success = false;
        Position position = player.getPosition();
        for (Position adjacentPosition : getPosition(position)) {
            for (Entity adjacentEntity : grid.getEntities(adjacentPosition.getX(), adjacentPosition.getY())) {
                if (adjacentEntity.getType() == entity.getType()) {
                    if (!player.hasTreasure()) {
                        throw new InvalidActionException("You do not have any gold");
                    } else {
                        entity.setBribed(true);
                        player.getInventory().removeNonSpecificItem("trerasure"); 
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
                if (adjacentEntity.getType() == entity.getType()) {
                    System.out.println("lala");
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
    private static List<Position> getPosition(Position position) {
        List<Position> adjacentPositions = new ArrayList<>();
        adjacentPositions.add(new Position(position.getX(), position.getY() - 1));
        adjacentPositions.add(new Position(position.getX(), position.getY() - 2));
        adjacentPositions.add(new Position(position.getX() + 1, position.getY()));
        adjacentPositions.add(new Position(position.getX() + 2, position.getY()));
        adjacentPositions.add(new Position(position.getX(), position.getY() + 1));
        adjacentPositions.add(new Position(position.getX(), position.getY() + 2));
        adjacentPositions.add(new Position(position.getX() - 1, position.getY()));
        adjacentPositions.add(new Position(position.getX() - 2, position.getY()));
        return adjacentPositions;
    }
}
