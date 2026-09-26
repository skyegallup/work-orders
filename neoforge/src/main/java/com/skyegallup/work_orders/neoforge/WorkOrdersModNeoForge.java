package com.skyegallup.work_orders.neoforge;

import com.skyegallup.work_orders.WorkOrdersMod;
import com.skyegallup.work_orders.commands.AllCommands;
import com.skyegallup.work_orders.core.WorkOrderItemListings;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
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

        // Register ourselves for server and other game events we are interested in
        NeoForge.EVENT_BUS.register(this);
    }

    public void onDataPackRegistry(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(WORK_ORDER, WorkOrderItemListings.CODEC);
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        AllCommands.register(event.getDispatcher());
    }
}
