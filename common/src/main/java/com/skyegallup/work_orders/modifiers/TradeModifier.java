package com.skyegallup.work_orders.modifiers;

import com.mojang.serialization.Codec;
import com.skyegallup.work_orders.utils.RegistrarCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

import java.util.function.Function;

public abstract class TradeModifier {
    public abstract Codec<? extends TradeModifier> type();
    public abstract ItemStack apply(ItemStack itemStack, RandomSource random);

    public static Codec<TradeModifier> CODEC  = RegistrarCodecs.byNameCodec(AllTradeModifiers.TRADE_MODIFIER_CODEC_REGISTRY)
        .dispatch("type", TradeModifier::type, Function.identity());
}
