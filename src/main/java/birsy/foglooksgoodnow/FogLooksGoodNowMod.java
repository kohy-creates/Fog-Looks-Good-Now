package birsy.foglooksgoodnow;

import birsy.foglooksgoodnow.client.FogManager;
import birsy.foglooksgoodnow.config.FogLooksGoodNowConfig;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(FogLooksGoodNowMod.MODID)
public class FogLooksGoodNowMod {

    public static final String MODID = "foglooksgoodnow";
    public static final Logger LOGGER = LogUtils.getLogger();

    public FogLooksGoodNowMod() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, FogLooksGoodNowConfig.CLIENT_CONFIG, MODID + ".toml");
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::onConfigLoad);
        modEventBus.addListener(this::onConfigReload);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onConfigLoad(ModConfigEvent.Loading event) {
        FogManager.getDensityManagerOptional().ifPresent((FogManager::initializeConfig));
    }

    public void onConfigReload(ModConfigEvent.Reloading event) {
        FogManager.getDensityManagerOptional().ifPresent((FogManager::initializeConfig));
    }
}

