package net.notvergin.betterpearls.entities.projectiles;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.notvergin.betterpearls.entities.BananaEntity;
import net.notvergin.betterpearls.entities.ModEntities;
import net.notvergin.betterpearls.items.ModItems;

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

        if(!this.level().isClientSide() && (pResult.getType() == HitResult.Type.BLOCK || pResult.getType() == HitResult.Type.ENTITY))
        {
            BananaEntity banana = new BananaEntity(ModEntities.BANANA_ENTITY.get(), this.level());
            banana.setPos(pResult.getLocation().x(), pResult.getLocation().y(), pResult.getLocation().z());
            banana.setOwner(this.getOwner() instanceof LivingEntity ? (LivingEntity)this.getOwner() : null);
            this.level().addFreshEntity(banana);
        }

        this.discard();
    }
}
