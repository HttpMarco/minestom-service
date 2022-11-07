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

    public int getPort() {
        return this.document.get("port", int.class);
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
