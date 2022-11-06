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


        if(!document.has("enableOptifineSupport")) {
            document.set("enableOptifineSupport", true);
        }


        document.write(path);
    }

    public String getHostname() {
        return this.document.get("hostname", String.class);
    }

    public int getPort() {
        return this.document.get("port", int.class);
    }

    public boolean isOptifineSupport() {
        return this.document.get("enableOptifineSupport", boolean.class);
    }

}
