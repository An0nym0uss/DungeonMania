package dungeonmania.entities;

import java.util.Random;

import com.google.gson.JsonObject;

import dungeonmania.entities.enemy.Assassin;
import dungeonmania.entities.enemy.Mercenary;
import dungeonmania.entities.enemy.Spider;
import dungeonmania.entities.enemy.Zombie;

public class RandomEnemyFactory implements EntityFactory {

    private Random rand = new Random();

    @Override
    /**
     * 60% chance for spider
     * 32% chance for zombie
     *  7% chance for mercenary
     *      - 30% (2.1%) chance for Assassin to spawn in place of mercenary
     *      - 70% (4.9%) chance for Mercenary
     */
    public Entity createEntity(JsonObject entityData) {
        
        double prob = rand.nextDouble();

        if (prob < 0.60) {

            return new Spider(null, 1, 50, 1);
        } else if (prob < 0.93) {

            return new Zombie(null, 1, 100, 1);
        } else {
            double prob2 = rand.nextDouble();
            if (prob2 < 0.30) {
                return new Assassin(null, 1,100,3);
            }
            return new Mercenary(null, 1, 50, 2);
        }
    }
    
}
