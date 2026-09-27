package gay.skyebound.work_orders.modifiers;

import com.mojang.serialization.Codec;
import gay.skyebound.work_orders.WorkOrdersMod;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrarManager;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class AllTradeModifiers {
    public static final ResourceKey<Registry<Codec<? extends TradeModifier>>> TRADE_MODIFIER_CODEC_KEY = ResourceKey.createRegistryKey(
        new ResourceLocation(WorkOrdersMod.ID, "trade_modifier_codec")
    );
    public static final Registrar<Codec<? extends TradeModifier>> TRADE_MODIFIER_CODEC_REGISTRY = RegistrarManager.get(WorkOrdersMod.ID)
        .<Codec<? extends TradeModifier>>builder(TRADE_MODIFIER_CODEC_KEY.registry())
        .syncToClients()
        .build();

    public static final RegistrySupplier<Codec<EnchantmentTradeModifier>> ENCHANTMENT_CODEC = TRADE_MODIFIER_CODEC_REGISTRY.register(
        new ResourceLocation(WorkOrdersMod.ID, "enchantment"),
        () -> EnchantmentTradeModifier.CODEC
    );
    public static final RegistrySupplier<Codec<SetNbtTradeModifier>> SET_NBT_CODEC = TRADE_MODIFIER_CODEC_REGISTRY.register(
        new ResourceLocation(WorkOrdersMod.ID, "set_nbt"),
        () -> SetNbtTradeModifier.CODEC
    );
    public static final RegistrySupplier<Codec<SetPotionTradeModifier>> SET_POTION_CODEC = TRADE_MODIFIER_CODEC_REGISTRY.register(
        new ResourceLocation(WorkOrdersMod.ID, "set_potion"),
        () -> SetPotionTradeModifier.CODEC
    );

    public static void registerAll() {
        // pass
    }
}
