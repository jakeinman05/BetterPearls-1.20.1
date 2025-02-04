package net.notvergin.betterpearls.entities.projectiles;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.notvergin.betterpearls.entities.ModEntities;
import net.notvergin.betterpearls.items.ModItems;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ThrownHomingPearl extends ThrowableItemProjectile
{
    public ThrownHomingPearl(EntityType<? extends ThrownHomingPearl> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ThrownHomingPearl(Level pLevel, LivingEntity pShooter) {
        super(ModEntities.THROWN_HOMING_PEARL.get(), pShooter, pLevel);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ModItems.HOMING_PEARL.get();
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
                }
            } else if (entity != null) {
                entity.teleportTo(this.getX(), this.getY(), this.getZ());
                entity.resetFallDistance();
            }

            this.discard();
        }
    }

    public void tick() {
        Entity entity = this.getOwner();

        if(entity instanceof ServerPlayer serverplayer)
        {
            Vec3 playerLook = serverplayer.getLookAngle().normalize();
            Vec3 pearlVec = this.getDeltaMovement();

            double lerp = 0.5;
            Vec3 newVec = pearlVec.lerp(playerLook.scale(1.5), lerp);

            this.setDeltaMovement(newVec);
        }

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
}
