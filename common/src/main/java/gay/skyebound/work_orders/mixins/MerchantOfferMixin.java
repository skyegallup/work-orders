package gay.skyebound.work_orders.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import gay.skyebound.work_orders.core.IMerchantOffer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MerchantOffer.class)
public abstract class MerchantOfferMixin implements IMerchantOffer {
    @Unique
    protected boolean work_orders$isWorkOrder;

    @Shadow public abstract ItemStack getBaseCostA();

    @Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/world/item/trading/MerchantOffer;)V", remap = false)
    private void onInitCopy(MerchantOffer offer, CallbackInfo callback) {
        this.work_orders$isWorkOrder = ((IMerchantOffer)offer).work_orders$getIsWorkOrder();
    }

    @Inject(at = @At("HEAD"), method = "getCostA", remap = false, cancellable = true)
    public void onGetCostA(CallbackInfoReturnable<ItemStack> callback) {
        // work orders should always use their base cost
        if (this.work_orders$getIsWorkOrder()) {
            callback.setReturnValue(this.getBaseCostA());
        }
    }

    @Inject(at = @At("TAIL"), method = "writeToStream(Lnet/minecraft/network/RegistryFriendlyByteBuf;Lnet/minecraft/world/item/trading/MerchantOffer;)V", remap = false)
    private static void onWriteToStream(RegistryFriendlyByteBuf registryFriendlyByteBuf, MerchantOffer merchantOffer, CallbackInfo ci) {
        // Write mod-specific data
        registryFriendlyByteBuf.writeBoolean(((IMerchantOffer)merchantOffer).work_orders$getIsWorkOrder());
    }

    @Inject(at = @At("TAIL"), method = "createFromStream(Lnet/minecraft/network/RegistryFriendlyByteBuf;)Lnet/minecraft/world/item/trading/MerchantOffer;", remap = false)
    private static void onCreateFromStream(RegistryFriendlyByteBuf registryFriendlyByteBuf, CallbackInfoReturnable<MerchantOffer> cir, @Local(name = "merchantOffer") MerchantOffer newMerchantOffer) {
        // Read mod-specific data
        // This injector places code after the tail, which is implicitly the point after all other data is read from the buffer
        boolean isWorkOrder = registryFriendlyByteBuf.readBoolean();
        ((IMerchantOffer)newMerchantOffer).work_orders$setIsWorkOrder(isWorkOrder);
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
