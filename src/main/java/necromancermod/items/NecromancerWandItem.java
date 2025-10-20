package necromancermod.items;

import necromancermod.projectiles.NecroticBoltProjectile;
import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.Projectile;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.projectileToolItem.magicProjectileToolItem.MagicProjectileToolItem;
import necesse.level.maps.Level;

public class NecromancerWandItem extends MagicProjectileToolItem {

    public NecromancerWandItem() {
        super(400);
        rarity = Rarity.RARE;
        attackAnimTime.setBaseValue(320);
        attackDamage.setBaseValue(18).setUpgradedValue(1, 95);
        velocity.setBaseValue(95);
        knockback.setBaseValue(35);
        attackRange.setBaseValue(1500);
        attackXOffset = 12;
        attackYOffset = 22;
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "necromancerwandtip"));
        return tooltips;
    }

    @Override
    public void showAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, int animAttack, int seed, GNDItemMap mapContent) {
        if (level.isClient()) {
            SoundManager.playSound(GameResources.magicbolt1, SoundEffect.effect(attackerMob)
                    .volume(0.8f)
                    .pitch(GameRandom.globalRandom.getFloatBetween(0.95f, 1.05f)));
        }
    }

    @Override
    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent) {
        Projectile projectile = new NecroticBoltProjectile(
                level,
                attackerMob,
                attackerMob.x,
                attackerMob.y,
                x,
                y,
                getProjectileVelocity(item, attackerMob),
                getAttackRange(item),
                getAttackDamage(item),
                getKnockback(item, attackerMob)
        );

        GameRandom random = new GameRandom(seed);
        projectile.resetUniqueID(random);
        projectile.moveDist(40);
        attackerMob.addAndSendAttackerProjectile(projectile);
        consumeMana(attackerMob, item);
        return item;
    }
}
