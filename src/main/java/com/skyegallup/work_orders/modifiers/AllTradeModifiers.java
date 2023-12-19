package com.skyegallup.work_orders.modifiers;

import com.mojang.serialization.Codec;
import com.skyegallup.work_orders.WorkOrdersMod;
import net.minecraft.core.Registry;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AllTradeModifiers {
    public static final DeferredRegister<Codec<? extends TradeModifier>> CODECS = DeferredRegister.create(
        WorkOrdersMod.TRADE_MODIFIER_CODEC,
        WorkOrdersMod.ID
    );
    public static final Registry<Codec<? extends TradeModifier>> DISPATCH = CODECS.makeRegistry(builder -> { });

    public static final DeferredHolder<Codec<? extends TradeModifier>, Codec<EnchantmentTradeModifier>> ENCHANTMENT_CODEC = CODECS.register(
        "enchantment", () -> EnchantmentTradeModifier.CODEC
    );
    public static final DeferredHolder<Codec<? extends TradeModifier>, Codec<SetNbtTradeModifier>> SET_NBT_CODEC = CODECS.register(
        "set_nbt", () -> SetNbtTradeModifier.CODEC
    );
    public static final DeferredHolder<Codec<? extends TradeModifier>, Codec<SetPotionTradeModifier>> SET_POTION_CODEC = CODECS.register(
        "set_potion", () -> SetPotionTradeModifier.CODEC
    );
}
