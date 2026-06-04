package gay.skyebound.work_orders.commands;

import com.mojang.brigadier.CommandDispatcher;
import gay.skyebound.work_orders.WorkOrdersMod;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class AllCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal(WorkOrdersMod.MOD_ID)
                .then(ForceRestockCommand.register(dispatcher))
        );
    }
}
