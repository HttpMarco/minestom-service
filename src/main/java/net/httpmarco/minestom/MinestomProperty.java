package net.httpmarco.minestom;

import net.httpmarco.minestom.concurrent.Document;

import java.nio.file.Path;

public final class MinestomProperty {

    private final Document document;

    public MinestomProperty(Path path) {

        document = new Document(path);

        document.addIfNotExists("port",  25565).addIfNotExists("hostname",  "0.0.0.0")
                .addIfNotExists("autoInstance", true).addIfNotExists("enableOptifineSupport", true)
                .addIfNotExists("enableBungeeCordSupport", true).addIfNotExists("enableDebug", true)
                .addIfNotExists("saveInstanceOnShutdown", false);

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

    public boolean isWorldSave() {
        return this.document.get("saveInstanceOnShutdown", boolean.class);
    }

}
