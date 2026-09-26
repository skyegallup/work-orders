package com.skyegallup.work_orders.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.skyegallup.work_orders.core.IMerchantOffer;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.trading.MerchantOffers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Comparator;

@Mixin(MerchantMenu.class)
public class MerchantMenuMixin {
    @ModifyExpressionValue(
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/trading/Merchant;getOffers()Lnet/minecraft/world/item/trading/MerchantOffers;"
        ),
        method = "getOffers"
    )
    public MerchantOffers getSortedOffers(MerchantOffers original) {
        // sort work orders to the front of the list, without mutating the original for safety
        MerchantOffers sorted = original.copy();
        sorted.sort(Comparator.comparing(offer -> ((IMerchantOffer)offer).work_orders$getIsWorkOrder()).reversed());
        return sorted;
    }
}
