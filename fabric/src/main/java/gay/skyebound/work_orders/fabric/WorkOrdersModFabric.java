package gay.skyebound.work_orders.fabric;

import gay.skyebound.work_orders.WorkOrdersMod;
import gay.skyebound.work_orders.core.WorkOrderItemListings;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;

public final class WorkOrdersModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        WorkOrdersMod.init();

        // Do Fabric-specific setup:
        DynamicRegistries.register(WorkOrdersMod.WORK_ORDER, WorkOrderItemListings.CODEC);
    }
}
