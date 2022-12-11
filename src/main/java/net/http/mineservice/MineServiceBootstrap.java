package net.http.mineservice;

import net.http.mineservice.terminal.MineTerminal;
import net.minestom.server.MinecraftServer;

public class MineServiceBootstrap {

    public static void main(String[] args) {
        MinecraftServer minecraftServer = MinecraftServer.init();

        MinecraftServer.setTerminalEnabled(false);
        MineTerminal.startTerminal();

        new MineService();

        minecraftServer.start("127.0.0.1", 25565);
    }
}
