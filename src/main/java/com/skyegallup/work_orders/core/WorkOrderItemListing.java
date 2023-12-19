package com.skyegallup.work_orders.core;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import static net.minecraft.world.entity.npc.VillagerTrades.ItemListing;

import com.skyegallup.work_orders.modifiers.TradeModifier;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WorkOrderItemListing implements ItemListing {
    protected final ItemStack price;
    protected final ItemStack price2;
    protected final ItemStack forSale;
    protected final List<TradeModifier> forSaleModifiers;
    protected final int xp;
    protected final float priceMult;

    public WorkOrderItemListing(
        ItemStack price,
        ItemStack price2,
        ItemStack forSale,
        List<TradeModifier> forSaleModifiers,
        int xp,
        float priceMult
    ) {
        this.price = price;
        this.price2 = price2;
        this.forSale = forSale;
        this.forSaleModifiers = forSaleModifiers;
        this.xp = xp;
        this.priceMult = priceMult;
    }

    @Override
    public MerchantOffer getOffer(@NotNull Entity entity, @NotNull RandomSource random) {
        ItemStack copy = forSale.copy();
        for (TradeModifier modifier : forSaleModifiers) {
            copy = modifier.apply(copy, random);
        }

        return new MerchantOffer(price, price2, copy, 1, xp, priceMult);
    }

    public ItemStack getPrice() {
        return this.price;
    }
    public ItemStack getPrice2() {
        return this.price2;
    }
    public ItemStack getForSale() {
        return this.forSale;
    }
    public List<TradeModifier> getForSaleModifiers() {
        return this.forSaleModifiers;
    }
    public int getXp() {
        return this.xp;
    }
    public float getPriceMult() {
        return this.priceMult;
    }

    public static final Codec<WorkOrderItemListing> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("price").forGetter(WorkOrderItemListing::getPrice),
            ItemStack.ITEM_WITH_COUNT_CODEC.optionalFieldOf("price2", ItemStack.EMPTY).forGetter(WorkOrderItemListing::getPrice2),  // TODO: fix this
            ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("forSale").forGetter(WorkOrderItemListing::getForSale),
            ExtraCodecs.strictOptionalField(TradeModifier.CODEC.listOf(), "forSaleModifiers", List.of()).forGetter(WorkOrderItemListing::getForSaleModifiers),
            Codec.INT.optionalFieldOf("xp", 50).forGetter(WorkOrderItemListing::getXp),
            Codec.FLOAT.optionalFieldOf("priceMult", 1f).forGetter(WorkOrderItemListing::getPriceMult)
        ).apply(instance, WorkOrderItemListing::new)
    );
}
