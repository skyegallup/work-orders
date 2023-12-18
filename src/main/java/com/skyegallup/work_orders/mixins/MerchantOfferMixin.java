package com.skyegallup.work_orders.mixins;

import com.skyegallup.work_orders.core.IMerchantOffer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MerchantOffer.class)
public abstract class MerchantOfferMixin implements IMerchantOffer {
    @Unique
    protected boolean work_orders$isWorkOrder;

    @Final
    @Shadow
    private ItemStack baseCostA;

    @Shadow public abstract ItemStack getBaseCostA();

    @Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/nbt/CompoundTag;)V", remap = false)
    private void onInitWithCompoundTag(CompoundTag compoundTag, CallbackInfo callback) {
        if (compoundTag.contains("isWorkOrder", 1)) {  // 1 -> boolean
            this.work_orders$isWorkOrder = compoundTag.getBoolean("isWorkOrder");
        } else {
            // default to false to avoid crashing on vanilla save data
            this.work_orders$isWorkOrder = false;
        }
    }

    @Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/world/item/trading/MerchantOffer;)V", remap = false)
    private void onInitCopy(MerchantOffer offer, CallbackInfo callback) {
        this.work_orders$isWorkOrder = ((IMerchantOffer)offer).work_orders$getIsWorkOrder();
    }

    @ModifyVariable(at = @At("RETURN"), method = "createTag",  remap = false)
    public CompoundTag modifyCompoundTag(CompoundTag compoundtag) {
        compoundtag.putBoolean("isWorkOrder", this.work_orders$getIsWorkOrder());
        return compoundtag;
    }

    @Inject(at = @At("HEAD"), method = "getCostA", remap = false, cancellable = true)
    public void onGetCostA(CallbackInfoReturnable<ItemStack> callback) {
        // work orders should always use their base cost
        if (!this.baseCostA.isEmpty() && this.work_orders$getIsWorkOrder()) {
            callback.setReturnValue(this.getBaseCostA());
        }
    }

    @Override
    public boolean work_orders$getIsWorkOrder() {
        return this.work_orders$isWorkOrder;
    }

    @Override
    public void work_orders$setIsWorkOrder(boolean isWorkOrder) {
        this.work_orders$isWorkOrder = isWorkOrder;
    }
}
