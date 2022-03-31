package net.gigaclub.buildersystemplugin.Andere.InterfaceAPI;

import net.gigaclub.buildersystemplugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class ClickEventBuilder implements Listener {
   // private InventoryClickEvent event;

    @EventHandler

    public void ClickEventBuilder(InventoryClickEvent event){
      Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        ItemMeta meta = item.getItemMeta();
    PersistentDataContainer data = meta.getPersistentDataContainer();
               if (data.has(new NamespacedKey(Main.getPlugin(),"gui"), PersistentDataType.INTEGER)){
                   int is_gui = data.get(new NamespacedKey(Main.getPlugin(), "gui"), PersistentDataType.INTEGER);
                   if(is_gui == 1){
                       event.setCancelled(true);
                   }



        }
    }
}
