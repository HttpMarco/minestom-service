package net.httpmarco.minestom;

import net.httpmarco.minestom.concurrent.Document;

import java.nio.file.Path;

public final class MinestomProperty {

    private final Document document;

    public MinestomProperty(Path path) {

        document = new Document(path);

        if(!document.has("port")) {
            document.set("port", 25565);
        }

        if(!document.has("hostname")) {
            document.set("hostname", "0.0.0.0");
        }

        if(!document.has("motd")) {
            document.set("motd", "A Http Minestom Server!");
        }

        if(!document.has("icon")) {
            document.set("icon", "favicon.png");
        }

        if(!document.has("maxPlayers")) {
            document.set("maxPlayers", 20);
        }

        if(!document.has("onlinePlayers")) {
            document.set("onlinePlayers", -1);
        }

        if (!document.has("brand")) {
            document.set("brand", "HttpService");
        }

        if(!document.has("autoInstance")) {
            document.set("autoInstance", true);
        }

        if(!document.has("enableOptifineSupport")) {
            document.set("enableOptifineSupport", true);
        }

        if(!document.has("enableBungeeCordSupport")) {
            document.set("enableBungeeCordSupport", true);
        }


        if(!document.has("enableDebug")) {
            document.set("enableDebug", false);
        }

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

    public boolean isDebugMode() {
        return this.document.get("enableDebug", boolean.class);
    }

}
