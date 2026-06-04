package gay.skyebound.work_orders.modifiers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import gay.skyebound.work_orders.WorkOrdersMod;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class AllTradeModifiers {
    public static final Registry<Codec<? extends TradeModifier>> DISPATCH = new MappedRegistry<>(
            WorkOrdersMod.TRADE_MODIFIER_CODEC,
            Lifecycle.stable()
    );

    public static final Codec<EnchantmentTradeModifier> ENCHANTMENT_CODEC = register("enchantment", EnchantmentTradeModifier.CODEC);
    public static final Codec<SetNbtTradeModifier> SET_NBT_CODEC = register("set_nbt", SetNbtTradeModifier.CODEC);
    public static final Codec<SetPotionTradeModifier> SET_POTION_CODEC = register("set_potion", SetPotionTradeModifier.CODEC);

    public static void initialize() {
        // pass
    }

    private static <T extends TradeModifier> Codec<T> register(String id, Codec<T> codec) {
        return Registry.register(
                DISPATCH,
                new ResourceLocation(WorkOrdersMod.MOD_ID, id),
                codec
        );
    }
}
