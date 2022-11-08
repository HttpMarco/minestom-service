package net.httpmarco.minestom;

import net.httpmarco.minestom.commands.StopCommand;
import net.httpmarco.minestom.managers.PingManager;
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

        MinecraftServer.LOGGER.info("Trying to start Minestom server on port " + getProperty().getPort() + ".");
        MinecraftServer server = MinecraftServer.init();

        if (getProperty().isOptifineSupport()) {
            OptifineSupport.enable();
        }

        if (getProperty().isAutoInstanceSupport()){
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

        MinecraftServer.setBrandName(getProperty().getBrand());

        if (getProperty().isBungeeCordSupport()) {
            BungeeCordProxy.enable();
        } else {
            MojangAuth.init();
        }

        new PingManager();
        new StopCommand();

        server.start(getProperty().getHostname(), getProperty().getPort());
    }

    public static MinestomProperty getProperty(){
        return new MinestomProperty(Paths.get("polo.json"));
    }
}
