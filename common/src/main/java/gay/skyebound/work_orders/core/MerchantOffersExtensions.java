package gay.skyebound.work_orders.core;

import com.mojang.serialization.Codec;
import net.minecraft.world.item.trading.MerchantOffers;

import java.util.function.Function;

public class MerchantOffersExtensions {
    /**
     * An extended replacement for MerchantOffers::CODEC that includes MerchantOffer data for this mod.
     */
    public static final Codec<MerchantOffers> EXTENDED_CODEC = MerchantOfferExtensions.EXTENDED_CODEC
        .listOf()
        .fieldOf("Recipes")
        .xmap(MerchantOffers::new, Function.identity())
        .codec();
    // MerchantOffers::STREAM_CODEC should work as-is since we use mixins to modify the underlying read/write methods
}
