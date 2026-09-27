package com.skyegallup.work_orders.neoforge;

import com.skyegallup.work_orders.WorkOrdersMod;
import com.skyegallup.work_orders.core.WorkOrderItemListings;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

import static com.skyegallup.work_orders.WorkOrdersMod.WORK_ORDER;

@Mod(WorkOrdersMod.ID)
public final class WorkOrdersModNeoForge {
    public WorkOrdersModNeoForge(IEventBus modEventBus) {
        // Run our common setup.
        WorkOrdersMod.init();

        // Do NeoForge-specific setup:
        // Set up for loading our datapack registries
        modEventBus.addListener(this::onDataPackRegistry);
    }

    public void onDataPackRegistry(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(WORK_ORDER, WorkOrderItemListings.CODEC);
    }
}
