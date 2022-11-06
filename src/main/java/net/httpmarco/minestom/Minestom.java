package net.httpmarco.minestom;

import net.minestom.server.MinecraftServer;

import java.nio.file.Paths;

public class Minestom {

    public static void main(String[] args) {

        MinestomProperty minestomProperty = new MinestomProperty(Paths.get("polo.json"));
        MinecraftServer server = MinecraftServer.init();

        server.start(minestomProperty.getHostname(), minestomProperty.getPort());
    }
}
