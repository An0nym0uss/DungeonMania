package dungeonmania.entities.enemy;

import org.json.JSONObject;

import dungeonmania.Grid;
import dungeonmania.entities.Damage;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Health;
import dungeonmania.entities.Moving;
import dungeonmania.entities.statics.Boulder;
import dungeonmania.entities.statics.Door;
import dungeonmania.entities.statics.Wall;
import dungeonmania.util.Direction;
import dungeonmania.entities.player.Player;
import dungeonmania.util.Position;

import java.util.List;
import java.util.*;

public class Mercenary extends Enemy {
    public Mercenary(int speed, int health, int damage) {
        super(speed, health, damage);
        
    }

    @Override
    public void update(Grid grid) {
        /* TODO:
        * find out which direction it should move by doing something with player's and mercenary's x and y position
        * try move in that direction
        * if it cant, try move in the other direction closer to the player
        */
        // get player
        // breadth first search
        // A* search

        List<Position> adjacentSquares = this.getPosition().getAdjacentCardinalPositions();
        grid.getPlayer().getPosition().getAdjacentCardinalPositions();

        List<Position> shortestPath = this.breadthFirstSearch(grid);
        //                Path - (1,1) (2,1) (2,2)
        // TODO: What if the path doesn't exist, or is less than 1?
        if (shortestPath == null) {

        }
        // Perform movement
        Position nextStep = shortestPath.get(1);
        this.setPosition(nextStep.getX(), nextStep.getY());

        // TODO: Bribing




//        visited = {
//                "position": "parent"
//                "(1,1): Null"
//                "(1,2): (1,1)"
//                "(2,1): (1,1)"
//                "(2,2) - Goal": ("2,1")
//
//                Path - (1,1) (2,1) (2,2)
//        }
    }

    // Returns the shortest path between the start (mercenary) and the goal (player)
    public List<Position> breadthFirstSearch(Grid grid) {
        List<Position> queue = new ArrayList<Position>();
        queue.add(this.getPosition());
        // key - position; value - previous position
        Map<Position, Position> visited = new HashMap<>();
        visited.put(this.getPosition(), null);
        boolean canMoveToPosition = true;
//        visited.put(key, value);
//        visited.get(key);
//        visited.containsKey()
//        for (Position value : visited.values()) {
//
//        }

        while (queue.size() != 0) {
            Position v = queue.remove(0);
//            if (v == grid.getPlayer().getPosition()) {
//                return v;
//            }
            for (Position w : v.getAdjacentCardinalPositions()) {
                // Checks if square is visited AND is possible to move into (via moving constraints)
                if (!visited.containsKey(w) && this.canMoveToPosition(grid, w)) {
                    visited.containsKey(w);
                    queue.add(w);
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
