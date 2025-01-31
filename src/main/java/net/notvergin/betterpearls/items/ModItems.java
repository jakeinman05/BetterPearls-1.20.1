package net.notvergin.betterpearls.items;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.notvergin.betterpearls.items.pearls.BananaPearlItem;
import net.notvergin.betterpearls.items.pearls.ExplosivePearlItem;

import static net.notvergin.betterpearls.BetterPearls.MODID;

public class ModItems
{
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    // Register new item here
    public static final RegistryObject<Item> SUICIDE_PEARL = ITEMS.register("explosive_pearl", () -> new ExplosivePearlItem(new Item.Properties()));
    public static final RegistryObject<Item> BANANA_PEARL = ITEMS.register("banana_pearl", () -> new BananaPearlItem(new Item.Properties()));

    public static void register(IEventBus bus)
    {
        ITEMS.register(bus);
    }

}
