package net.httpmarco.minestom;

import net.httpmarco.minestom.commands.StopCommand;
import net.minestom.server.MinecraftServer;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.extras.bungee.BungeeCordProxy;
import net.minestom.server.extras.optifine.OptifineSupport;
import java.nio.file.Paths;

public final class Minestom {

    public static void main(String[] args) {

        MinestomProperty minestomProperty = new MinestomProperty(Paths.get("polo.json"));
        MinecraftServer.LOGGER.info("Trying to start Minestom server on port " + minestomProperty.getPort() + ".");

        MinecraftServer server = MinecraftServer.init();



        if (minestomProperty.isOptifineSupport()) {
            OptifineSupport.enable();
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
