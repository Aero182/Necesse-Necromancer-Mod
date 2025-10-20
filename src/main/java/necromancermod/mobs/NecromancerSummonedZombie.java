package necromancermod.mobs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.SummonedFollowerTargetAI;
import necesse.entity.mobs.ai.behaviourTree.trees.SummonedFollowerTargetAI.SummonMeleeAttack;
import necesse.entity.mobs.ai.behaviourTree.trees.SummonedFollowerTargetAI.SummonMoveToOwner;
import necesse.entity.mobs.ai.behaviourTree.trees.SummonedFollowerTargetAI.SummonSelectTarget;
import necesse.entity.mobs.summon.SummonedMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.NoLootItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

public class NecromancerSummonedZombie extends SummonedMob {

    public static GameTexture texture;

    private static final GameDamage MELEE_DAMAGE = new GameDamage(28);
    private static final int ATTACK_RANGE = 12 * 32;
    private static final int ATTACK_COOLDOWN = 25;

    public static final LootTable lootTable = new LootTable(
            NoLootItem.noLoot()
    );

    public NecromancerSummonedZombie() {
        super(220);
        setSpeed(55);
        setFriction(3);
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-14, -12, 28, 24);
        selectBox = new Rectangle(-14, -7 - 34, 28, 48);
    }

    @Override
    public void init() {
        super.init();
        ai = new BehaviourTreeAI<>(this, new SummonedFollowerTargetAI<>(
                this,
                new SummonMoveToOwner(12 * 32, 40),
                new SummonSelectTarget(ATTACK_RANGE, true),
                new SummonMeleeAttack(MELEE_DAMAGE, ATTACK_COOLDOWN, 20, 1200)
        ));
    }

    @Override
    public LootTable getLootTable() {
        return lootTable;
    }

    @Override
    public void spawnDeathParticles(float knockbackX, float knockbackY) {
        for (int i = 0; i < 4; i++) {
            getLevel().entityManager.addParticle(new FleshParticle(
                    getLevel(),
                    texture,
                    GameRandom.globalRandom.nextInt(5),
                    8,
                    32,
                    x,
                    y,
                    20f,
                    knockbackX,
                    knockbackY
            ), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    @Override
    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileX(), getTileY());
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 51;
        Point sprite = getAnimSprite(x, y, getDir());
        drawY += getBobbing(x, y);
        drawY += getLevel().getTile(getTileX(), getTileY()).getMobSinkingAmount(this);
        DrawOptions drawOptions = texture.initDraw()
                .sprite(sprite.x, sprite.y, 64)
                .light(light)
                .pos(drawX, drawY);
        list.add(new MobDrawable() {
            @Override
            public void draw(TickManager tickManager) {
                drawOptions.draw();
            }
        });
        addShadowDrawables(tileList, x, y, light, camera);
    }

    @Override
    public int getRockSpeed() {
        return 20;
    }
}
