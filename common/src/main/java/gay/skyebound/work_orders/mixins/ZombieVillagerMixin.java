package gay.skyebound.work_orders.mixins;

import com.mojang.serialization.Codec;
import gay.skyebound.work_orders.core.MerchantOffersExtensions;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.item.trading.MerchantOffers;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ZombieVillager.class)
public class ZombieVillagerMixin {
    @Redirect(
        method = "addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/world/item/trading/MerchantOffers;CODEC:Lcom/mojang/serialization/Codec;",
            opcode = Opcodes.GETSTATIC
        )
    )
    private Codec<MerchantOffers> useExtendedCodecForSave() {
        // Swap regular codec with our extended codec
        return MerchantOffersExtensions.EXTENDED_CODEC;
    }

    @Redirect(
        method = "readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/world/item/trading/MerchantOffers;CODEC:Lcom/mojang/serialization/Codec;",
            opcode = Opcodes.GETSTATIC
        )
    )
    private Codec<MerchantOffers> useExtendedCodecForRead() {
        // Swap regular codec with our extended codec
        return MerchantOffersExtensions.EXTENDED_CODEC;
    }
}
