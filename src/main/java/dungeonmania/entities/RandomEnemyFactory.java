package dungeonmania.entities;

import java.util.Random;

import com.google.gson.JsonObject;

import dungeonmania.entities.enemy.Mercenary;
import dungeonmania.entities.enemy.Spider;
import dungeonmania.entities.enemy.Zombie;

public class RandomEnemyFactory implements EntityFactory {

    private Random rand = new Random();

    @Override
    /**
     * 60% chance for spide
     * 32% chance for zombie
     *  7% chance for mercenary
     */
    public Entity createEntity(JsonObject entityData) {
        
        double prob = rand.nextDouble();

        if (prob < 0.60) {

            return new Spider(null, 1, 1, 1);
        } else if (prob < 0.93) {

            return new Zombie(null, 1, 1, 1);
        } else {

            return new Mercenary(null, 1, 1, 1);
        }
    }
    
}
