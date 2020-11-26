package dev.upcraft.entitycramming.mixin.impl;

import dev.upcraft.entitycramming.VolatileEntityCramming;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(GameRules.class)
public class GameRulesMixin {

    @SuppressWarnings("UnresolvedMixinReference")
    @ModifyConstant(method = "<clinit>", constant = @Constant(ordinal = 0), slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=maxEntityCramming")))
    private static int vec_updateEntityCrammingDefault(int value) {
        int configValue = VolatileEntityCramming.getConfig().defaultMaxEntityCramming;
        if(configValue > 1) {
            VolatileEntityCramming.LOGGER.debug("Updating default value for maxEntityCramming game rule. Before: {}; After: {}", value, configValue);
            return configValue;
        }
        return value;
    }
}
