package dungeonmania.entities;

import java.util.Random;

import com.google.gson.JsonObject;

import dungeonmania.entities.collectable.*;

public class RandomCollectableFactory implements EntityFactory {

    private Random rand = new Random();

    @Override
    /**
     * 10% Sword
     * 20% Treasure
     * 19% Arrow
     * 18% Wood
     * 1% Sun Stone
     * 8% Silver Key
     * 8% HealthPotion
     * 3% Invis
     * 1% Invinc
     * 12% Bomb
     * 
     */
    public Entity createEntity(JsonObject entityData) {
        
        double prob = rand.nextDouble();

        if (prob < 0.10) {

            return new Sword(null);
        } else if (prob < 0.30) {

            return new Treasure(null);
        } else if (prob < 0.49) {
            
            return new Arrow(null);
        } else if (prob < 0.67) {

            return new Wood(null);
        } else if (prob < 0.68) {

            return new SunStone(null);
        } else if (prob < 0.76) {

            return new Key("key_silver", null, 1);
        } else if (prob < 0.84) {

            return new HealthPotion(null);
        } else if (prob < 0.87) {

            return new InvisibilityPotion(null);
        } else if (prob < 0.88) {

            return new InvincibilityPotion(null);
        } else {

            return new Bomb(null);
        }
    }
    
}
