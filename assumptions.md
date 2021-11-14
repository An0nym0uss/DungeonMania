# Assumptions

## `Mode`
* Can still use invincibility potion in hard mode, but does nothing.
* We set our own standard player health and hard player health.

## `Dungeon`
* We allow for the dungeon to become unsolvable, e.g, blow up exit with a bomb.

## `Player`
* Player can only hold one sword, one bow and one shield.
* Player can do damage without a weapon.

## `Entities`
* Enemies entities cannot spawn on the grid where collectable entities are placed on the grid.
* Battles are simultaneous, so even if a player dies during a battle, it still does damage to the enemy. This is only important if a player respawns.
* Entities cannot move into bomb.
* Each moving entity have their own individual damage and health.

## `Boulders`
* Boulders can be pushed into exit, open doors, and portals, blocking anyone who tries to enter.

## `Portals`
* Assumed that portal type is based on the colour, e.g, portal_blue, portal_red.

## `Grid`
* Entities cannot move out of the bound of the grid. By default if not specified, the grid is 15x15 in size.
* Grid has layers, 5 layers, one for static entities, collectables, moving enitiies (including boulders), spiders, and player.
* Entities in the same layer cannot move into each other.

## `.json`
* The json file should always have a key called entities with a corresponding json array value with it.
* If the json file is formatted correctly, then it should always have entities stored as the correct format.
* Assumed that entity names that are more than one work have underscores between it word, such as zombie_toast_spawner.

## `Mercenary`
* Mercenary only needs one coin to be bribed.
* If the mercenary cannot move such that its closer to the player, then it doesn't move at all.
* Mind control mercenary or assassin is trigged through interact and if the player has the sceptre and treasures at the same time then the mercenary will be mind conrtrolled instead of bribed.
* Mind control a bribed mercenary will have no effect but mercenary will gladly take more treasure even if it has been bribed already.
* Mind control already mind controlled enemies using sceptre does not refresh the mind control duration.

## `Potion effects`
* When the player use invincibility potion or invisibility potion when their effect is still on, their duration will reset.

## `Door`
* Can walk through open doors.
* Door type is named after type of key it uses of if its open, e.g. "silver_locked_door", "open_door"

## `Key`
* If a key is used to craft a shield, it is removed from the inventory. Yes, you cannot make the game unsolvable if you craft a key instead of using it on a door.
* Key type is named after type of metal/colour it is, e.g. "silver_key", "gold_key" etc.

## `One Ring`
* When one ring is used, player heal back to full health and kill the current enemy.

## `Bomb`
* Bomb is in the same layer as enemies, so it can be placed on exit, switch, portal, and open door.

## `Spider`
* The spawn rate for spiders is every 30 ticks, until they reach a maximum of 4 spiders on the grid.

## `Hydra`
* Hydra can regenerate above its max health.
* Can only spawn one hydra at a time

## `Sun Stone Items`
* Sceptre and mignight armour never breaks.

## `Anduril`
* Anduril will never break.
* Does 3x damage to bosses after all damage bonuses are added.

## `Battles`
* Sword damage count as bonus damage instead of character damage.
* Bonus damage or defense that are additive in nature (e.g. sword damage) are calculated before ones that are multiplicative in nature(e.g. anduril damage against bosses).
* Weapon and armour durability is decreased per usage. 
* Health of enemy can go negative temporarily but cannot be revived once it went negative, i.e. when attacking hydra with a bow if the first attack reduced the hydra's health below zero then the hydra is killed regardless of the outcome of the second attack.
* The minimum damage player can receive is zero.

## `Swamp Tiles`
* Boulder is not affected by swamp tile, only enemy entities are.
* A swamp tiles movement factor must be >= 1.
* A spider moving through swamp tiles still moves in a circle, just takes more ticks to do it
movement factor of 1 means swamp tile does nothing.

## `Time Travel`
* Multiple older selves cannot appear in the same cell.
* Older selves cannot use items such as potions or bombs.
* Older selves can unlock doors and push boulders just like player.
* When the game rewinds, bribed mercenaries become unbribed.

## `Generating Dungeon`
* Maze is (0,0) to (50,50) inclusive, so sized 51 by 51.

## `Saving`
* Saving game resets ticks.
