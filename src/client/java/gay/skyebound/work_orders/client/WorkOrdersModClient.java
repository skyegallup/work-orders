package gay.skyebound.work_orders.client;

import gay.skyebound.work_orders.client.particles.AllParticleProviders;
import net.fabricmc.api.ClientModInitializer;

public class WorkOrdersModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AllParticleProviders.initialize();
    }
}
