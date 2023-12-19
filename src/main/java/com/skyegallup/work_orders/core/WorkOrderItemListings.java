package com.skyegallup.work_orders.core;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.npc.VillagerProfession;

import java.util.List;

public class WorkOrderItemListings {
    protected final VillagerProfession profession;
    protected List<WorkOrderItemListing> itemListings;

    public WorkOrderItemListings(VillagerProfession profession, List<WorkOrderItemListing> itemListings) {
        this.profession = profession;
        this.itemListings = itemListings;
    }

    public VillagerProfession getProfession() {
        return this.profession;
    }
    public List<WorkOrderItemListing> getItemListings() {
        return this.itemListings;
    }

    public static final Codec<WorkOrderItemListings> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            BuiltInRegistries.VILLAGER_PROFESSION.byNameCodec().fieldOf("profession").forGetter(WorkOrderItemListings::getProfession),
            WorkOrderItemListing.CODEC.listOf().fieldOf("listings").forGetter(WorkOrderItemListings::getItemListings)
        ).apply(instance, WorkOrderItemListings::new)
    );
}
