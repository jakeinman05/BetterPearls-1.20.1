package net.notvergin.betterpearls.entities.projectiles;

import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.notvergin.betterpearls.entities.ModEntities;
import net.notvergin.betterpearls.items.ModItems;

public class ThrownRocketPearl extends ThrowableItemProjectile
{
    public ThrownRocketPearl(EntityType<? extends ThrownRocketPearl> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ThrownRocketPearl(Level pLevel, LivingEntity pShooter) {
        super(ModEntities.THROWN_ROCKET_PEARL.get(), pShooter, pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.ROCKET_PEARL.get();
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
    }

    @Override
    public void tick()
    {
        super.tick();

        if(this.isAlive())
        {
            Vec3 pearlVec = this.getDeltaMovement();
            double xOff = pearlVec.x * Math.sin((double) this.tickCount / 20);
            double yOff = pearlVec.y * Math.cos((double) this.tickCount / 20);
            double zOff = pearlVec.z * Math.sin((double) this.tickCount / 20);
            Vec3 flyRot = pearlVec.lerp(new Vec3(xOff, yOff, zOff), 1.3D);
            // somehow get this to fly in some sort of rotation, like its propelled by a firework
            this.setDeltaMovement(flyRot);

            this.level().addParticle(ParticleTypes.FIREWORK, this.getX(), this.getY(), this.getZ(), xOff, yOff, zOff);
        }

    }
}
