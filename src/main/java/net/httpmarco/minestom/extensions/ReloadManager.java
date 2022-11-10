package net.httpmarco.minestom.extensions;

import java.lang.reflect.Field;
import java.util.*;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import net.minestom.server.extensions.Extension;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"unchecked", "unused"})
public final class ReloadManager {

    public static final Logger LOGGER = LoggerFactory.getLogger(ReloadManager.class);

    public void reloadExtensions() {
        Collection<Extension> reloadableExtensions = MinecraftServer.getExtensionManager().getExtensions().stream().filter(this::isValidExtension).toList();
        for (Extension reloadableExtension : reloadableExtensions)
            unloadExtension(reloadableExtension);
        loadExtensions();
    }

    public void reloadExtension(@NotNull Extension extension) {
        unloadExtension(extension);
        loadExtension(extension);
    }

    private void unloadExtension(Extension extension) {
        List<String> dependents = new LinkedList<>(extension.getDependents());
        for (String dependentID : dependents) {
            Extension dependentExt = Objects.requireNonNull(getExtensions()).get(dependentID.toLowerCase());
            if (dependentExt != null) {
                LOGGER.info("Unloading dependent extension {} (because it depends on {})", dependentID, extension.getOrigin().getName());
                unload(dependentExt);
            }
        }
        LOGGER.info("Unloading extension {}", extension.getOrigin().getName());
        unload(extension);
    }

    private void unload(Extension extension) {
        extension.preTerminate();
        extension.terminate();
        EventNode<Event> eventNode = extension.getEventNode();
        MinecraftServer.getGlobalEventHandler().removeChild(eventNode);
        extension.postTerminate();
    }

    private void loadExtensions() {
        for (Extension value : Objects.requireNonNull(getExtensions()).values())
            loadExtension(value);
    }

    private void loadExtension(Extension extension) {
        extension.preInitialize();
        MinecraftServer.getGlobalEventHandler().addChild(extension.getEventNode());
        extension.initialize();
        extension.postInitialize();
    }

    private boolean isValidExtension(Extension extension) {
        return (extension instanceof ReloadableExtension && ((ReloadableExtension)extension).isReloadable());
    }

    private Map<String, Extension> getExtensions() {
        try {
            Field field = MinecraftServer.getExtensionManager().getClass().getDeclaredField("extensions");
            field.setAccessible(true);
            return (Map<String, Extension>)field.get(MinecraftServer.getExtensionManager());
        } catch (NoSuchFieldException|IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

}
