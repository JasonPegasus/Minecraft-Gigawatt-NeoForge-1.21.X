package com.jasonpegasus.gigawatt;

import com.jasonpegasus.gigawatt.item.WandOfTransmutationItem;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Optional;

public class GwItems {
   public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Gigawatt.MOD_ID);

   public static final DeferredItem<Item> NETHER_STAR_DUST = ITEMS.register("nether_star_dust", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
   public static final DeferredItem<Item> WAND_OF_TRANSMUTATION = ITEMS.register("wand_of_transmutation", WandOfTransmutationItem::new);


   private static final CreateRegistrate REGISTRATE = Gigawatt.registrate();
   public static final ItemEntry<Item> URANIUM_ROD = REGISTRATE.item("uranium_rod", Item::new)
           .tag(Tags.Items.RODS)
           .properties(p->p
                   .food(new FoodProperties(100, 10, true, 3, Optional.empty(),
                           List.of(new FoodProperties.PossibleEffect(() -> new MobEffectInstance(MobEffects.WITHER, 400, 10), 1)))))
           .register();

   public static final ItemEntry<Item> URANIUM_INGOT = REGISTRATE.item("uranium_ingot", Item::new)
           .tag(Tags.Items.INGOTS)
           .register();

   public static void register(IEventBus eventBus)
   { ITEMS.register(eventBus); }
}