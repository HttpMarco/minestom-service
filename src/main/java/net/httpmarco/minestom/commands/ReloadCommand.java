package net.httpmarco.minestom.commands;

import net.httpmarco.minestom.Minestom;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;

public final class ReloadCommand extends Command {

    public ReloadCommand() {
        super("reload", "rl");
        setCondition((sender, commandString) -> sender instanceof net.minestom.server.command.ConsoleSender);
        setDefaultExecutor((sender, context) -> Minestom.getInstance().getReloadManager().reloadExtensions());
        MinecraftServer.getCommandManager().register(this);
    }
}
