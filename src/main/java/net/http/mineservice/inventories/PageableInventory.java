package net.http.mineservice.inventories;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import java.util.List;

public abstract class PageableInventory<T> extends SingletonInventory {

    private final List<T> elements;
    private final int[] possibleSlots;

    @Getter
    private int currentPage = 1;

    private static final ItemStack NEXT_PAGE_ITEM = ItemStack.of(Material.ARROW).withDisplayName(Component.text("§8» §bNächste §7Seite"));
    private static final ItemStack BE_PAGE_ITEM = ItemStack.of(Material.ARROW).withDisplayName(Component.text("§8» §bVorherige §7Seite"));
    private final ClickableItem localNextPageItem = new ClickableItem(NEXT_PAGE_ITEM, player -> buildPage(++currentPage));
    private final ClickableItem localBehaviorPageItem = new ClickableItem(BE_PAGE_ITEM, player -> buildPage(--currentPage));

    public PageableInventory(InventoryType inventoryType, String title, boolean clickable, int[] possibleSlots, List<T> values) {
        super(inventoryType, clickable, title);

        this.elements = values;
        this.possibleSlots = possibleSlots;

        this.fill();
        buildPage(1);
    }

    public void fill() {}

    public int calculateNextPageSlot() {
        return this.getInventory().getSize() - 1;
    }

    public int calculateBehaviorPageSlot() {
        return this.getInventory().getSize() - 9;
    }

    public void onPageChange(PageableInventory<T> inventory) {}

    public void buildPage(int id) {

        this.currentPage = id;
        this.clear();

        if (currentPage > 1) {
            setItem(calculateBehaviorPageSlot(), localBehaviorPageItem);
        } else {
            removeItem(calculateBehaviorPageSlot());
        }

        if (elements.size() == possibleSlots.length) {
            removeItem(calculateNextPageSlot());
        } else if (currentPage < getMaximalPage()) {
            setItem(calculateNextPageSlot(), localNextPageItem);
        } else {
            removeItem(calculateNextPageSlot());
        }

        int stepID = 0;
        for (T element : elements.subList(possibleSlots.length * (currentPage - 1), Math.min(elements.size(), possibleSlots.length * (currentPage - 1) + possibleSlots.length))) {
           setItem(possibleSlots[stepID], constructItem(element));
           stepID++;
        }
        onPageChange(this);
    }

    public void clear() {
        for (int slot : possibleSlots) {
            getInventory().setItemStack(slot, ItemStack.AIR);
        }
    }

    public int getMaximalPage() {
        return elements.size() / possibleSlots.length;
    }

    public abstract ClickableItem constructItem(T value);

}
