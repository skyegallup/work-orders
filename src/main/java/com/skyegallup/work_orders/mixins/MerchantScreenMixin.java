package com.skyegallup.work_orders.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import com.skyegallup.work_orders.WorkOrdersMod;
import com.skyegallup.work_orders.core.IMerchantOffer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MerchantScreen.class)
public class MerchantScreenMixin {
    @Unique
    private static final ResourceLocation WORK_ORDER_INDICATOR_SPRITE = new ResourceLocation(
        WorkOrdersMod.ID,
        "container/villager/work_order_indicator"
    );

    @Inject(at = @At(value = "INVOKE", target = "net.minecraft.client.gui.screens.inventory.MerchantScreen.renderAndDecorateCostA(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;II)V"), method = "render", remap = false)
    public void onRender(
        GuiGraphics graphics,
        int mouseX,
        int mouseY,
        float f,
        CallbackInfo callback,
        @Local(ordinal = 0) MerchantOffer merchantOffer,
        @Local(ordinal = 2) int i,
        @Local(ordinal = 7) int j1
    ) {
        if (((IMerchantOffer)merchantOffer).work_orders$getIsWorkOrder()) {
            graphics.blitSprite(WORK_ORDER_INDICATOR_SPRITE, i + 6, j1, 0, 86, 18);
        }
    }
}
