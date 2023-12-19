package com.skyegallup.work_orders.core;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.BasicItemListing;

public class WorkOrderItemListing extends BasicItemListing {
    public WorkOrderItemListing(
        ItemStack price,
        ItemStack forSale,
        int xp,
        float priceMult
    ) {
        super(price, forSale, 1, xp, priceMult);
    }


    public ItemStack getPrice() {
        return this.price;
    }
    public ItemStack getForSale() {
        return this.forSale;
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
            ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("forSale").forGetter(WorkOrderItemListing::getForSale),
            Codec.INT.optionalFieldOf("xp", 50).forGetter(WorkOrderItemListing::getXp),
            Codec.FLOAT.optionalFieldOf("priceMult", 1f).forGetter(WorkOrderItemListing::getPriceMult)
        ).apply(instance, WorkOrderItemListing::new)
    );
}
