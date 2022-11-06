package net.httpmarco.minestom;

import net.httpmarco.minestom.commands.StopCommand;
import net.minestom.server.MinecraftServer;
import net.minestom.server.extras.optifine.OptifineSupport;

import java.nio.file.Paths;

public class Minestom {

    public static void main(String[] args) {

        MinestomProperty minestomProperty = new MinestomProperty(Paths.get("polo.json"));
        MinecraftServer server = MinecraftServer.init();

        if(minestomProperty.isOptifineSupport()) {
            OptifineSupport.enable();
        }

        new StopCommand();

        server.start(minestomProperty.getHostname(), minestomProperty.getPort());
    }
}
