package net.notvergin.betterpearls.eventhandlers;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.notvergin.betterpearls.entities.BananaEntity;
import net.notvergin.betterpearls.items.ModItems;

import static net.notvergin.betterpearls.BetterPearls.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents
{
    @SubscribeEvent
    public static void RightClickBanana(PlayerInteractEvent.EntityInteract event)
    {
        Entity target = event.getTarget();
        Player player = event.getEntity();
        if(target instanceof BananaEntity banana && player.isAlive())
        {
            player.addItem(ModItems.BANANA_PEARL.get().getDefaultInstance());
            player.level().playSound(null, banana.getX(), banana.getY(), banana.getZ(), SoundEvents.SLIME_SQUISH_SMALL, SoundSource.NEUTRAL, 1.0F, 1.0F);
            player.swing(InteractionHand.MAIN_HAND);
            target.discard();
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
        }
    }
}
