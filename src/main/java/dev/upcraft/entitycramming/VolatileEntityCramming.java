package dev.upcraft.entitycramming;

import io.github.glasspane.mesh.api.util.config.ConfigHandler;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VolatileEntityCramming implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger("VolatileEntityCramming");
    public static final String MODID = "vec";

    public static VecConfig getConfig() {
        return ConfigHandler.getConfig(VecConfig.class);
    }

    @Override
    public void onInitialize() {
        ConfigHandler.registerConfig(MODID, "VolatileEntityCramming", VecConfig.class);
    }
}
