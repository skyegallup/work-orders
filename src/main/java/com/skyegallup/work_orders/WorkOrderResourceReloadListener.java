package com.skyegallup.work_orders;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Map;

public class WorkOrderResourceReloadListener extends SimpleJsonResourceReloadListener {
    private static final Logger LOGGER = LogUtils.getLogger();

    public WorkOrderResourceReloadListener() {
        super(new GsonBuilder().create(), "work_orders");
    }

    @Override
    protected void apply(
        Map<ResourceLocation, JsonElement> objects,
        @NotNull ResourceManager resourceManager,
        @NotNull ProfilerFiller profiler
    ) {
        LOGGER.warn("Loading work order data...");

        objects.forEach((resourceLocation, jsonElement) -> {
            LOGGER.warn("Found work order at " + resourceLocation.toString());
            // TODO: do something here :)
        });

        LOGGER.info(String.format("Loaded %s work orders", objects.size()));
    }
}
