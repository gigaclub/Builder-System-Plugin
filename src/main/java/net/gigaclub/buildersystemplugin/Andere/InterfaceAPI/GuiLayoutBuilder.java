package net.gigaclub.buildersystemplugin.Andere.InterfaceAPI;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;


public class GuiLayoutBuilder {


    public GuiLayoutBuilder() {}
    public Inventory guiLayoutBuilder(Inventory inventory, int size) {
        int row = size / 9;
        size--;
        if (size == 26 || size == 35 || size == 44 || size == 53) {
            for (int i = 0; i <= 8; i++) {
                inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());
            }
            for (int i = size - 9; i <= size; i++) {
                inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());
            }
            row--;
                for (int i2 = 2; i2 <= row; i2++) {
                    inventory.setItem(i2*9-1, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());
                    inventory.setItem(i2*9-9, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());
                }

        }
        return inventory;
    }

    public Inventory guiFullBuilder(Inventory inventory, int size){
        size--;
        for (int i = 0; i <= size; i++) {
            inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());
        }
        return inventory;
    }
}




