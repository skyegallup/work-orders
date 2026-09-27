package gay.skyebound.work_orders.modifiers;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;

public class SetPotionTradeModifier extends TradeModifier {
    protected final Holder<Potion> potion;

    public SetPotionTradeModifier(Holder<Potion> potion) {
        this.potion = potion;
    }

    @Override
    public ItemStack apply(ItemStack itemStack, RandomSource random, RegistryAccess registryAccess) {
        return PotionContents.createItemStack(itemStack.getItem(), this.potion);
    }

    @Override
    public MapCodec<? extends TradeModifier> type() {
        return AllTradeModifiers.SET_POTION_CODEC.get();
    }

    public Holder<Potion> getPotion() {
        return potion;
    }

    public static MapCodec<SetPotionTradeModifier> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        BuiltInRegistries.POTION.holderByNameCodec().fieldOf("id").forGetter(SetPotionTradeModifier::getPotion)
    ).apply(instance, SetPotionTradeModifier::new));
}
