package net.notvergin.betterpearls.entities;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BananaEntity extends AmbientCreature implements TraceableEntity
{
    private static final EntityDataAccessor<Integer> DATA_LIFESPAN_ID = SynchedEntityData.defineId(BananaEntity.class, EntityDataSerializers.INT);
    private static final int DEFAULT_LIFESPAN = 2400;
    public static LivingEntity owner;
    private int health = 5;

    public BananaEntity(EntityType<? extends BananaEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_LIFESPAN_ID, DEFAULT_LIFESPAN);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 5.0D);
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    @Override
    public void tick()
    {
        super.tick();

        if(!this.level().isClientSide)
            this.isColliding(this.blockPosition(), this.level().getBlockState(this.blockPosition()));

        int i = getLifespan() - 1;
        setLifespan(i);

        if(i <= 0){
            Minecraft.getInstance().particleEngine.createTrackingEmitter(this, ParticleTypes.ITEM_SLIME);
            this.discard();
        }


        if(!this.level().isClientSide && this.onGround())
            this.setDeltaMovement(Vec3.ZERO);
    }

    @Override
    public boolean isColliding(BlockPos pPos, BlockState pState)
    {
        AABB collisionBox = this.getBoundingBox().inflate(0.5);
        List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, collisionBox);

        for(LivingEntity entity : entities) {
            if (entity instanceof Player player) {
                if(!player.isCrouching()) {
                    slip(player);
                }
            }
            if(!(entity instanceof BananaEntity)) {
                slip(entity);
            }
        }
        return super.isColliding(pPos, pState);
    }

    private static void slip(LivingEntity entity)
    {
        // maybe use this
        double movementSpeed = entity.getAttribute(Attributes.MOVEMENT_SPEED).getValue();

        Vec3 entityVec = entity.getDeltaMovement();
        double slipX = entity.getLookAngle().x * 1.1;
        double slipZ = entity.getLookAngle().z * 1.1;
        double slipY = Math.max(0.15, -entityVec.y * 0.5);
        Vec3 slipVec = new Vec3(slipX, slipY, slipZ);
        entity.setDeltaMovement(slipVec);
        entity.hurtMarked = true;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setLifespan(pCompound.getInt("lifespan"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("lifespan", this.getLifespan());
    }

    private void setLifespan(int life) {
        this.entityData.set(DATA_LIFESPAN_ID, life);
    }

    private int getLifespan() {
        return this.entityData.get(DATA_LIFESPAN_ID);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount)
    {
        if(health - pAmount <= 0) {
            Minecraft.getInstance().particleEngine.createTrackingEmitter(this, ParticleTypes.ITEM_SLIME);
            this.discard();
        }

        else
        {
            health -= (int) pAmount;
            return false;
        }
        return false;
    }

    public void setOwner(LivingEntity pOwner) {
        owner = pOwner;
    }

    @Override
    public @Nullable Entity getOwner() {
        return owner;
    }
}
