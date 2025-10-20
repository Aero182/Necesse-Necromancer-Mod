package necromancermod.items;

import necromancermod.mobs.NecromancerSummonedZombie;
import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.SummonedMob;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.ManaCost;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.level.maps.Level;

public class NecromancerBoneWandItem extends SummonToolItem {

    public NecromancerBoneWandItem() {
        super(400);
        rarity = Rarity.RARE;
        attackAnimTime.setBaseValue(320);
        attackDamage.setBaseValue(18).setUpgradedValue(1, 85);
        knockback.setBaseValue(35);
        attackRange.setBaseValue(1100);
        attackXOffset = 12;
        attackYOffset = 22;
        manaCost = new ManaCost(35);
        maxSummons = 1;
        itemTexture = GameTexture.fromFile("items/necromancerwand");
    }

    @Override
    public void setupAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, int animAttack, int seed, GNDItemMap mapContent) {
        super.setupAttack(level, x, y, attackerMob, attackHeight, item, animAttack, seed, mapContent);
        consumeMana(attackerMob, item);
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "necromancerwandtip"));
        return tooltips;
    }

    @Override
    public SummonedMob summon(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, int animAttack, int seed, GNDItemMap mapContent) {
        NecromancerSummonedZombie summon = new NecromancerSummonedZombie();
        GameRandom random = new GameRandom(seed);
        summon.resetUniqueID(random);
        summon.setLevel(level);
        summon.setPosition(x, y);
        return summon;
    }
}
