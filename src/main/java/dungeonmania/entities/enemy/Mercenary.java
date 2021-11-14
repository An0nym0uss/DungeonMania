package dungeonmania.entities.enemy;

import dungeonmania.entities.collectable.CollectableEntity;
import dungeonmania.exceptions.InvalidActionException;
import org.json.JSONObject;

import dungeonmania.Grid;
import dungeonmania.constants.Layer;
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
    private int mindcontrolDuration;
    
    public Mercenary(Position position, int speed, int health, int damage) {
        super("mercenary", position, true, speed, health, damage);
        this.bribed = false;
        this.mindcontrolDuration = 0;
    }

    @Override
    public void update(Grid grid) {
        this.tickDown();
        if (this.shouldCommenceBattle(grid) && !this.getBribed()) {
            this.commenceBattle(grid);
        }
        else {
            List<Position> shortestPath = this.breadthFirstSearch(grid);

            // Perform movement
            if (shortestPath.size() == 2 && getBribed()) {

            }
            else if (shortestPath.size() > 1) {
                // There is a valid path
                Position nextStep = shortestPath.get(1);
                grid.dettach(this);
                Position newPos = new Position(nextStep.getX(), nextStep.getY(), Layer.ENEMY);
                this.setPosition(newPos);
                grid.attach(this);
            }

            if (this.shouldCommenceBattle(grid) && !this.getBribed()) {
                this.commenceBattle(grid);
            }
        }


    }


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

    public int getMindcontrolDuration() {
        return this.mindcontrolDuration;
    }

    public void setMindcontrolDuration(int mindcontrolDuration) {
        this.mindcontrolDuration = mindcontrolDuration;
    }

    /**
     * check if the mercenary is mind controlled, i.e. mindControlled duration > 0
     * for each tick, if so, set the mercenary to bribed state and reduce the 
     * duration by one 
     */
    public void tickDown() {
        if (this.mindcontrolDuration > 0) {
            this.mindcontrolDuration -= 1;
            if (this.mindcontrolDuration == 0) {
                this.bribed = false;
            }
        }
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
        return false;
    }

    @Override
    public Mercenary clone() {
        Position oldPos = new Position(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getLayer());
        return new Mercenary(oldPos, this.getSpeed(), this.getHealth(), this.getDamage());
    }

    @Override
    public JSONObject getJSON() {

        JSONObject mercenary = super.getJSON();

        mercenary.put("bribed", getBribed());
        mercenary.put("mindcontrolDuration", getMindcontrolDuration());

        return mercenary;
    }

}
