package net.notvergin.betterpearls.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.notvergin.betterpearls.entities.projectiles.ThrownExplosivePearl;

import static net.notvergin.betterpearls.BetterPearls.MODID;

public class ModEntities
{
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);

    // register new entities here
    // just copy paste this one and change registry name and id
    public static final RegistryObject<EntityType<ThrownExplosivePearl>> THROWN_SUICIDE_PEARL = ENTITY_TYPES.register("thrown_explosive_pearl", () -> EntityType.Builder.<ThrownExplosivePearl>of(ThrownExplosivePearl::new, MobCategory.MISC).sized(0.25F, 0.25F).build("thrown_explosive_pearl"));

    //public static final EntityType<ThrownEnderpearl> ENDER_PEARL = register("ender_pearl", EntityType.Builder.<ThrownEnderpearl>of(ThrownEnderpearl::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10));

    public static void register(IEventBus bus)
    {
        ENTITY_TYPES.register(bus);
    }

}
