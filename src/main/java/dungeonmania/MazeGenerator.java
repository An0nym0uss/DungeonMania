package dungeonmania;

import java.util.Random;

import dungeonmania.constants.Layer;
import dungeonmania.entities.Entity;
import dungeonmania.entities.RandomCollectableFactory;
import dungeonmania.entities.RandomEnemyFactory;
import dungeonmania.entities.collectable.Key;
import dungeonmania.entities.collectable.Sword;
import dungeonmania.entities.collectable.Treasure;
import dungeonmania.entities.collectable.Wood;
import dungeonmania.entities.collectable.buildable.Shield;
import dungeonmania.entities.enemy.Enemy;
import dungeonmania.entities.enemy.Spider;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.statics.Door;
import dungeonmania.entities.statics.Exit;
import dungeonmania.entities.statics.Portal;
import dungeonmania.entities.statics.Wall;
import dungeonmania.entities.statics.ZombieToastSpawner;
import dungeonmania.modes.Mode;
import dungeonmania.util.Position;

/**
 * @author Enoch Kavur (z5258204)
 */
public class MazeGenerator {

    static private final int BOTH_CLOSED = 0;
    static private final int RIGHT_ONLY_OPEN = 1;
    static private final int DOWN_ONLY_OPEN = 2;
    static private final int BOTH_OPEN = 3;

    private int[][] maze;
    
    private boolean[][] hasVisited; // 

    static private final int HEIGHT = 25;

    static private final int WIDTH = 25;

    private int startLocation; // Start location of the maze

    private int endLocation; // End location of the maze

    private Mode mode;

    private Random rand = new Random();

    public MazeGenerator(int startLocation, int endLocation, Mode mode) { // Constructor

        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.mode = mode;

        // connections of array
        this.maze = new int[HEIGHT][WIDTH];

        // visited array
        this.hasVisited = new boolean[HEIGHT][WIDTH];

        for (int row = 0; row < HEIGHT; row++) {
            
            for (int col = 0; col < WIDTH; col++) {

                maze[row][col] = BOTH_CLOSED;

                hasVisited[row][col] = false;

            }
        }

        int xStart = rand.nextInt(25);
        int yStart = rand.nextInt(25);

        CellGenerator(yStart*25+xStart);
        
    }

    public void CellGenerator(int cell) {

        int row = cell / WIDTH;

        int col = cell % WIDTH;

        hasVisited[row][col] = true;

        // System.out.println("[" + row + "," + col + "]"); testing

        boolean hasRight = true;

        boolean hasDown = true;

        boolean hasLeft = true;

        boolean hasUp = true;


        if ((col+1 >= WIDTH) || (hasVisited[row][col+1])) {

            hasRight = false;

        }

        if ((row+1 >= HEIGHT) || (hasVisited[row+1][col])) {

            hasDown = false;

        }

        if ((row-1 < 0) || hasVisited[row-1][col]) {

            hasUp = false;

        }

        if ((col-1 < 0) || (hasVisited[row][col-1])) {

            hasLeft = false;

        }

        while ((hasRight || hasDown || hasLeft || hasUp)) {

            int random = rand.nextInt(4);

            if (random == 0 && hasRight) {

                if (maze[row][col] == 0) {

                    maze[row][col] = RIGHT_ONLY_OPEN;

                } else {

                    maze[row][col] = BOTH_OPEN;

                }
                hasRight = false;

                CellGenerator(row*WIDTH + col+ 1);

            } else if (random == 1 && hasDown) {

                
                if (maze[row][col] == 0) {

                    maze[row][col] = DOWN_ONLY_OPEN;

                } else {

                    maze[row][col] = BOTH_OPEN;

                }

                hasDown = false;

                CellGenerator((row+1)*WIDTH + col);

            } else if (random == 2 && hasLeft) {

                maze[row][col-1] = RIGHT_ONLY_OPEN;

                hasLeft = false;

                CellGenerator((row)*WIDTH + col-1);

            } else if (random == 3 && hasUp) {

                maze[row-1][col] = DOWN_ONLY_OPEN;

                hasUp = false;

                CellGenerator((row-1)*WIDTH + col);

            }

            if ((col+1 >= WIDTH) || (hasVisited[row][col+1])) {

                hasRight = false;

            }

            if ((row+1 >= HEIGHT) || (hasVisited[row+1][col])) {

                hasDown = false;

            }

            if ((row-1 < 0) || hasVisited[row-1][col]) {

                hasUp = false;

            }

            if ((col-1 < 0) || (hasVisited[row][col-1])) {

                hasLeft = false;

            }

        }

        return;

    }

    public Grid toGrid() {

        Grid grid = new Grid(51, 51, new Entity[51][51][Layer.LAYER_SIZE], null);

        RandomEnemyFactory enemyFactory = new RandomEnemyFactory();
        RandomCollectableFactory itemFactory = new RandomCollectableFactory();

        // Attach wall every where
        for (int i = 0; i < HEIGHT*2 + 1; i++) {

            for (int j = 0; j < WIDTH*2 + 1; j++) {
                if (i % 2 == 0 || j % 2 == 0) {
                    grid.attach(new Wall(new Position(j, i, Layer.STATIC)));
                }
            }
        }

        // Spawn doors
        for (int i = 0, max = rand.nextInt(4) + 3; i < max; i++) {
            //int xPortal1 = rand.nextInt(24)*2 + 2;
            //int yPortal1 = rand.nextInt(24)*2 + 2;
            int xPortal1 = rand.nextInt(50) + 1;
            int yPortal1 = rand.nextInt(50) + 1;

            grid.attach(new Door("door_locked_silver", new Position(xPortal1, yPortal1, Layer.STATIC), 1, false));
        }

        // only on hard
        if (mode.getZombieSpawnRate() < 20) {
            int xSpawner = rand.nextInt(24)*2 + 2;
            int ySpawner = rand.nextInt(24)*2 + 2;

            grid.attach(new ZombieToastSpawner(new Position(xSpawner, ySpawner, Layer.STATIC), mode));
        }

        // Add 2-4 portals

        String[] colours = {"blue", "red", "orange", "gray"};

        for (int i = 0, max = rand.nextInt(2) + 2; i < max; i++) {
            int xPortal1 = rand.nextInt(24)*2 + 2;
            int yPortal1 = rand.nextInt(24)*2 + 2;
            int xPortal2 = rand.nextInt(24)*2 + 2;
            int yPortal2 = rand.nextInt(24)*2 + 2;

            grid.attach(new Portal("portal_"+ colours[i], new Position(xPortal1, yPortal1, Layer.STATIC), colours[i]));
            grid.attach(new Portal("portal_"+ colours[i], new Position(xPortal2, yPortal2, Layer.STATIC), colours[i]));
        }



        for (int i = 1; i < HEIGHT*2; i++) {

            for (int j = 1; j < WIDTH*2; j++) {

                if (i % 2 == 1 && j % 2 == 1) {

                    int row = (i-1)/2;

                    int col = (j-1)/2;

                    double prob = rand.nextDouble();

                    // 1.5% chance to spawn in enemy
                    if (prob < 0.015) {
                        Enemy enemy = (Enemy) enemyFactory.createEntity(null);

                        if (enemy instanceof Spider) {
                            enemy.setPosition(new Position(j, i, Layer.SPIDER));
                        } else {
                            enemy.setPosition(new Position(j, i, Layer.ENEMY));
                        }

                        grid.attach(enemy);
                    }
                    // 3% chance to spawn item
                    else if (prob < 0.045) {
                        Entity item = itemFactory.createEntity(null);

                        item.setPosition(new Position(j, i, Layer.COLLECTABLE));
                        grid.attach(item);
                    // 0.5% for wood
                    }

                    // If start location add player and starts with a key
                    if (startLocation/ WIDTH == row && startLocation % WIDTH == col) {

                        grid.attach(new Player(new Position(j, i, Layer.PLAYER), mode));
                        grid.attach(new Key("key_silver", new Position(j, i, Layer.COLLECTABLE), 1));
                    }

                    // If end location add exit
                    if (endLocation/ WIDTH == row && endLocation % WIDTH == col) {

                        grid.attach(new Exit(new Position(j, i, Layer.STATIC)));

                    }

                    // if there is a connection then remove current wall
                    if ((maze[row][col] == RIGHT_ONLY_OPEN || maze[row][col] == BOTH_OPEN) && j != WIDTH*2 -1) {

                        grid.dettach(new Wall(new Position(j+1, i, Layer.STATIC)));
                    }

                    if ((maze[row][col] == DOWN_ONLY_OPEN || maze[row][col] == BOTH_OPEN) && i != HEIGHT*2 -1) {
                        grid.dettach(new Wall(new Position(j, i+1, Layer.STATIC)));
                    }
                
                }
            }
        }

        return grid;
    }
}