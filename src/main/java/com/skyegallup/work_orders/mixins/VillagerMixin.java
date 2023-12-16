package com.skyegallup.work_orders.mixins;

import com.skyegallup.work_orders.core.IMerchantOffer;
import com.skyegallup.work_orders.particles.AllParticleTypes;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.*;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillager {
    @Unique protected long work_orders$lastWorkOrderStartGameTime;
    @Unique protected long work_orders$workOrderDuration = 2 * (24000);  // 2 in-game days' worth of ticks
    @Unique protected long work_orders$workOrderParticlePeriod = 20;  // show particles every 1 second
    @Unique protected long work_orders$lastWorkOrderParticleGameTime;

    public VillagerMixin(EntityType<? extends Villager> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(at = @At("TAIL"), method = "restock", remap = false)
    public void onRestock(CallbackInfo callback) {
        // check if we currently have a work order
        if (this.work_orders$getCurrentWorkOrder() != null) {
            // if so, check if it's expired and remove it if necessary
            if (this.level().getGameTime() >= this.work_orders$lastWorkOrderStartGameTime + this.work_orders$workOrderDuration) {
                this.work_orders$clearCurrentWorkOrder();
            }
        }
        // if we don't have an active work order, see if we should activate one now
        else if (this.random.nextDouble() < 0.25) {
            VillagerData villagerData = this.getVillagerData();
            VillagerProfession profession = villagerData.getProfession();

            Int2ObjectMap<VillagerTrades.ItemListing[]> trades = VillagerTrades.TRADES.get(profession);
            if (trades != null && !trades.isEmpty()) {
                VillagerTrades.ItemListing[] workOrderItemListings = trades.get(6);
                if (workOrderItemListings != null) {
                    this.work_orders$pickAndAddWorkOrder(workOrderItemListings);
                }
            }
        }
    }

    @Inject(at = @At("TAIL"), method = "tick", remap = false)
    public void onTick(CallbackInfo callback) {
        // summon particles to indicate that a work order is ready
        if (!this.isClientSide() && this.work_orders$hasActiveWorkOrder()) {
            long gameTime = this.level().getGameTime();
            long timeSinceParticles = gameTime - this.work_orders$lastWorkOrderParticleGameTime;
            if (timeSinceParticles >= this.work_orders$workOrderParticlePeriod) {
                this.work_orders$addWorkOrderParticlesAroundSelf();
                this.work_orders$lastWorkOrderParticleGameTime = gameTime;
            }
        }
    }

    @Unique
    protected void work_orders$addWorkOrderParticlesAroundSelf() {
        for (int i = 0; i < 3; ++i) {
            double dx = this.random.nextGaussian() * 0.02;
            double dy = this.random.nextGaussian() * 0.02;
            double dz = this.random.nextGaussian() * 0.02;

            ServerLevel level = (ServerLevel)this.level();
            level.sendParticles(
                AllParticleTypes.VILLAGER_WORK_ORDER.get(),
                this.getRandomX(1.0), this.getY(1.0) + 0.125, this.getRandomZ(1.0),
                1,  // count
                dx, dy, dz,
                0.1f  // max speed
            );
        }
    }

    @Unique
    protected @Nullable MerchantOffer work_orders$getCurrentWorkOrder() {
        Optional<MerchantOffer> workOrder = this.getOffers()
            .stream()
            .filter(offer -> ((IMerchantOffer)offer).work_orders$getIsWorkOrder())
            .findFirst();
        return workOrder.orElse(null);
    }

    @Unique
    protected void work_orders$clearCurrentWorkOrder() {
        MerchantOffer currentWorkOrder = this.work_orders$getCurrentWorkOrder();
        if (currentWorkOrder != null) {
            this.getOffers().remove(currentWorkOrder);
        }
    }

    @Unique
    protected boolean work_orders$hasActiveWorkOrder() {
        MerchantOffer currentWorkOrder = this.work_orders$getCurrentWorkOrder();
        return currentWorkOrder != null && !currentWorkOrder.isOutOfStock();
    }

    @Unique
    protected void work_orders$pickAndAddWorkOrder(VillagerTrades.ItemListing[] listings) {
        MerchantOffer offer = listings[this.random.nextInt(listings.length)].getOffer(this, this.random);
        if (offer != null) {
            ((IMerchantOffer)offer).work_orders$setIsWorkOrder(true);
            this.getOffers().add(offer);
        }
    }

    @Shadow(remap = false) public abstract @NotNull VillagerData getVillagerData();
}
