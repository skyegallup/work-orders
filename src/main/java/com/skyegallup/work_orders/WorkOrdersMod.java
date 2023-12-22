package com.skyegallup.work_orders;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.skyegallup.work_orders.commands.AllCommands;
import com.skyegallup.work_orders.core.WorkOrderItemListings;
import com.skyegallup.work_orders.modifiers.AllTradeModifiers;
import com.skyegallup.work_orders.modifiers.TradeModifier;
import com.skyegallup.work_orders.particles.AllParticleProviders;
import com.skyegallup.work_orders.particles.AllParticleTypes;
import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(WorkOrdersMod.ID)
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

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public WorkOrdersMod(IEventBus modEventBus)
    {
        // Set up for loading our datapack registries
        modEventBus.addListener(this::onDataPackRegistry);

        // Register ourselves for server and other game events we are interested in
        NeoForge.EVENT_BUS.register(this);

        // Register our DeferredRegisters to the mod bus
        AllParticleTypes.PARTICLE_TYPES.register(modEventBus);
        AllTradeModifiers.CODECS.register(modEventBus);

        // Register our mod config using MidnightLib
        MidnightConfig.init(ID, Config.class);
    }

    public void onDataPackRegistry(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(WORK_ORDER, WorkOrderItemListings.CODEC);
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        AllCommands.register(event.getDispatcher());
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onRegisterParticleProviders(RegisterParticleProvidersEvent event) {
            AllParticleProviders.register(event);
        }
    }
}
