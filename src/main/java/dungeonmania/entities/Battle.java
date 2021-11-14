ettach(enemy);
        } else {
            while (true) {
                // start battle
                // enemy attack first
                if (!player.getPeaceful()) {
                    int enemyDamageDealt = enemyDamage(player, enemy);
                    player.receiveDamage(enemyDamageDealt);
                }

                if (!player.isDead()){
                    if (!player.hasAnduril()) {
                        int playerDamageDealt = playerDamage(player, enemy);
                        enemy.receiveDamage(playerDamageDealt);
                        // player with bow attack again
                        if (player.hasBow() && !enemy.isDead()) {
                            enemy.receiveDamage(playerDamageDealt);
                            player.useBow();
                        }
                        // if player has an ally mercenary, it will attack as well
                        int mercDamageDealt = MercDamage(player, enemy);
                        enemy.receiveDamage(mercDamageDealt);
                    } else {
                        int playerDamageDealt = playerDamage(player, enemy);
                        enemy.receiveAndruilDamage(playerDamageDealt);
                        // if player has a bow and and an anduril, the second attack 
                        // with the bow will still count as an attack with andruil
                        if (player.hasBow() && !enemy.isDead()) {
                            enemy.receiveAndruilDamage(playerDamageDealt);
                            player.useBow();
                        }
                        // mecenary attack does not count as an attack with anduril
                        int mercDamageDealt = MercDamage(player, enemy);
                        enemy.receiveDamage(mercDamageDealt);
                    }

                    if (enemy.isDead()) {
                        grid.dettach(enemy);
                        if (player.isRareDrop()) {
                            RareCollectableEntities.spawnnRareCollectableEntities(player);
                        }
                        break;
                    }
                } else if (player.isDead()){
                    playerDies(player, enemy, grid);
                    
                    break;
                }
            }
        }
    }

    /**
     * player is dead
     * if player has the One Ring, regenerate to full health
     */
    private static void playerDies(Player player, Entity enemy, Grid grid) {
        if (player.getInventory().checkItem("one_ring") > 0) {
            player.setCurrentHealth(player.getMaxHealth());
            player.getInventory().removeNonSpecificItem("one_ring");
            grid.dettach(enemy);
        } else {
            // player dies   
            grid.dettach(player);
        }
    }

    private static int playerDamage(Player player, Entity enemy) {
        // calculate character base damage
        int damage =  player.damageDealt();

        // add weapon damage if player has any weapon
        damage += player.useSword();
        damage += player.useAnduril();
        damage += player.useMidnightArmour();

        // player with anduril deal triple damage against bosses
        if (player.hasAnduril() && (enemy instanceof Hydra  || enemy instanceof Assassin)) {
            damage *= 3;
        }
        // TODO: uncomment once zombie with armour is inplemented
        // if (enemy instanceof Zombie && (Zombie)enemy.hasArmour()) {
        //     playerDamageDealt = playerDamageDealt / 2;
        // }
        return damage;
    }

    private static int MercDamage(Player player, Entity enemy) {
        int damage = 0;
        if (player.hasMercAlly()) {
            // mercenary as an ally also use character's damage formula
            damage = (player.getMerc().getHealth() * player.getMerc().getDamage())/ 5;
        }
        // TODO: uncomment once zombie with armour is inplemented
        // if (enemy instanceof Zombie && (Zombie)enemy.hasArmour()) {
        //     playerDamageDealt = playerDamageDealt / 2;
        // }
        return damage;
    }

    private static int enemyDamage(Player player, Enemy enemy) {
        int damage = enemy.damageDealt();
        // damage is deducted by the shield's defense if layer have one 
        damage = damage - player.getShieldDefense();
        player.useShield();
        damage = damage - player.getMidnightArmourDefense();
        // armour halves enemy damage
        if (player.hasArmour()) {
            damage = damage / 2;
        }
        // enemy damage can not go negative 
        player.useArmour();
        if (damage < 0) {
            damage = 0;
        }
        return damage;
    }

    private static void playerOlderSelfBattle(Player player, OlderSelf olderSelf, Grid grid) {
        if (player.getStatusEffect().isInvisible() || olderSelf.getStatusEffect().isInvisible()) {
            
        } else if (player.getStatusEffect().isInvincible()) {
            grid.dettach(olderSelf);
        } else if (olderSelf.getStatusEffect().isInvincible()) {
            grid.dettach(player);
        } else {
            // start battle
            // enemy attack first
            int enemyDamageDealt = playerDamage(olderSelf, player);
            player.receiveDamage(enemyDamageDealt);

            if (!player.isDead()){
                int playerDamageDealt = playerDamage(player, olderSelf);
                olderSelf.receiveDamage(playerDamageDealt);
                // player with bow attack again
                if (player.hasBow() && !olderSelf.isDead()) {
                    olderSelf.receiveDamage(playerDamageDealt);
                }
                // if player has an ally mercenary, it will attack as well
                int mercDamageDealt = MercDamage(player, olderSelf);
                olderSelf.receiveDamage(mercDamageDealt);

                if (olderSelf.isDead()) {
                    grid.dettach(olderSelf);
                    if (player.isRareDrop()) {
                        RareCollectableEntities.spawnnRareCollectableEntities(player);
                    }
                }
            } else {
                playerDies(player, olderSelf, grid);
            }
        }
    }
}
