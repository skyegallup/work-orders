package gay.skyebound.work_orders.core;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import static net.minecraft.world.entity.npc.VillagerTrades.ItemListing;

import gay.skyebound.work_orders.modifiers.TradeModifier;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class WorkOrderItemListing implements ItemListing {
    protected final ItemStack price;
    protected final ItemStack price2;
    protected final ItemStack forSale;
    protected final List<TradeModifier> forSaleModifiers;
    protected final int xp;
    protected final float priceMult;

    public WorkOrderItemListing(
        ItemStack price,
        ItemStack price2,
        ItemStack forSale,
        List<TradeModifier> forSaleModifiers,
        int xp,
        float priceMult
    ) {
        this.price = price;
        this.price2 = price2;
        this.forSale = forSale;
        this.forSaleModifiers = forSaleModifiers;
        this.xp = xp;
        this.priceMult = priceMult;
    }

    @Override
    public MerchantOffer getOffer(@NotNull Entity entity, @NotNull RandomSource random) {
        RegistryAccess registryAccess;
        try (Level level = entity.level()) {
            registryAccess = level.registryAccess();
        } catch (IOException e) {
            throw new RuntimeException("Failed to access level.", e);
        }

        ItemStack copy = forSale.copy();
        for (TradeModifier modifier : forSaleModifiers) {
            copy = modifier.apply(copy, random, registryAccess);
        }

        ItemCost cost = new ItemCost(price.getItem());
        Optional<ItemCost> cost2;
        if (price2.isEmpty()) {
            cost2 = Optional.empty();
        } else {
            cost2 = Optional.of(new ItemCost(price2.getItem()));
        }

        return new MerchantOffer(cost, cost2, copy, 1, xp, priceMult);
    }

    public ItemStack getPrice() {
        return this.price;
    }
    public ItemStack getPrice2() {
        return this.price2;
    }
    public ItemStack getForSale() {
        return this.forSale;
    }
    public Optional<List<TradeModifier>> getForSaleModifiers() {
        if (this.forSaleModifiers == null || this.forSaleModifiers.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(this.forSaleModifiers);
        }
    }
    public int getXp() {
        return this.xp;
    }
    public float getPriceMult() {
        return this.priceMult;
    }

    public static final Codec<WorkOrderItemListing> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            ItemStack.CODEC.fieldOf("price").forGetter(WorkOrderItemListing::getPrice),
            ItemStack.CODEC.optionalFieldOf("price2", ItemStack.EMPTY).forGetter(WorkOrderItemListing::getPrice2),
            ItemStack.CODEC.fieldOf("forSale").forGetter(WorkOrderItemListing::getForSale),
            Codec.optionalField("forSaleModifiers", TradeModifier.CODEC.listOf(), false).forGetter(WorkOrderItemListing::getForSaleModifiers),
            Codec.INT.optionalFieldOf("xp", 50).forGetter(WorkOrderItemListing::getXp),
            Codec.FLOAT.optionalFieldOf("priceMult", 1f).forGetter(WorkOrderItemListing::getPriceMult)
        ).apply(instance, (price, price2, forSale, forSaleModifiers, xp, priceMult) -> {
            List<TradeModifier> forSaleModifiersUnwrapped = forSaleModifiers.orElse(null);
            return new WorkOrderItemListing(price, price2, forSale, forSaleModifiersUnwrapped, xp, priceMult);
        })
    );
}
