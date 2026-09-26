package com.skyegallup.work_orders.neoforge;

import com.skyegallup.work_orders.WorkOrdersMod;
import com.skyegallup.work_orders.commands.AllCommands;
import com.skyegallup.work_orders.core.WorkOrderItemListings;
import com.skyegallup.work_orders.modifiers.AllTradeModifiers;
import com.skyegallup.work_orders.particles.AllParticleProviders;
import com.skyegallup.work_orders.particles.AllParticleTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
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

        // Register our DeferredRegisters to the mod bus
        // TODO: Maybe needs to be modloader-specific? Check Architectury API
        AllParticleTypes.PARTICLE_TYPES.register(modEventBus);
        AllTradeModifiers.CODECS.register(modEventBus);
    }

    public void onDataPackRegistry(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(WORK_ORDER, WorkOrderItemListings.CODEC);
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        AllCommands.register(event.getDispatcher());
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = WorkOrdersMod.ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onRegisterParticleProviders(RegisterParticleProvidersEvent event) {
            // TODO: Maybe needs to be modloader-specific? Check Architectury API
            AllParticleProviders.register(event);
        }
    }
}
