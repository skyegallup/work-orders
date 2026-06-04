package gay.skyebound.work_orders.particles;

import gay.skyebound.work_orders.WorkOrdersMod;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class AllParticleTypes {
    public static SimpleParticleType VILLAGER_WORK_ORDER = Registry.register(
            BuiltInRegistries.PARTICLE_TYPE,
            new ResourceLocation(WorkOrdersMod.MOD_ID, "villager_work_order"),
            FabricParticleTypes.simple(false)
    );

    public static void initialize() {
        // pass
    }
}
