package gay.skyebound.work_orders.components;

import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import gay.skyebound.work_orders.WorkOrdersMod;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;

public class AllDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(
        WorkOrdersMod.ID,
        Registries.DATA_COMPONENT_TYPE
    );

    public static final RegistrySupplier<DataComponentType<Boolean>> IS_WORK_ORDER = DATA_COMPONENTS.register(
        ResourceLocation.fromNamespaceAndPath(WorkOrdersMod.ID, "is_work_order"),
        () -> DataComponentType.<Boolean>builder()
            .persistent(Codec.BOOL)
            .networkSynchronized(ByteBufCodecs.BOOL)
            .build()
    );
}
