package com.gogless.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class MenuListener implements Listener {

    // Run this event handler at the highest priority to ensure our checks happen first.
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        InventoryView view = event.getView();
        String title = view.getTitle();

        // Check if it's one of our menus
        if (!title.equals(GuiManager.MAIN_MENU_TITLE) && !title.equals(GuiManager.WARPS_MENU_TITLE)) {
            return;
        }

        // Always cancel the event first for our menus to prevent any item movement.
        event.setCancelled(true);

        // This is the crucial fix for ghost items. If the click happened anywhere
        // but the top inventory (our menu), we force an inventory update on the
        // player's client. This immediately corrects any visual glitches or ghost items.
        if (event.getClickedInventory() == null || !event.getClickedInventory().equals(view.getTopInventory())) {
            player.updateInventory();
            return;
        }

        ItemStack clickedItem = event.getCurrentItem();

        // Ignore clicks on empty slots or our filler panes.
        if (clickedItem == null || clickedItem.getType().isAir() || clickedItem.getType() == Material.GRAY_STAINED_GLASS_PANE) {
            return;
        }

        // Handle clicks for the correct menu
        if (title.equals(GuiManager.MAIN_MENU_TITLE)) {
            handleMainMenuClick(player, clickedItem);
        } else if (title.equals(GuiManager.WARPS_MENU_TITLE)) {
            handleWarpsMenuClick(player, clickedItem);
        }

        // After an action is handled, force an inventory update. This prevents ghost
        // items from appearing on the client due to lag, as it forces the client
        // to sync with the server's state *after* the item has been handled.
        player.updateInventory();
    }

    private void handleMainMenuClick(Player player, ItemStack item) {
        switch (item.getType()) {
            case PLAYER_HEAD:
                player.closeInventory();
                player.performCommand("warp hub");
                break;
            case DIAMOND_SWORD:
                player.closeInventory();
                player.performCommand("skills");
                break;
            case ENCHANTED_BOOK:
                player.closeInventory();
                player.performCommand("enchanter");
                break;
            case RED_BED:
                player.closeInventory();
                player.performCommand("home");
                break;
            case ENDER_PEARL:
                GuiManager.openWarpsMenu(player);
                break;
            case CHEST:
                player.closeInventory();
                player.performCommand("rewards gui daily-rewards");
                break;
            case NETHER_STAR:
                player.closeInventory();
                player.performCommand("spawn");
                break;
            case BARRIER:
                player.closeInventory();
                break;
            default:
                break;
        }
    }

    private void handleWarpsMenuClick(Player player, ItemStack item) {
        switch (item.getType()) {
            case SAND:
                player.closeInventory();
                player.performCommand("warp Sunbeam");
                break;
            case SCULK:
                player.closeInventory();
                player.performCommand("warp Veilstorm");
                break;
            case SNOW_BLOCK:
                player.closeInventory();
                player.performCommand("warp Moonstruck");
                break;
            case CHEST: // New case for Kits
                player.closeInventory();
                player.performCommand("warp kits");
                break;
            case BARRIER:
                GuiManager.openMainMenu(player);
                break;
            default:
                break;
        }
    }
}