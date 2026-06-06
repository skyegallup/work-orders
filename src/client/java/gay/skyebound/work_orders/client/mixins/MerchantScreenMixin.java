package gay.skyebound.work_orders.client.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import gay.skyebound.work_orders.WorkOrdersMod;
import gay.skyebound.work_orders.core.IMerchantOffer;
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
        WorkOrdersMod.MOD_ID,
        "textures/gui/sprites/container/villager/work_order_indicator.png"
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
            int xOffset = 6;
            int yOffset = 0;
            int zOffset = 0;
            int u = 0;
            int v = 0;
            int uWidth = 86;
            int vHeight = 18;
            graphics.blit(
                    WORK_ORDER_INDICATOR_SPRITE,
                    i + xOffset,
                    j1 + yOffset,
                    zOffset,
                    u,
                    v,
                    uWidth,
                    vHeight,
                    uWidth,
                    vHeight
            );
        }
    }
}
