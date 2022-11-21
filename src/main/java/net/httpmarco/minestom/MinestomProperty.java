package net.httpmarco.minestom;

import net.httpmarco.minestom.concurrent.Document;

import java.nio.file.Path;

public final class MinestomProperty {

    private final Document document;

    public MinestomProperty(Path path) {

        document = new Document(path);

        document.addIfNotExists("port",  25565)
        .addIfNotExists("hostname",  "0.0.0.0")
        .addIfNotExists("motd",  "A new http minestom service")
        .addIfNotExists("icon", "icon.png")
        .addIfNotExists("autoInstance", true)
        .addIfNotExists("enableOptifineSupport", true)
        .addIfNotExists("brand", "HttpService")
        .addIfNotExists("maxPlayers", 20)
        .addIfNotExists("onlinePlayers", -1)
        .addIfNotExists("enableBungeeCordSupport", true)
        .addIfNotExists("saveInstanceOnShutdown", false);
        
        document.write(path);
    }

    public String getHostname() {
        return this.document.get("hostname", String.class);
    }
    public String getMotd(){
        return this.document.get("motd", String.class);
    }
    public String getIcon(){
        return this.document.get("icon", String.class);
    }
    public String getBrand(){
        return this.document.get("brand", String.class);
    }
    public int getPort() {
        return this.document.get("port", int.class);
    }
    public int getMaxPlayers(){
        return this.document.get("maxPlayers", int.class);
    }
    public int getOnlinePlayers(){
        return this.document.get("onlinePlayers", int.class);
    }
    public boolean isAutoInstanceSupport() {
        return this.document.get("autoInstance", boolean.class);
    }
    public boolean isOptifineSupport() {
        return this.document.get("enableOptifineSupport", boolean.class);
    }

    public boolean isBungeeCordSupport() {
        return this.document.get("enableBungeeCordSupport", boolean.class);
    }

    public boolean isWorldSave() {
        return this.document.get("saveInstanceOnShutdown", boolean.class);
    }

}
