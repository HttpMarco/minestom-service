package net.http.mineservice.inventories;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.network.packet.client.play.ClientNameItemPacket;

public final class InputInventory {

    private final Inventory inventory;

    public InputInventory(String name, ItemStack icon) {
        this.inventory = new Inventory(InventoryType.ANVIL, name);
        this.inventory.setItemStack(0, icon);

        MinecraftServer.getPacketListenerManager().setListener(ClientNameItemPacket.class, (packet, player) -> {
            player.sendMessage(packet.itemName());
        });
    }

    public void open(Player player) {
        player.openInventory(this.inventory);
    }
}
