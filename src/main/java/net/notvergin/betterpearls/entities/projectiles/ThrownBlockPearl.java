package net.notvergin.betterpearls.entities.projectiles;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.notvergin.betterpearls.entities.ModEntities;
import net.notvergin.betterpearls.items.ModItems;
import org.jetbrains.annotations.NotNull;

public class ThrownBlockPearl extends ThrowableItemProjectile
{
    public ThrownBlockPearl(EntityType<? extends ThrownBlockPearl> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ThrownBlockPearl(Level pLevel, LivingEntity pLivingEntity) {
        super(ModEntities.THROWN_BLOCK_PEARL.get(), pLivingEntity, pLevel);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ModItems.BLOCK_PEARL.get();
    }

    @Override
    protected void onHit(HitResult pResult)
    {
        super.onHit(pResult);
        for(int i = 0; i < 32; ++i) {
            this.level().addParticle(ParticleTypes.PORTAL, this.getX(), this.getY() + this.random.nextDouble() * 2.0D, this.getZ(), this.random.nextGaussian(), 0.0D, this.random.nextGaussian());
        }

        if (!this.level().isClientSide && !this.isRemoved()) {
            Entity entity = this.getOwner();
            if (entity instanceof ServerPlayer) {
                ServerPlayer serverplayer = (ServerPlayer)entity;
                if (serverplayer.connection.isAcceptingMessages() && serverplayer.level() == this.level() && !serverplayer.isSleeping()) {
                    if (entity.isPassenger()) {
                        serverplayer.dismountTo(this.getX(), this.getY(), this.getZ());
                    } else {
                        entity.teleportTo(this.getX(), this.getY(), this.getZ());
                    }

                    entity.teleportTo(this.getX(), this.getY(), this.getZ());
                    entity.resetFallDistance();

                    BlockPos playerPos = serverplayer.blockPosition();
                    BlockPos blockPos = new BlockPos(playerPos.getX(), playerPos.getY()-1, playerPos.getZ());
                    BlockState state = Blocks.END_STONE.defaultBlockState();
                    serverplayer.level().broadcastEntityEvent(this, ((byte) 3));
                    serverplayer.level().setBlock(blockPos, state, 3);
                }
            } else if (entity != null) {
                entity.teleportTo(this.getX(), this.getY(), this.getZ());
                entity.resetFallDistance();

                BlockPos playerPos = entity.blockPosition();
                BlockPos blockPos = new BlockPos(playerPos.getX(), playerPos.getY()-1, playerPos.getZ());
                BlockState state = Blocks.END_STONE.defaultBlockState();
                entity.level().broadcastEntityEvent(this, ((byte) 3));
                entity.level().setBlock(blockPos, state, 3);
            }

            this.discard();
        }
    }
}
