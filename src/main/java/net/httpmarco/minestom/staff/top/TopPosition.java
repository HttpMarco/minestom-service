package net.httpmarco.minestom.staff.top;

import net.httpmarco.minestom.concurrent.Sign;
import net.httpmarco.minestom.skulls.SkullProvider;
import net.minestom.server.coordinate.Point;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.EquipmentSlot;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.entity.metadata.EntityMeta;
import net.minestom.server.entity.metadata.other.ArmorStandMeta;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.metadata.PlayerHeadMeta;
import net.minestom.server.utils.mojang.MojangUtils;

import java.util.UUID;

public final class TopPosition {

    private String username;
    private int value;

    private Sign sign;
    private EntityCreature creature;

    public TopPosition(String username, int value) {
        this.username = username;
        this.value = value;
    }

    public void build(int id, Point point, Instance instance) {

        EntityCreature creature = new EntityCreature(EntityType.ARMOR_STAND);
        ArmorStandMeta meta = (ArmorStandMeta) creature.getEntityMeta();
        meta.setMarker(true);

        creature.setInvisible(true);
        creature.setEquipment(EquipmentSlot.HELMET, ItemStack.of(Material.PLAYER_HEAD).withMeta((new PlayerHeadMeta.Builder()).skullOwner(UUID.randomUUID()).playerSkin(PlayerSkin.fromUsername(username)).build()));
        creature.setInstance(instance, point.add(0.5,-1.5,1));
        creature.setView(-180,0);
        creature.setNoGravity(true);

        this.sign = new Sign(point.add(0, -1, 0), instance);
        this.sign.setLine(0, "Rang #§6§l" + id);
        this.sign.setLine(1, username);
        this.sign.setLine(2, "");
        this.sign.setLine(3, "§7" + value + " Coins");
        this.sign.update();


    }
}
