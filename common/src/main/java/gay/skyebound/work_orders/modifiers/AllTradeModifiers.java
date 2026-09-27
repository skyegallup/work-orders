package gay.skyebound.work_orders.modifiers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import gay.skyebound.work_orders.WorkOrdersMod;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrarManager;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class AllTradeModifiers {
    public static final ResourceKey<Registry<Codec<? extends TradeModifier>>> TRADE_MODIFIER_CODEC_KEY = ResourceKey.createRegistryKey(
        ResourceLocation.fromNamespaceAndPath(WorkOrdersMod.ID, "trade_modifier_codec")
    );
    public static final Registrar<MapCodec<? extends TradeModifier>> TRADE_MODIFIER_CODEC_REGISTRY = RegistrarManager.get(WorkOrdersMod.ID)
        .<MapCodec<? extends TradeModifier>>builder(TRADE_MODIFIER_CODEC_KEY.registry())
        .syncToClients()
        .build();

    public static final RegistrySupplier<MapCodec<EnchantmentTradeModifier>> ENCHANTMENT_CODEC = TRADE_MODIFIER_CODEC_REGISTRY.register(
        ResourceLocation.fromNamespaceAndPath(WorkOrdersMod.ID, "enchantment"),
        () -> EnchantmentTradeModifier.CODEC
    );
    public static final RegistrySupplier<MapCodec<SetPotionTradeModifier>> SET_POTION_CODEC = TRADE_MODIFIER_CODEC_REGISTRY.register(
        ResourceLocation.fromNamespaceAndPath(WorkOrdersMod.ID, "set_potion"),
        () -> SetPotionTradeModifier.CODEC
    );

    public static void registerAll() {
        // pass
    }
}
