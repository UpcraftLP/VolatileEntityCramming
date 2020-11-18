package dev.upcraft.entitycramming;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.explosion.Explosion;

public class ExplosionHelper {

    public static boolean shouldExplode(LivingEntity entity, DamageSource source, float damageAmount) {
        return entity.isAlive() && source == DamageSource.CRAMMING && !entity.isSpectator();
    }

    public static void createExplosionAt(LivingEntity entity, DamageSource source, float damageAmount) {
        // TODO config for power and destruction type
        entity.getEntityWorld().createExplosion(entity, entity.getX(), entity.getY(), entity.getZ(), 2.0F, Explosion.DestructionType.NONE);
        entity.damage(DamageSource.explosion(entity), Float.MAX_VALUE);
        if(!entity.isDead()) { // kill the entity, even if it is immune to explosion damage
            entity.kill();
        }
    }
}
