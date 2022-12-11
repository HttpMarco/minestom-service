package net.http.mineservice.instances;

import lombok.AllArgsConstructor;
import net.http.mineservice.instances.generator.FlatGenerator;
import net.http.mineservice.instances.generator.VoidGenerator;
import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.AnvilLoader;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.generator.Generator;

import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class InstanceProvider {

    private final Map<String, InstanceContainer> instanceContainerMap = new HashMap<>();

    public Collection<InstanceContainer> getAllInstances() {
        return this.instanceContainerMap.values();
    }

    public Optional<InstanceContainer> getInstance(String name) {
        return Optional.ofNullable(getInstanceOrNull(name));
    }

    public InstanceContainer getInstanceOrNull(String name) {
        return instanceContainerMap.get(name);
    }

    public void createInstance(String name, Type type) {
        var container = MinecraftServer.getInstanceManager().createInstanceContainer();
        container.setGenerator(type.generator);
        this.instanceContainerMap.put(name, container);
    }

    public void loadInstance(String name, Path path) {
        var container = MinecraftServer.getInstanceManager().createInstanceContainer(new AnvilLoader(path.resolve(name)));
        container.setGenerator(Type.VOID.generator);
        this.instanceContainerMap.put(name, container);
    }

    @AllArgsConstructor
    public enum Type {

        FLAT(new FlatGenerator()),
        VOID(new VoidGenerator());

        private final Generator generator;

    }
}
