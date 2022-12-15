package net.http.mineservice;

import net.http.mineservice.instances.InstanceProvider;
import net.http.mineservice.terminal.MineTerminal;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerLoginEvent;

public class MineServiceBootstrap {

    public static void main(String[] args) {
        MinecraftServer minecraftServer = MinecraftServer.init();

        MinecraftServer.setTerminalEnabled(false);
        MineTerminal.startTerminal();

        new MineService();

        //Test elements
        var provider = MineService.getInstance().getInstanceProvider();
        provider.createInstance("Polo", InstanceProvider.Type.FLAT);
        MinecraftServer.getGlobalEventHandler().addListener(PlayerLoginEvent.class, event -> event.setSpawningInstance(provider.getInstanceOrNull("Polo")));

        minecraftServer.start("127.0.0.1", 25565);
    }
}
