package net.httpmarco.minestom;

import lombok.Getter;
import net.httpmarco.minestom.commands.ExtensionsCommand;
import net.httpmarco.minestom.commands.ReloadCommand;
import net.httpmarco.minestom.commands.StopCommand;
import net.httpmarco.minestom.managers.PingManager;
import net.httpmarco.minestom.extensions.ReloadManager;
import net.httpmarco.minestom.staff.top.TopCollection;
import net.httpmarco.minestom.staff.top.TopPosition;
import net.httpmarco.minestom.tablist.TablistGroup;
import net.httpmarco.minestom.tablist.TablistTeam;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.extras.bungee.BungeeCordProxy;
import net.minestom.server.extras.optifine.OptifineSupport;
import net.minestom.server.instance.Instance;
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

        var globalEventHandler = MinecraftServer.getGlobalEventHandler();

        if (minestomProperty.isAutoInstanceSupport()) {
            // Auto generated instance
            var instanceManager = MinecraftServer.getInstanceManager();
            var instanceContainer = instanceManager.createInstanceContainer();
            instanceContainer.setGenerator(unit -> unit.modifier().fillHeight(0, 40, Block.GRASS_BLOCK));
            globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
                var player = event.getPlayer();
                event.setSpawningInstance(instanceContainer);
                player.setRespawnPoint(new Pos(0, 42, 0));
                player.setGameMode(GameMode.CREATIVE);
            });
        }

        MinecraftServer.setBrandName(getProperty().getBrand());

        if (minestomProperty.isWorldSave()) {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                for (Instance i : MinecraftServer.getInstanceManager().getInstances()) i.saveChunksToStorage();
            }));
        }

        if (minestomProperty.isBungeeCordSupport()) {
            BungeeCordProxy.enable();
        } else MojangAuth.init();

        new PingManager();
        new ReloadCommand();
        new StopCommand();
        new ExtensionsCommand();

        server.start(getProperty().getHostname(), getProperty().getPort());
    }

    public static MinestomProperty getProperty() {
        return new MinestomProperty(Paths.get("polo.json"));
    }
}
