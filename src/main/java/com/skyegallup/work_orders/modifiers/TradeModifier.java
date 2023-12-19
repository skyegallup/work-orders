package com.skyegallup.work_orders.modifiers;

import com.mojang.serialization.Codec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

import java.util.function.BiFunction;

public interface TradeModifier extends BiFunction<ItemStack, RandomSource, ItemStack> {
    Codec<? extends TradeModifier> type();

    Codec<TradeModifier> CODEC = AllTradeModifiers.DISPATCH.byNameCodec()
        .dispatch("type",
            a -> {
                System.out.println("Running the thing!");
                return a.type();
            },
            a -> {
                System.out.println("Running the other thing!");
                return a;
            }
        );
}
