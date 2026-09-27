package gay.skyebound.work_orders.particles;

import dev.architectury.registry.client.particle.ParticleProviderRegistry;

public class AllParticleProviders {
    public static void register() {
        ParticleProviderRegistry.register(
                AllParticleTypes.VILLAGER_WORK_ORDER,
                VillagerWorkOrderParticleProvider::new
        );
    }
}
