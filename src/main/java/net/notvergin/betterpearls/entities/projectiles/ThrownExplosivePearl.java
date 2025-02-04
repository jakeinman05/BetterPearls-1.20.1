package net.notvergin.betterpearls.entities.projectiles;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.notvergin.betterpearls.entities.ModEntities;
import net.notvergin.betterpearls.items.ModItems;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ThrownExplosivePearl extends ThrowableItemProjectile
{
    // used for registry
    public ThrownExplosivePearl(EntityType<? extends ThrownExplosivePearl> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    // used for summoning from thrown pearl
    public ThrownExplosivePearl(Level pLevel, LivingEntity pLivingEntity) {
        super(ModEntities.THROWN_SUICIDE_PEARL.get(), pLivingEntity, pLevel);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ModItems.SUICIDE_PEARL.get();
    }

    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        pResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.0F);
    }

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
                    serverplayer.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2));
                    this.level().explode(
                            null,
                            this.getX(),
                            this.getY(),
                            this.getZ(),
                            3.0F,
                            Level.ExplosionInteraction.TNT
                    );
                }
            } else if (entity != null) {
                entity.teleportTo(this.getX(), this.getY(), this.getZ());
                entity.resetFallDistance();
                this.level().explode(
                        null,
                        this.getX(),
                        this.getY(),
                        this.getZ(),
                        3.0F,
                        Level.ExplosionInteraction.TNT
                );
            }

            this.discard();
        }
    }


    public void tick() {
        Entity entity = this.getOwner();

        if(this.isAlive())
        {
            double x = this.getX();
            double y = this.getY();
            double z = this.getZ();
            this.level().addParticle(ParticleTypes.SMOKE, x, y, z, 0, 0, 0);
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
