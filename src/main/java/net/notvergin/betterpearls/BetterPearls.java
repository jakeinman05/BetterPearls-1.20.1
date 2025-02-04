package net.notvergin.betterpearls;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.notvergin.betterpearls.entities.ModEntities;
import net.notvergin.betterpearls.items.ModItems;
import net.notvergin.betterpearls.renderers.entities.RenderBanana;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BetterPearls.MODID)
public class BetterPearls
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "betterpearls";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // empty constructor for forge to run
    public BetterPearls() {}

    public BetterPearls(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        ModEntities.register(modEventBus);
        ModItems.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {}

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES)
        {
            // add pearls to creative tab here
            event.accept(ModItems.SUICIDE_PEARL);
            event.accept(ModItems.BANANA_PEARL);
            event.accept(ModItems.BLOCK_PEARL);
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(ModEntities.BANANA_ENTITY.get(), RenderBanana::new);
        }
    }
}
