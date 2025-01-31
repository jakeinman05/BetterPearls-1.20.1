package net.notvergin.betterpearls.entities.model;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.notvergin.betterpearls.BetterPearls.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModModelLayers
{
    public static final ModelLayerLocation BANANA_LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(MODID, "banana_peel"), "main");

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BANANA_LAYER_LOCATION, BananaModel::createBodyLayer);
    }
}
