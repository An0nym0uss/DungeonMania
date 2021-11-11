package dungeonmania.entities.enemy;

import dungeonmania.entities.collectable.CollectableEntity;
import org.json.JSONObject;

import dungeonmania.Grid;
import dungeonmania.entities.Entity;
import dungeonmania.entities.statics.Boulder;
import dungeonmania.entities.statics.Door;
import dungeonmania.entities.statics.Wall;
import dungeonmania.util.Direction;
import dungeonmania.entities.player.Player;
import dungeonmania.util.Position;

import java.util.List;
import java.util.*;

public class Mercenary extends Enemy {

    private boolean bribed;
    
    public Mercenary(Position position, int speed, int health, int damage) {
        super("mercenary", position, false, speed, health, damage);
        this.bribed = false;
    }

    @Override
    public void update(Grid grid) {
//        return;
        /* TODO:
        * find out which direction it should move by doing something with player's and mercenary's x and y position
        * try move in that direction
        * if it cant, try move in the other direction closer to the player
        */
        // get player
        // breadth first search
        // A* search
        
        //List<Position> adjacentSquares = this.getPosition().getAdjacentCardinalPositions();
        // grid.getPlayer().getPosition().getAdjacentCardinalPositions();

         List<Position> shortestPath = this.breadthFirstSearch(grid);
        // System.out.println(shortestPath);

        // Perform movement
         Position nextStep = shortestPath.get(1);
        // System.out.println(nextStep);
         this.setPosition(nextStep);
        // TODO: Bribing
        // boolean bribed = this.isBribed();
    }

    public boolean isBribed() {
        if (this.getBribed()) {
            // Mercenary follows player
            return true;
        }
        else {
            // Mercenary attacks player
            return false;
        }
    }

    // public boolean isHasBeenBribed() {
    //     return hasBeenBribed;
    // }

    // public void setHasBeenBribed(boolean hasBeenBribed) {
    //     this.hasBeenBribed = hasBeenBribed;
    // }

    // Returns the shortest path between the start (mercenary) and the goal (player)
    public List<Position> breadthFirstSearch(Grid grid) {
        List<Position> queue = new ArrayList<Position>();
        queue.add(this.getPosition());
        // key - position; value - previous position
        Map<Position, Position> visited = new HashMap<>();
        visited.put(this.getPosition(), null);
        //boolean canMoveToPosition = true;
//        visited.put(key, value);
//        visited.get(key);
//        visited.containsKey()
//        for (Position value : visited.values()) {
//
//        }

        boolean hasReachedGoal = false;
        while (queue.size() != 0 && !hasReachedGoal) {
            Position v = queue.remove(0);

            // Check goal condition (adjacent square of player)
            List<Position> playerAdjacentPositions = grid.getPlayer().getPosition().getAdjacentCardinalPositions();
            // Check v is in playerAdjacentPositions
            for (Position playerAdjacentPosition: playerAdjacentPositions) {
                if (playerAdjacentPosition.getX() == v.getX() && playerAdjacentPosition.getY() == v.getY()) {
                    visited.put(grid.getPlayer().getPosition(), v);
                    hasReachedGoal = true;
                    break;
                }
            }


            for (Position w : v.getAdjacentCardinalPositions()) {
                if (w.getX() >= 0 && w.getX() < grid.getWidth() && w.getY() >= 0 && w.getY() < grid.getHeight()) {
                    // Checks if square is visited AND is possible to move into (via moving constraints)
                    if (!visited.containsKey(w) && this.canMoveToPosition(grid, w)) {
//                        if (!visited.containsKey(w)) {
                        // visited.containsKey(w);
                        visited.put(w, v);
                        queue.add(w);
                    }
                }

            }
        }
        


        // visited
        // get shortest path as a list of positions
//        path is empty list
//        current node is goal


//        while node is not null:
//           add current node to path
//           set current node as parent
        
        List<Position> path = new ArrayList<Position>();
        Position currentNode = grid.getPlayer().getPosition();
        while (currentNode != null) {
            path.add(currentNode);
            currentNode = visited.get(currentNode);
        }
        Collections.reverse(path);
        return path;
        
    }

    public boolean getBribed() {
        return this.bribed;
    }

    public void setBribed(boolean bribed) {
        this.bribed = bribed;
    }

    @Override
    public void move(Grid grid, Direction d) {

    }

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
        if (e instanceof Player) {
            return true;
        }
        return false;
    }
}
