package net.notvergin.betterpearls.renderers.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.notvergin.betterpearls.entities.BananaEntity;
import net.notvergin.betterpearls.entities.model.BananaModel;
import net.notvergin.betterpearls.entities.model.ModModelLayers;
import org.jetbrains.annotations.NotNull;

import static net.notvergin.betterpearls.BetterPearls.MODID;

public class RenderBanana extends EntityRenderer<BananaEntity>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(MODID, "textures/entity/banana_peel.png");
    private final BananaModel<BananaEntity> model;

    public RenderBanana(EntityRendererProvider.Context context) {
        super(context);
        this.model = new BananaModel<>(context.bakeLayer(ModModelLayers.BANANA_LAYER_LOCATION));
    }

    @Override
    public void render(BananaEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        poseStack.translate(0.0, 1.5, 0.0);
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));

        // Render the model
        VertexConsumer vertexConsumer = buffer.getBuffer(this.model.renderType(TEXTURE));
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(BananaEntity entity) {
        return TEXTURE;
    }
}
