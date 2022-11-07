package net.httpmarco.minestom;

import net.httpmarco.minestom.commands.StopCommand;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.extras.bungee.BungeeCordProxy;
import net.minestom.server.extras.optifine.OptifineSupport;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.block.Block;

import java.nio.file.Paths;

public final class Minestom {

    public static void main(String[] args) {

        MinestomProperty minestomProperty = new MinestomProperty(Paths.get("polo.json"));
        MinecraftServer.LOGGER.info("Trying to start Minestom server on port " + minestomProperty.getPort() + ".");

        MinecraftServer server = MinecraftServer.init();



        if (minestomProperty.isOptifineSupport()) {
            OptifineSupport.enable();
        }

        if (minestomProperty.isAutoInstanceSupport()){
            // Auto generated instance
            InstanceManager instanceManager = MinecraftServer.getInstanceManager();
            InstanceContainer instanceContainer = instanceManager.createInstanceContainer();
            instanceContainer.setGenerator(unit -> unit.modifier().fillHeight(0, 40, Block.GRASS_BLOCK));
            GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
            globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
                final Player player = event.getPlayer();
                event.setSpawningInstance(instanceContainer);
                player.setRespawnPoint(new Pos(0, 42, 0));
            });
        }

        MinecraftServer.setBrandName("HttpService");

        if (minestomProperty.isBungeeCordSupport()) {
            BungeeCordProxy.enable();
        } else {
            MojangAuth.init();
        }

        new StopCommand();

        server.start(minestomProperty.getHostname(), minestomProperty.getPort());
    }
}
