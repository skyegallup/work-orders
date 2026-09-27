package gay.skyebound.work_orders.utils;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;

public class ItemStackCodecs{
    public static final Codec<ItemStack> CODEC = RecordCodecBuilder.create(
        (instance) -> instance.group(
            BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(ItemStack::getItem),
            Codec.intRange(1, Integer.MAX_VALUE).optionalFieldOf("count", 1).forGetter(ItemStack::getCount)
        ).apply(instance, ItemStack::new));
}
