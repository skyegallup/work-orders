package gay.skyebound.work_orders.core;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

import java.util.Optional;

public class MerchantOfferExtensions {
    /**
     * An extended replacement for MerchantOffer::CODEC that includes data for this mod.
     */
    public static final Codec<MerchantOffer> EXTENDED_CODEC = RecordCodecBuilder.create(
        (instance) -> instance.group(
            ItemCost.CODEC.fieldOf("buy").forGetter(MerchantOffer::getItemCostA),
            ItemCost.CODEC.lenientOptionalFieldOf("buyB").forGetter(MerchantOffer::getItemCostB),
            ItemStack.CODEC.fieldOf("sell").forGetter(MerchantOffer::getResult),
            Codec.INT.lenientOptionalFieldOf("uses", 0).forGetter(MerchantOffer::getUses),
            Codec.INT.lenientOptionalFieldOf("maxUses", 4).forGetter(MerchantOffer::getMaxUses),
            Codec.BOOL.lenientOptionalFieldOf("rewardExp", true).forGetter(MerchantOffer::shouldRewardExp),
            Codec.INT.lenientOptionalFieldOf("specialPrice", 0).forGetter(MerchantOffer::getSpecialPriceDiff),
            Codec.INT.lenientOptionalFieldOf("demand", 0).forGetter(MerchantOffer::getDemand),
            Codec.FLOAT.lenientOptionalFieldOf("priceMultiplier", 0.0F).forGetter(MerchantOffer::getPriceMultiplier),
            Codec.INT.lenientOptionalFieldOf("xp", 1).forGetter(MerchantOffer::getXp),
            Codec.BOOL.lenientOptionalFieldOf("isWorkOrder", false).forGetter(merchantOffer -> ((IMerchantOffer)merchantOffer).work_orders$getIsWorkOrder())
        ).apply(instance, MerchantOfferExtensions::fromAllFields)
    );

    public static MerchantOffer fromAllFields(
        ItemCost baseCostA,
        @SuppressWarnings("OptionalUsedAsFieldOrParameterType") Optional<ItemCost> costB,
        ItemStack result,
        int uses,
        int maxUses,
        boolean rewardExp,
        int specialPriceDiff,
        int demand,
        float priceMultiplier,
        int xp,
        boolean isWorkOrder
    ) {
        MerchantOffer offer = new MerchantOffer(baseCostA, costB, result, uses, maxUses, rewardExp, specialPriceDiff, demand, priceMultiplier, xp);
        ((IMerchantOffer)offer).work_orders$setIsWorkOrder(isWorkOrder);
        return offer;
    }
}
