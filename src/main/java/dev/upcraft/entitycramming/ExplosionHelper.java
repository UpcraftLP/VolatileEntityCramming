package dev.upcraft.entitycramming;

import io.github.glasspane.mesh.util.collections.CollectionHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Random;

public class ExplosionHelper {

    public static boolean shouldExplode(LivingEntity entity, DamageSource source, float damageAmount) {
        return entity.isAlive() && source == DamageSource.CRAMMING && !entity.isSpectator();
    }

    public static void createExplosionAt(LivingEntity entity, DamageSource source, float damageAmount) {
        playExplosionSound(entity);
        VecConfig config = VolatileEntityCramming.getConfig();
        entity.getEntityWorld().createExplosion(entity, entity.getX(), entity.getY(), entity.getZ(), Math.max(0.0001F, config.explosionPower), config.blockBreaking.toDestructionType());
        entity.damage(DamageSource.explosion(entity), Float.MAX_VALUE);
        if (!entity.isDead()) { // kill the entity, even if it is immune to explosion damage
            entity.kill();
        }
    }

    private static void playExplosionSound(LivingEntity entity) {
        VecConfig config = VolatileEntityCramming.getConfig();
        if (config.explosionSounds.length > 0) {
            Random entityRandom = entity.getRandom();
            Identifier soundID = CollectionHelper.getRandomElement(entityRandom, VolatileEntityCramming.getConfig().explosionSounds);
            SoundEvent soundEvent = Registry.SOUND_EVENT.getOrEmpty(soundID).orElseGet(() -> {
                VolatileEntityCramming.LOGGER.warn("Unknown sound event: {}, please check your config!", soundID);
                return SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE;
            });
            entity.playSound(soundEvent, 10.0F, (entityRandom.nextFloat() - entityRandom.nextFloat()) * 0.2F + 1.0F);
        }
    }
}
