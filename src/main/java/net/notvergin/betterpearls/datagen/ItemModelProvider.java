package net.notvergin.betterpearls.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.notvergin.betterpearls.items.ModItems;

import static net.notvergin.betterpearls.BetterPearls.MODID;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider
{
    public ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MODID, existingFileHelper);
    }

    @Override
    protected void registerModels()
    {
        buildItem(ModItems.SUICIDE_PEARL);
        buildItem(ModItems.BANANA_PEARL);
        buildItem(ModItems.BLOCK_PEARL);
    }

    private ItemModelBuilder buildItem(RegistryObject<Item> item)
    {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(MODID, "item/" + item.getId().getPath()));
    }
}
