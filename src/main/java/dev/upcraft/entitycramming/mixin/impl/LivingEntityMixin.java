package dev.upcraft.entitycramming.mixin.impl;

import dev.upcraft.entitycramming.ExplosionHelper;
import dev.upcraft.entitycramming.VolatileEntityCramming;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Redirect(method = "tickCramming", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private boolean vec_tickCramming(LivingEntity livingEntity, DamageSource source, float amount) {
        if (ExplosionHelper.shouldExplode(livingEntity, source, amount)) {
            ExplosionHelper.createExplosionAt(livingEntity, source, amount);
            return true;
        } else {
            return livingEntity.damage(source, amount);
        }
    }

    @Inject(method = "damage", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/LivingEntity;despawnCounter:I", opcode = Opcodes.PUTFIELD, shift = At.Shift.AFTER))
    private void vec_damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if(VolatileEntityCramming.getConfig().explodeOnAllCrammingDamage && ExplosionHelper.shouldExplode((LivingEntity)(Object) this, source, amount)) {
            ExplosionHelper.createExplosionAt((LivingEntity)(Object) this, source, amount);
            cir.setReturnValue(true);
        }
    }
}
