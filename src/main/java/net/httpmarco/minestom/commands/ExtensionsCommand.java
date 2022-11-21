package net.httpmarco.minestom.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;

import java.util.Collection;

public final class ExtensionsCommand extends Command {

    public ExtensionsCommand() {
        super("extensions");

        setDefaultExecutor((sender, context) -> {
            Collection<String> reloadableExtensions = MinecraftServer.getExtensionManager().getExtensions().stream().map(it -> it.getOrigin().getName()).toList();
            sender.sendMessage("Extensions: " + String.join(", ", reloadableExtensions));
        });

        MinecraftServer.getCommandManager().register(this);
    }
}
