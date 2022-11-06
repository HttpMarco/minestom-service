package net.httpmarco.minestom.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.ConsoleSender;
import net.minestom.server.command.builder.Command;

public final class StopCommand extends Command {

    public StopCommand() {
        super("shutdown", "stop");

        setCondition((sender, commandString) -> sender instanceof  ConsoleSender || sender.hasPermission("httpservice.shutdown"));

        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Trying to stop httpservice server...");
            MinecraftServer.stopCleanly();
        });

        MinecraftServer.getCommandManager().register(this);
    }
}
