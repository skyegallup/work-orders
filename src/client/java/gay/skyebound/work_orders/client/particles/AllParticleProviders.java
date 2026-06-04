package gay.skyebound.work_orders.client.particles;

import gay.skyebound.work_orders.particles.AllParticleTypes;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public class AllParticleProviders {
    public static void initialize() {
        ParticleFactoryRegistry.getInstance().register(
                AllParticleTypes.VILLAGER_WORK_ORDER,
                VillagerWorkOrderParticleProvider::new
        );
    }
}
