package necromancermod;

import necromancermod.items.NecromancerBoneWandItem;
import necromancermod.mobs.NecromancerSummonedZombie;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.Recipes;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.ingredient.ItemIngredient;
import necesse.inventory.recipe.tech.RecipeTechRegistry;
import necesse.gfx.gameTexture.GameTexture;

@ModEntry
public class NecromancerMod {

    public void init() {
        ItemRegistry.registerItem("necromancerwand", new NecromancerBoneWandItem(), 30, true);
        MobRegistry.registerMob("necromancer_summoned_zombie", NecromancerSummonedZombie.class, true);
    }

    public void initResources() {
        NecromancerSummonedZombie.texture = GameTexture.fromFile("mobs/summonedzombie");
    }

    public void postInit() {
        Recipes.registerModRecipe(new Recipe(
                "necromancerwand",
                1,
                RecipeTechRegistry.MAGICWORKSTATION,
                new ItemIngredient[]{
                        new ItemIngredient("bone", 20),
                        new ItemIngredient("ironbar", 8)
                }
        ));
    }
}
