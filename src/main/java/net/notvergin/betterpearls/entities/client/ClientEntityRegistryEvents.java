package net.notvergin.betterpearls.entities.client;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.notvergin.betterpearls.entities.ModEntities;
import net.notvergin.betterpearls.renderers.entities.RenderBanana;

import static net.notvergin.betterpearls.BetterPearls.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEntityRegistryEvents
{
    @SubscribeEvent
    public static void onClientSetup(EntityRenderersEvent.RegisterRenderers event)
    {
        // render each entity on client setup
        EntityRenderers.register(ModEntities.THROWN_SUICIDE_PEARL.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.THROWN_BANANA_PEARL.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.BANANA_ENTITY.get(), RenderBanana::new);
        EntityRenderers.register(ModEntities.THROWN_BLOCK_PEARL.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.THROWN_HOMING_PEARL.get(), ThrownItemRenderer::new);
    }
}
