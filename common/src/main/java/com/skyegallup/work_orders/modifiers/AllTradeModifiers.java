package com.skyegallup.work_orders.modifiers;

import com.mojang.serialization.Codec;
import com.skyegallup.work_orders.WorkOrdersMod;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;

public class AllTradeModifiers {
    public static final DeferredRegister<Codec<? extends TradeModifier>> CODECS = DeferredRegister.create(
        WorkOrdersMod.ID,
        WorkOrdersMod.TRADE_MODIFIER_CODEC
    );
    public static final Registry<Codec<? extends TradeModifier>> DISPATCH = CODECS.(builder -> { });

    public static final RegistrySupplier<Codec<EnchantmentTradeModifier>> ENCHANTMENT_CODEC = CODECS.register(
        "enchantment", () -> EnchantmentTradeModifier.CODEC
    );
    public static final RegistrySupplier<Codec<SetNbtTradeModifier>> SET_NBT_CODEC = CODECS.register(
        "set_nbt", () -> SetNbtTradeModifier.CODEC
    );
    public static final RegistrySupplier<Codec<SetPotionTradeModifier>> SET_POTION_CODEC = CODECS.register(
        "set_potion", () -> SetPotionTradeModifier.CODEC
    );
}
