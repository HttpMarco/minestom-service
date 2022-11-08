package net.httpmarco.minestom.skulls;

import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.metadata.PlayerHeadMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Base64;
import java.util.UUID;

@SuppressWarnings({"UnstableApiUsage", "unused"})
public final class SkullProvider {

    @NotNull
    public static ItemStack getSkullWithSkinValue(@NotNull String value) {
        PlayerSkin skin = new PlayerSkin(value, "");
        return ItemStack.of(Material.PLAYER_HEAD).withMeta((new PlayerHeadMeta.Builder()).skullOwner(UUID.randomUUID()).playerSkin(skin).build());
    }

    @NotNull
    public static ItemStack getSkullByLink(@NotNull String link) {
        PlayerSkin skin = new PlayerSkin(Base64.getEncoder().encodeToString(("{textures:{SKIN:{url:\"http://textures.minecraft.net/texture/" + link + "\"}}}").getBytes()), "");
        return ItemStack.of(Material.PLAYER_HEAD).withMeta((new PlayerHeadMeta.Builder()).skullOwner(UUID.randomUUID()).playerSkin(skin).build());
    }

}
