package com.skyegallup.work_orders.core;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.BasicItemListing;

public class WorkOrderItemListing extends BasicItemListing {
    public WorkOrderItemListing(ItemStack price, ItemStack forSale, int xp, float priceMult) {
        super(price, forSale, 1, xp, priceMult);
    }
}
