package com.skyegallup.work_orders.modifiers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

public class SetNbtTradeModifier extends TradeModifier {
    protected final CompoundTag tag;

    public SetNbtTradeModifier(CompoundTag tag) {
        this.tag = tag;
    }

    @Override
    public ItemStack apply(ItemStack itemStack, RandomSource random) {
        itemStack.getOrCreateTag().merge(tag);
        return itemStack;
    }

    @Override
    public Codec<? extends TradeModifier> type() {
        return AllTradeModifiers.SET_NBT_CODEC.get();
    }

    public CompoundTag getTag() {
        return tag;
    }

    public static Codec<SetNbtTradeModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        TagParser.AS_CODEC.fieldOf("tag").forGetter(SetNbtTradeModifier::getTag)
    ).apply(instance, SetNbtTradeModifier::new));
}
