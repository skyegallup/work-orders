package com.skyegallup.work_orders.modifiers;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public abstract class TradeModifier {
    public abstract Codec<? extends TradeModifier> type();
    public abstract ItemStack apply(ItemStack itemStack, RandomSource random);

    public static Codec<TradeModifier> CODEC = Codec.STRING
        .fieldOf("type")
        .codec()
        .dispatch(
                "type",
                tradeModifier -> Objects.requireNonNull(
                        AllTradeModifiers.TRADE_MODIFIER_CODEC_REGISTRY.getId(tradeModifier.type())
                ).toString(),
                resourceLocation -> AllTradeModifiers.TRADE_MODIFIER_CODEC_REGISTRY.get(new ResourceLocation(resourceLocation))
        );
}
