package net.http.mineservice;

import net.minestom.server.MinecraftServer;

public class MineServiceBootstrap {

    public static void main(String[] args) {
        MinecraftServer minecraftServer = MinecraftServer.init();

        minecraftServer.start("127.0.0.1", 25565);
    }
}
