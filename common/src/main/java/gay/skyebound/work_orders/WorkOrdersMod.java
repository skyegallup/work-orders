package gay.skyebound.work_orders;

import com.mojang.logging.LogUtils;
import gay.skyebound.work_orders.commands.AllCommands;
import gay.skyebound.work_orders.components.AllDataComponents;
import gay.skyebound.work_orders.core.WorkOrderItemListings;
import gay.skyebound.work_orders.modifiers.AllTradeModifiers;
import gay.skyebound.work_orders.particles.AllParticleProviders;
import gay.skyebound.work_orders.particles.AllParticleTypes;
import dev.architectury.event.events.common.CommandRegistrationEvent;
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
        ResourceLocation.fromNamespaceAndPath(ID, "work_order")
    );

    public static void init()
    {
        // Do common setup:
        AllDataComponents.DATA_COMPONENTS.register();
        AllParticleTypes.PARTICLE_TYPES.register();
        AllTradeModifiers.registerAll();
        AllParticleProviders.register();

        // Register commands
        CommandRegistrationEvent.EVENT.register(
            (dispatcher, registry, selection) -> AllCommands.register(dispatcher)
        );

        // Register our mod config using MidnightLib
        MidnightConfig.init(WorkOrdersMod.ID, Config.class);
    }
}
