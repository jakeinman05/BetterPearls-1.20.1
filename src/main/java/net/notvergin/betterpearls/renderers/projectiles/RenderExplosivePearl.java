package net.notvergin.betterpearls.renderers.projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.notvergin.betterpearls.entities.projectiles.ThrownExplosivePearl;

import static net.notvergin.betterpearls.BetterPearls.MODID;

public class RenderExplosivePearl extends EntityRenderer<ThrownExplosivePearl>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(MODID, "textures/item/explosive_pearl.png");

    public RenderExplosivePearl(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public void render(ThrownExplosivePearl pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);

        Minecraft.getInstance().getTextureManager().bindForSetup(TEXTURE);


    }

    @Override
    public ResourceLocation getTextureLocation(ThrownExplosivePearl pEntity) {
        return TEXTURE;
    }
}
