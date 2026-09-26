package com.skyegallup.work_orders;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.skyegallup.work_orders.core.WorkOrderItemListings;
import com.skyegallup.work_orders.modifiers.TradeModifier;
import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class WorkOrdersMod
{
    public static final String ID = "work_orders";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final ResourceKey<Registry<WorkOrderItemListings>> WORK_ORDER = ResourceKey.createRegistryKey(
        new ResourceLocation(ID, "work_order")
    );
    public static final ResourceKey<Registry<Codec<? extends TradeModifier>>> TRADE_MODIFIER_CODEC = ResourceKey.createRegistryKey(
        new ResourceLocation(ID, "trade_modifier_codec")
    );

    public static void init()
    {
        // Do common setup:
        // Register our mod config using MidnightLib
        MidnightConfig.init(ID, Config.class);
    }
}
