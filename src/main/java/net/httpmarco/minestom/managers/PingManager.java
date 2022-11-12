package net.httpmarco.minestom.managers;

import net.httpmarco.minestom.Minestom;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.server.ServerListPingEvent;
import net.minestom.server.ping.ResponseData;

import java.io.File;
import java.util.Base64;

public class PingManager {

    public PingManager() {
        final var globalEventHandler = MinecraftServer.getGlobalEventHandler();
        final var property = Minestom.getProperty();
        final var motd = LegacyComponentSerializer.legacySection().deserialize(property.getMotd());

        String icon = null;
        try (final var stream = new File(property.getIcon()).toURI().toURL().openStream()) {
            if (stream != null) {
                icon = "data:image/png;base64," + Base64.getEncoder().encodeToString(stream.readAllBytes());
            }
        } catch (Exception ignored) {
            // favicon.png not found
        }

        final var finalIcon = icon;
        globalEventHandler.addListener(ServerListPingEvent.class, serverListPingEvent -> {
            final var responseData = new ResponseData();
            responseData.setDescription(motd);
            responseData.setMaxPlayer(property.getMaxPlayers());
            if (property.getOnlinePlayers() != -1) {
                responseData.setOnline(Minestom.getProperty().getOnlinePlayers());
            }

            if (finalIcon != null) {
                responseData.setFavicon(finalIcon);
            }

            serverListPingEvent.setResponseData(responseData);
        });
    }

}
