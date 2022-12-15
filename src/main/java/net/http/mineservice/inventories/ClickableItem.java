package net.http.mineservice.inventories;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class ClickableItem {

    private final ItemStack stack;
    private ClickAction clickAction = null;

    public void click(Player player) {
        if(clickAction != null) {
            clickAction.click(player);
        }
    }
}
