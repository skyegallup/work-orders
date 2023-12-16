package com.skyegallup.work_orders.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.SuspendedTownParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class VillagerWorkOrderParticleProvider implements ParticleProvider<SimpleParticleType> {
    private final SpriteSet sprite;

    public VillagerWorkOrderParticleProvider(SpriteSet spriteSet) {
        this.sprite = spriteSet;
    }

    public Particle createParticle(
            SimpleParticleType particleType,
            ClientLevel level,
            double x,
            double y,
            double z,
            double dx,
            double dy,
            double dz
    ) {
        SuspendedTownParticle suspendedTownParticle = new SuspendedTownParticle(level, x, y, z, dx, dy, dz);
        suspendedTownParticle.pickSprite(this.sprite);
        suspendedTownParticle.setColor(0.76F, 0.70F, 0.23F);
        return suspendedTownParticle;
    }
}
