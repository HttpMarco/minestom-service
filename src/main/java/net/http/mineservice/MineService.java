package net.http.mineservice;

import lombok.Getter;
import net.http.mineservice.instances.InstanceProvider;

@Getter
public final class MineService {

    @Getter
    private static MineService instance;

    private final InstanceProvider instanceProvider;

    public MineService() {
        instance = this;

        this.instanceProvider = new InstanceProvider();
    }
}
