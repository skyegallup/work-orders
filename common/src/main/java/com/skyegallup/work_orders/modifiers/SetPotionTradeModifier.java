package com.skyegallup.work_orders.modifiers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;

public class SetPotionTradeModifier extends TradeModifier {
    protected final Holder<Potion> potion;

    public SetPotionTradeModifier(Holder<Potion> potion) {
        this.potion = potion;
    }

    @Override
    public ItemStack apply(ItemStack itemStack, RandomSource random) {
        return PotionUtils.setPotion(itemStack, this.potion.value());
    }

    @Override
    public Codec<? extends TradeModifier> type() {
        return AllTradeModifiers.SET_POTION_CODEC.get();
    }

    public Holder<Potion> getPotion() {
        return potion;
    }

    public static Codec<SetPotionTradeModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        BuiltInRegistries.POTION.holderByNameCodec().fieldOf("id").forGetter(SetPotionTradeModifier::getPotion)
    ).apply(instance, SetPotionTradeModifier::new));
}
