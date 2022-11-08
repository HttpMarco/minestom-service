package net.httpmarco.minestom;

import lombok.Getter;
import net.httpmarco.minestom.commands.ReloadCommand;
import net.httpmarco.minestom.commands.StopCommand;
import net.httpmarco.minestom.extensions.ReloadManager;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.extras.bungee.BungeeCordProxy;
import net.minestom.server.extras.optifine.OptifineSupport;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.block.Block;

import java.nio.file.Paths;

@Getter
public final class Minestom {

    @Getter
    private static Minestom instance;

    private final ReloadManager reloadManager;

    public static void main(String[] args) {
        new Minestom();
    }

    public Minestom() {
        instance = this;
        MinestomProperty minestomProperty = new MinestomProperty(Paths.get("polo.json"));
        MinecraftServer.LOGGER.info("Trying to start Minestom server on port " + minestomProperty.getPort() + ".");

        MinecraftServer server = MinecraftServer.init();
        this.reloadManager = new ReloadManager();

        if (minestomProperty.isOptifineSupport()) {
            OptifineSupport.enable();
        }

        if (minestomProperty.isAutoInstanceSupport()) {
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

        if (minestomProperty.isWorldSave()) {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                for (Instance i : MinecraftServer.getInstanceManager().getInstances()) i.saveChunksToStorage();
            }));
        }

        MinecraftServer.setBrandName("HttpService");
        if (minestomProperty.isBungeeCordSupport()) {
            BungeeCordProxy.enable();
        } else MojangAuth.init();

        new ReloadCommand();
        new StopCommand();
        server.start(minestomProperty.getHostname(), minestomProperty.getPort());
    }
}
