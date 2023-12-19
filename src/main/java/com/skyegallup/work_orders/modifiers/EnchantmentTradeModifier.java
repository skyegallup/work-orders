package com.skyegallup.work_orders.modifiers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class EnchantmentTradeModifier extends TradeModifier {
    protected int minEnchantLevel;
    protected int maxEnchantLevel;
    protected boolean includeTreasureEnchants;

    public EnchantmentTradeModifier(int minEnchantLevel, int maxEnchantLevel, boolean includeTreasureEnchants) {
        this.minEnchantLevel = minEnchantLevel;
        this.maxEnchantLevel = maxEnchantLevel;
        this.includeTreasureEnchants = includeTreasureEnchants;
    }

    @Override
    public ItemStack apply(ItemStack itemStack, RandomSource randomSource) {
        int enchantLevel = minEnchantLevel + randomSource.nextInt(maxEnchantLevel - minEnchantLevel);
        return EnchantmentHelper.enchantItem(randomSource, itemStack, enchantLevel, includeTreasureEnchants);
    }

    @Override
    public Codec<? extends TradeModifier> type() {
        return AllTradeModifiers.ENCHANTMENT_CODEC.get();
    }

    public int getMinEnchantLevel() {
        return minEnchantLevel;
    }
    public int getMaxEnchantLevel() {
        return maxEnchantLevel;
    }
    public boolean getIncludeTreasureEnchants() {
        return includeTreasureEnchants;
    }

    public static Codec<EnchantmentTradeModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.INT.fieldOf("minLevel").forGetter(EnchantmentTradeModifier::getMinEnchantLevel),
        Codec.INT.fieldOf("maxLevel").forGetter(EnchantmentTradeModifier::getMaxEnchantLevel),
        Codec.BOOL.optionalFieldOf("includeTreasureEnchants", false).forGetter(EnchantmentTradeModifier::getIncludeTreasureEnchants)
    ).apply(instance, EnchantmentTradeModifier::new));
}
