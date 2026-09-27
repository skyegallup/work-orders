package gay.skyebound.work_orders.modifiers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.Optional;

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
    public ItemStack apply(ItemStack itemStack, RandomSource randomSource, RegistryAccess registryAccess) {
        int enchantLevel = minEnchantLevel + randomSource.nextInt(maxEnchantLevel - minEnchantLevel);
        Optional<HolderSet.Named<Enchantment>> enchantmentsForTradedEquipment = registryAccess
            .registryOrThrow(Registries.ENCHANTMENT)
            .getTag(EnchantmentTags.ON_TRADED_EQUIPMENT);  // follows default villager trade setup behavior
        return EnchantmentHelper.enchantItem(randomSource, itemStack, enchantLevel, registryAccess, enchantmentsForTradedEquipment);
    }

    @Override
    public MapCodec<? extends TradeModifier> type() {
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

    public static MapCodec<EnchantmentTradeModifier> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        Codec.INT.fieldOf("minLevel").forGetter(EnchantmentTradeModifier::getMinEnchantLevel),
        Codec.INT.fieldOf("maxLevel").forGetter(EnchantmentTradeModifier::getMaxEnchantLevel),
        Codec.BOOL.optionalFieldOf("includeTreasureEnchants", false).forGetter(EnchantmentTradeModifier::getIncludeTreasureEnchants)
    ).apply(instance, EnchantmentTradeModifier::new));
}
