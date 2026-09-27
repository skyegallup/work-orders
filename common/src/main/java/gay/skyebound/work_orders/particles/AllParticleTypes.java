package gay.skyebound.work_orders.particles;

import gay.skyebound.work_orders.WorkOrdersMod;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;

public class AllParticleTypes {
    public static DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(
        WorkOrdersMod.ID,
        Registries.PARTICLE_TYPE
    );

    public static RegistrySupplier<SimpleParticleType> VILLAGER_WORK_ORDER = PARTICLE_TYPES.register(
        "villager_work_order",
        () -> new SimpleParticleType(false)
    );
}
