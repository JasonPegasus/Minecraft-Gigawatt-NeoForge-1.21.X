package com.jasonpegasus.gigawatt;

import com.jasonpegasus.gigawatt.item.WandOfTransmutation_I;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GwItems {
   public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Gigawatt.MOD_ID);

   public static final DeferredItem<Item> NETHER_STAR_DUST = ITEMS.register("nether_star_dust", () -> new Item(new Item.Properties()));
   public static final DeferredItem<Item> WAND_OF_TRANSMUTATION = ITEMS.register("wand_of_transmutation", () -> new WandOfTransmutation_I(new Item.Properties().stacksTo(1)));



   public static void register(IEventBus eventBus){
      ITEMS.register(eventBus);
   }
}
