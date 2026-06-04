package gay.skyebound.work_orders;

import com.mojang.serialization.Codec;
import gay.skyebound.work_orders.commands.AllCommands;
import gay.skyebound.work_orders.core.WorkOrderItemListings;
import gay.skyebound.work_orders.modifiers.AllTradeModifiers;
import gay.skyebound.work_orders.modifiers.TradeModifier;
import gay.skyebound.work_orders.particles.AllParticleTypes;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class WorkOrdersMod implements ModInitializer
{
    public static final String MOD_ID = "work_orders";
    // private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final ResourceKey<Registry<WorkOrderItemListings>> WORK_ORDER = ResourceKey.createRegistryKey(
        new ResourceLocation(MOD_ID, "work_order")
    );
    public static final ResourceKey<Registry<Codec<? extends TradeModifier>>> TRADE_MODIFIER_CODEC = ResourceKey.createRegistryKey(
        new ResourceLocation(MOD_ID, "trade_modifier_codec")
    );

    @Override
    public void onInitialize()
    {
        AllParticleTypes.initialize();
        AllTradeModifiers.initialize();
        DynamicRegistries.register(WORK_ORDER, WorkOrderItemListings.CODEC);

        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> AllCommands.register(dispatcher)));

        // Register our mod config using MidnightLib
        MidnightConfig.init(MOD_ID, Config.class);
    }
}
