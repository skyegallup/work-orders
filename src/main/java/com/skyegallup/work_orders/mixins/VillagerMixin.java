package com.skyegallup.work_orders.mixins;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.*;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillager {
    @Unique protected long lastWorkOrderStartGameTime;
    @Unique @Nullable protected MerchantOffer currentWorkOrder;
    @Unique protected long workOrderDuration = 2 * (24000);  // 2 in-game days' worth of ticks

    public VillagerMixin(EntityType<? extends AbstractVillager> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(at = @At("RETURN"), method = "restock", remap = false)
    public void onRestock(CallbackInfo callback) {
        // check if we currently have a work order
        if (this.currentWorkOrder != null) {
            // if so, check if it's expired and remove it if necessary
            if (this.level().getGameTime() >= this.lastWorkOrderStartGameTime + this.workOrderDuration) {
                this.currentWorkOrder = null;
                this.getOffers().remove(this.currentWorkOrder);
            }
        }
        // if we don't have an active work order, see if we should activate one now
        else if (this.random.nextDouble() < 0.05) {
            VillagerData villagerData = this.getVillagerData();
            VillagerProfession profession = villagerData.getProfession();

            Int2ObjectMap<VillagerTrades.ItemListing[]> trades = VillagerTrades.TRADES.get(profession);
            if (trades != null && !trades.isEmpty()) {
                VillagerTrades.ItemListing[] workOrderItemListings = trades.get(6);
                if (workOrderItemListings != null) {
                    MerchantOffers offers = this.getOffers();
                    this.addOffersFromItemListings(offers, workOrderItemListings, 1);
                }
            }
        }
    }
    @Shadow(remap = false) public abstract VillagerData getVillagerData();
}
