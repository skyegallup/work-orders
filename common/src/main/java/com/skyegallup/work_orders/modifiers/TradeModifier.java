package com.skyegallup.work_orders.modifiers;

import com.mojang.serialization.Codec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

import java.util.function.Function;

public abstract class TradeModifier {
    public abstract Codec<? extends TradeModifier> type();
    public abstract ItemStack apply(ItemStack itemStack, RandomSource random);

    public static Codec<TradeModifier> CODEC = AllTradeModifiers.DISPATCH.byNameCodec()
        .dispatch("type", TradeModifier::type, Function.identity());
}
