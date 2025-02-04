package net.notvergin.betterpearls.items;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.notvergin.betterpearls.items.pearls.*;

import static net.notvergin.betterpearls.BetterPearls.MODID;

public class ModItems
{
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    // Register new item here
    public static final RegistryObject<Item> SUICIDE_PEARL = ITEMS.register("explosive_pearl", () -> new ExplosivePearlItem(new Item.Properties()));
    public static final RegistryObject<Item> BANANA_PEARL = ITEMS.register("banana_pearl", () -> new BananaPearlItem(new Item.Properties()));
    public static final RegistryObject<Item> BLOCK_PEARL = ITEMS.register("block_pearl", () -> new BlockPearlItem(new Item.Properties()));
    public static final RegistryObject<Item> HOMING_PEARL = ITEMS.register("homing_pearl", () -> new HomingPearlItem(new Item.Properties()));
    public static final RegistryObject<Item> ROCKET_PEARL = ITEMS.register("rocket_pearl", () -> new RocketPearlItem(new Item.Properties()));

    public static void register(IEventBus bus)
    {
        ITEMS.register(bus);
    }

}
