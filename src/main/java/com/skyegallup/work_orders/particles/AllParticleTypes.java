package com.skyegallup.work_orders.particles;

import com.skyegallup.work_orders.WorkOrdersMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AllParticleTypes {
    public static DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(
        BuiltInRegistries.PARTICLE_TYPE,
        WorkOrdersMod.ID
    );

    public static DeferredHolder<ParticleType<?>, SimpleParticleType> VILLAGER_WORK_ORDER = PARTICLE_TYPES.register(
        "villager_work_order",
        () -> new SimpleParticleType(false)
    );
}
