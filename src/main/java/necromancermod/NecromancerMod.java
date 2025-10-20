package necromancermod;

import necromancermod.items.NecromancerWandItem;
import necromancermod.mobs.NecromancerSummonedZombie;
import necromancermod.mobs.SummonedZombieMob;
import necromancermod.projectiles.NecroticBoltProjectile;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.ProjectileRegistry;
import necesse.gfx.gameTexture.GameTexture;

@ModEntry
public class NecromancerMod {

    public void init() {
        ItemRegistry.registerItem("necromancerwand", new NecromancerWandItem(), 30, true);
        MobRegistry.registerMob("summonedzombie", SummonedZombieMob.class, true);
        MobRegistry.registerMob("necromancer_summoned_zombie", NecromancerSummonedZombie.class, true);
        ProjectileRegistry.registerProjectile("necroticbolt", NecroticBoltProjectile.class, "necroticbolt", "necroticbolt_shadow");
    }

    public void initResources() {
        SummonedZombieMob.texture = GameTexture.fromFile("mobs/summonedzombie");
        NecromancerSummonedZombie.texture = GameTexture.fromFile("mobs/summonedzombie");
    }

    public void postInit() {
        // Additional setup can be added here when new necromancer content is introduced.
    }
}
