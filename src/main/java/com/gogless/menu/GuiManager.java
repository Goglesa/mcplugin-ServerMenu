package com.gogless.menu;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class GuiManager {

    public static final String MAIN_MENU_TITLE = "§2Server Menu";
    public static final String WARPS_MENU_TITLE = "§5Warps";

    public static void openMainMenu(Player player) {
        Inventory menu = Bukkit.createInventory(null, 54, MAIN_MENU_TITLE);

        // Layout based on your image
        String newSkinValue = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2IyZWU2N2QzYTFjODQ3MDY3NWMzOWNhZDY5NTRhZjA2MGQzYzliYzQyZWU0YTQ5NjM3MjlhNGE3NjM0YmNjZiJ9fX0=";
        menu.setItem(20, createPlayerHead("§bWarp to Hub", newSkinValue));
        menu.setItem(21, createItem("§cSkills", Material.DIAMOND_SWORD));
        menu.setItem(22, createItem("§dEnchanter", Material.ENCHANTED_BOOK));
        menu.setItem(23, createItem("§aHome", Material.RED_BED));
        menu.setItem(24, createItem("§5Warps", Material.ENDER_PEARL));

        menu.setItem(30, createItem("§6Daily Rewards", Material.CHEST));
        menu.setItem(32, createItem("§eSpawn", Material.NETHER_STAR));

        // Add the barrier to close the menu
        menu.setItem(49, createItem("§cClose", Material.BARRIER));

        // Fill the rest of the inventory with glass panes
        fillEmptySlots(menu);

        player.openInventory(menu);
    }

    public static void openWarpsMenu(Player player) {
        Inventory warpsMenu = Bukkit.createInventory(null, 27, WARPS_MENU_TITLE);

        // Updated compact layout for warps with kits
        warpsMenu.setItem(10, createItem("§eWarp to Sunbeam", Material.SAND));
        warpsMenu.setItem(12, createItem("§8Warp to Veilstorm", Material.SCULK));
        warpsMenu.setItem(14, createItem("§3Warp to Moonstruck", Material.SNOW_BLOCK));
        warpsMenu.setItem(16, createItem("§aKits", Material.CHEST)); // New Kits item

        // Barrier to go back
        warpsMenu.setItem(26, createItem("§cBack", Material.BARRIER));

        // Fill the rest of the inventory with glass panes
        fillEmptySlots(warpsMenu);

        player.openInventory(warpsMenu);
    }

    private static ItemStack createItem(String name, Material material) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            item.setItemMeta(meta);
        }
        return item;
    }

    private static ItemStack createPlayerHead(String name, String textureValue) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
            profile.setProperty(new ProfileProperty("textures", textureValue));
            meta.setPlayerProfile(profile);
            head.setItemMeta(meta);
        }
        return head;
    }

    private static void fillEmptySlots(Inventory inventory) {
        ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = filler.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(" ");
            filler.setItemMeta(meta);
        }

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, filler);
            }
        }
    }
}