package dev.upcraft.entitycramming;

import io.github.glasspane.mesh.api.util.config.ConfigHandler;
import net.fabricmc.api.ModInitializer;

public class VolatileEntityCramming implements ModInitializer {

    public static final String MODID = "vec";

    public static VecConfig getConfig() {
        return ConfigHandler.getConfig(VecConfig.class);
    }

    @Override
    public void onInitialize() {
        ConfigHandler.registerConfig(MODID, "VolatileEntityCramming", VecConfig.class);
    }
}
