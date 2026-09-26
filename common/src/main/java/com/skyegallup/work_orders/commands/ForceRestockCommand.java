package com.skyegallup.work_orders.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.entity.EntityTypeTest;

import java.util.List;

public class ForceRestockCommand implements Command<CommandSourceStack> {
    private static final ForceRestockCommand CMD = new ForceRestockCommand();

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("force_restock")
            .requires(cs -> cs.hasPermission(0))
            .executes(CMD);
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) {
        // get all villagers in the world
        ServerLevel level = context.getSource().getLevel();
        List<? extends Villager> villagers = level.getEntities(
            EntityTypeTest.forExactClass(Villager.class),
            villager -> true
        );

        // trigger a restock for every villager
        for (Villager villager : villagers) {
            villager.restock();
        }

        // notify the client that the command worked
        context.getSource().sendSuccess(
            () -> Component.translatable("message.work_orders.restock_success", String.valueOf(villagers.size())),
            true
        );

        return 0;
    }
}
