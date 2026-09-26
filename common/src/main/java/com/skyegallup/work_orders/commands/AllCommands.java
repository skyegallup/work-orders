package com.skyegallup.work_orders.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.skyegallup.work_orders.WorkOrdersMod;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class AllCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralCommandNode<CommandSourceStack> commands = dispatcher.register(
            Commands.literal(WorkOrdersMod.ID)
                .then(ForceRestockCommand.register(dispatcher))
        );
    }
}
