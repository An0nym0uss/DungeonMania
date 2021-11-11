package dungeonmania.entities;

import java.util.Random;

import com.google.gson.JsonObject;

import dungeonmania.entities.collectable.*;

public class RandomCollectableFactory implements EntityFactory {

    private Random rand = new Random();

    @Override
    /**
     * 13% Sword
     * 21% Treasure
     * 18% Arrow
     * 19% Wood
     * 1% Sun Stone
     * 8% Silver Key
     * 10% HealthPotion
     * 6% Invis
     * 4% Invinc
     * 
     */
    public Entity createEntity(JsonObject entityData) {
        
        double prob = rand.nextDouble();

        if (prob < 0.13) {

            return new Sword(null);
        } else if (prob < 0.34) {

            return new Treasure(null);
        } else if (prob < 0.52) {
            
            return new Arrow(null);
        } else if (prob < 0.71) {

            return new Wood(null);
        } else if (prob < 0.72) {

            return new SunStone(null);
        } else if (prob < 0.80) {

            return new Key("key_silver", null, 1);
        } else if (prob < 0.90) {

            return new HealthPotion(null);
        } else if (prob < 0.96) {

            return new InvisibilityPotion(null);
        } else {

            return new InvincibilityPotion(null);
        }
    }
    
}
