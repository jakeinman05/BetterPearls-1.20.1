package net.notvergin.betterpearls.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BananaEntity extends Entity implements TraceableEntity
{
    private static final EntityDataAccessor<Integer> DATA_LIFESPAN_ID = SynchedEntityData.defineId(BananaEntity.class, EntityDataSerializers.INT);
    private static final int DEFAULT_LIFESPAN = 1200;
    LivingEntity owner;

    public BananaEntity(EntityType<BananaEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.blocksBuilding = true;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_LIFESPAN_ID, 1200);
    }

    @Override
    public void tick()
    {
        super.tick();

        if(!this.level().isClientSide)
            this.isColliding(this.blockPosition(), this.level().getBlockState(this.blockPosition()));

        int i = getLifespan() - 1;
        setLifespan(i);

        if(i <= 0)
            this.discard();

        if(!this.level().isClientSide && this.onGround())
            this.setDeltaMovement(Vec3.ZERO);;
    }

    @Override
    public boolean isColliding(BlockPos pPos, BlockState pState)
    {
        AABB collisionBox = this.getBoundingBox().inflate(0.5);
        List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, collisionBox);

        for(LivingEntity entity : entities)
        {
            if (entity instanceof Player player) {
                Vec3 playerVec = player.getDeltaMovement();

                // Ensure a slip effect even if the player is standing still
                double slipX = playerVec.x == 0 ? (player.getLookAngle().x * 0.8) : playerVec.x * 1.5;
                double slipZ = playerVec.z == 0 ? (player.getLookAngle().z * 0.8) : playerVec.z * 1.5;

                // Upward boost logic (ensures they slightly pop up)
                double slipY = Math.max(0.15, -playerVec.y * 1.5); // Always a small boost

                Vec3 slipVec = new Vec3(slipX, slipY, slipZ);
                System.out.println(playerVec + " ||| " + slipVec);

                player.setDeltaMovement(slipVec);
                player.hurtMarked = true;
            }

        }

        return super.isColliding(pPos, pState);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        this.setLifespan(pCompound.getInt("lifespan"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putInt("lifespan", this.getLifespan());
    }

    private void setLifespan(int life)
    {
        this.entityData.set(DATA_LIFESPAN_ID, life);
    }

    private int getLifespan()
    {
        return this.entityData.get(DATA_LIFESPAN_ID);
    }

    @Override
    public @Nullable Entity getOwner() {
        return owner;
    }

    public void setOwner(@Nullable LivingEntity owner) {
        this.owner = owner;
    }
}
