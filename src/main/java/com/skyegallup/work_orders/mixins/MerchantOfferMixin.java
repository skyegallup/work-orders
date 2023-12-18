package com.skyegallup.work_orders.mixins;

import com.skyegallup.work_orders.core.IMerchantOffer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MerchantOffer.class)
public class MerchantOfferMixin implements IMerchantOffer {
    @Unique
    protected boolean work_orders$isWorkOrder;

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

    @ModifyVariable(method = "createTag", at = @At("RETURN"), remap = false)
    public CompoundTag modifyCompoundTag(CompoundTag compoundtag) {
        compoundtag.putBoolean("isWorkOrder", this.work_orders$getIsWorkOrder());
        return compoundtag;
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
