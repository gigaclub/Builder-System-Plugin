package net.gigaclub.buildersystemplugin.Andere.InterfaceAPI;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import net.gigaclub.buildersystemplugin.Main;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;


public class ItemBuilder {

    /*
     *   ItemStack gadgetsItem = new ItemBuilder(Material.STORAGE_MINECART).setDisplayName("§6Gadgets §8× §7Rightclick").build());
     *
     *   player.getInventory().setItem(5, new ItemBuilder(Material.NETHER_STAR).setDisplayName("§bLobbySwitcher §8× §7Rightclick").build());
     *
     *   player.getInventory().setItem(8, new ItemBuilder(Material.SKULL_ITEM, (short) 3).setHead(player.getName()).setDisplayName("§bProfile §8× §7Rightclick").build());

     */




    private ItemStack stack;

    public ItemBuilder(Material mat) {
        stack = new ItemStack(mat);
    }
    public ItemBuilder(ItemStack item) {
        stack =new ItemStack(item);
    }

    public ItemBuilder(Material mat, short sh) {
        stack = new ItemStack(mat, 1, sh);
    }

    public ItemMeta getItemMeta() {
        return stack.getItemMeta();
    }


    public ItemBuilder setColor(Color color) {
        LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
        meta.setColor(color);
        setItemMeta(meta);
        return this;
    }

    public ItemBuilder setGlow(boolean glow) {
        if (glow) {
            addEnchant(Enchantment.DURABILITY, 1);
            addItemFlag(ItemFlag.HIDE_ENCHANTS);
        } else {
            ItemMeta meta = getItemMeta();
            for (Enchantment enchantment : meta.getEnchants().keySet()) {
                meta.removeEnchant(enchantment);
            }
        }
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        ItemMeta meta = stack.getItemMeta();
        meta.setUnbreakable(unbreakable);
        stack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        stack.setAmount(amount);
        return this;
    }

    public ItemBuilder setItemMeta(ItemMeta meta) {
        stack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setHead(String owner) {
        SkullMeta meta = (SkullMeta) stack.getItemMeta();
        meta.setOwner(owner);
        setItemMeta(meta);
        return this;
    }


    public ItemBuilder setDisplayName(String displayname) {
        ItemMeta meta = getItemMeta();
        meta.setDisplayName(displayname);
        setItemMeta(meta);
        return this;
    }

    public ItemBuilder setItemStack(ItemStack stack) {
        this.stack = stack;
        return this;
    }

    public ItemBuilder setLore(ArrayList<String> lore) {
        ItemMeta meta = getItemMeta();
        meta.setLore(lore);
        setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(String lore) {
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add(lore);
        ItemMeta meta = getItemMeta();
        meta.setLore(loreList);
        setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        ItemMeta meta = getItemMeta();
        meta.addEnchant(enchantment, level, true);
        setItemMeta(meta);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag flag) {
        ItemMeta meta = getItemMeta();
        meta.addItemFlags(flag);
        setItemMeta(meta);
        return this;
    }


    public ItemBuilder addIdentifier(String metadata) {
        ItemMeta meta = getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(new NamespacedKey(Main.getPlugin(), "identifie"), PersistentDataType.STRING, metadata);
        setItemMeta(meta);
        return this;
    }
    public ItemBuilder addID(int metadata) {
        ItemMeta meta = getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(new NamespacedKey(Main.getPlugin(), "id"), PersistentDataType.INTEGER, metadata);
        setItemMeta(meta);
        return this;
    }

    public ItemBuilder addIndex(int metadata) {
        ItemMeta meta = getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(new NamespacedKey(Main.getPlugin(), "index"), PersistentDataType.INTEGER, metadata);
        setItemMeta(meta);
        return this;
    }

    public ItemBuilder setGui(Boolean is_Gui) {
        ItemMeta meta = getItemMeta();
        if (is_Gui == true) {
            PersistentDataContainer data = meta.getPersistentDataContainer();
            data.set(new NamespacedKey(Main.getPlugin(), "gui"), PersistentDataType.INTEGER, 1);
            setItemMeta(meta);

            return this;
        }
        return this;
    }


    public ItemStack build() {
        return stack;
    }

}
