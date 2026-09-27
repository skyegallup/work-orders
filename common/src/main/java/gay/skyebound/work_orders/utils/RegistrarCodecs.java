package gay.skyebound.work_orders.utils;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import dev.architectury.registry.registries.Registrar;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

/**
 * Provides useful codecs for Registrars in the Architectury API.
 */
public class RegistrarCodecs {
    public static <T> Codec<T> byNameCodec(Registrar<T> registrar) {
        /*
         * This largely re-implements Mojang's implementation of "byNameCodec" on Registry, but for Registrars.
         * Once https://github.com/architectury/architectury-api/pull/506 is merged, this can be removed.
         */
        return ResourceLocation.CODEC.flatXmap(
            arg -> Optional.ofNullable(registrar.get(arg))
                .map(DataResult::success)
                .orElseGet(() -> DataResult.error(() -> {
                    String key = registrar.key().toString();
                    return "Unknown registry key in " + key + ": " + arg;
                })),
            object -> registrar.getKey(object)
                .map(ResourceKey::location)
                .map(DataResult::success)
                .orElseGet(() -> DataResult.error(() -> {
                    String key = registrar.key().toString();
                    return "Unknown registry key in " + key + ": " + object;
                }))
        );
    }
}
