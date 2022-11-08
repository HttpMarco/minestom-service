package net.httpmarco.minestom.managers;

import net.httpmarco.minestom.Minestom;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.server.ServerListPingEvent;
import net.minestom.server.network.ConnectionManager;
import net.minestom.server.ping.ResponseData;

import java.io.File;
import java.io.InputStream;
import java.util.Base64;

public class PingManager {

    public PingManager(){
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        ConnectionManager connectionManager = MinecraftServer.getConnectionManager();

        globalEventHandler.addListener(ServerListPingEvent.class, serverListPingEvent -> {
            ResponseData responseData = new ResponseData();
            responseData.setDescription(Minestom.getProperty().getMotd());
            responseData.setMaxPlayer(Minestom.getProperty().getMaxPlayers());
            if (Minestom.getProperty().getOnlinePlayers() == -1){
                // if set to -1 its autofilled with current count of players on the server
                responseData.setOnline(connectionManager.getOnlinePlayers().size());
            } else {
                responseData.setOnline(Minestom.getProperty().getOnlinePlayers());
            }

            String icon = null;
            try (InputStream stream = new File(Minestom.getProperty().getIcon()).toURL().openStream()) {
                if (stream != null) {
                    icon = "data:image/png;base64," + Base64.getEncoder().encodeToString(stream.readAllBytes());
                }
            } catch (Exception ignored) {
                // favicon.png not found
            } finally {
                if (icon != null){
                    responseData.setFavicon(icon);
                }
            }

            serverListPingEvent.setResponseData(responseData);
        } );
    }

}
