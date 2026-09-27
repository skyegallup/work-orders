package gay.skyebound.work_orders.modifiers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import gay.skyebound.work_orders.utils.RegistrarCodecs;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public abstract class TradeModifier {
    public abstract MapCodec<? extends TradeModifier> type();
    public abstract ItemStack apply(ItemStack itemStack, RandomSource random, RegistryAccess registryAccess);

    public static Codec<TradeModifier> CODEC  = RegistrarCodecs.byNameCodec(AllTradeModifiers.TRADE_MODIFIER_CODEC_REGISTRY)
        .dispatch("type", TradeModifier::type, Function.identity());
}
