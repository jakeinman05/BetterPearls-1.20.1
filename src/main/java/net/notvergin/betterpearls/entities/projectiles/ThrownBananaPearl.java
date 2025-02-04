package net.notvergin.betterpearls.entities.projectiles;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.notvergin.betterpearls.entities.BananaEntity;
import net.notvergin.betterpearls.entities.ModEntities;
import net.notvergin.betterpearls.items.ModItems;

import javax.annotation.Nullable;

public class ThrownBananaPearl extends ThrowableItemProjectile
{
    public ThrownBananaPearl(EntityType<? extends ThrownBananaPearl> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ThrownBananaPearl(Level pLevel, LivingEntity pLivingEntity) {
        super(ModEntities.THROWN_BANANA_PEARL.get(), pLivingEntity, pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.BANANA_PEARL.get();
    }

    @Override
    protected void onHit(HitResult pResult) {
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
                    this.level().playSound(null, this.blockPosition(), SoundEvents.SLIME_SQUISH, SoundSource.NEUTRAL, 1.0F, 1.0F);
                    spawnBanana();
                }
            } else if (entity != null) {
                entity.teleportTo(this.getX(), this.getY(), this.getZ());
                entity.resetFallDistance();
                this.level().playSound(null, this.blockPosition(), SoundEvents.SLIME_SQUISH, SoundSource.NEUTRAL, 1.0F, 1.0F);
                spawnBanana();
            }
            this.discard();
        }
        this.discard();
    }

    public void tick() {
        Entity entity = this.getOwner();

        if (entity instanceof Player && !entity.isAlive()) {
            this.discard();
        } else {
            super.tick();
        }

    }

    @Nullable
    public Entity changeDimension(ServerLevel pServer, net.minecraftforge.common.util.ITeleporter teleporter) {
        Entity entity = this.getOwner();
        if (entity != null && entity.level().dimension() != pServer.dimension()) {
            this.setOwner((Entity)null);
        }

        return super.changeDimension(pServer, teleporter);
    }

    private void spawnBanana()
    {
        EntityType<BananaEntity> entityType = ModEntities.BANANA_ENTITY.get();
        if (!this.level().isClientSide) {
            BananaEntity banana = entityType.create(this.level());
            if(banana != null)
            {
                banana.setPos(this.getX(), this.getY(), this.getZ());
                banana.setOwner(this.getOwner() instanceof LivingEntity ? (LivingEntity)this.getOwner() : null);
                this.level().addFreshEntity(banana);
            }
        }
    }
}
