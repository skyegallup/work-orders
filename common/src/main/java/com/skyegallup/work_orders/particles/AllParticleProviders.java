package com.skyegallup.work_orders.particles;

import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

public class AllParticleProviders {
    public static void register(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(AllParticleTypes.VILLAGER_WORK_ORDER.get(), VillagerWorkOrderParticleProvider::new);

        ParticleProviderRegistry.register(AllParticleTypes.VILLAGER_WORK_ORDER.get(), VillagerWorkOrderParticleProvider::new);
    }
}
