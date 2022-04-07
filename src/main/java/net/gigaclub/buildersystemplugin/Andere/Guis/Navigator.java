package net.gigaclub.buildersystemplugin.Andere.Guis;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import net.gigaclub.buildersystem.BuilderSystem;
import net.gigaclub.buildersystemplugin.Andere.InterfaceAPI.ItemBuilder;
import net.gigaclub.buildersystemplugin.Main;
import net.gigaclub.translation.Translation;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class Navigator implements Listener {
    HeadDatabaseAPI api = new HeadDatabaseAPI();
    Translation t = Main.getTranslation();

    BuilderSystem builderSystem = Main.getBuilderSystem();

    public void mainGui(Player player) {
        HeadDatabaseAPI api = new HeadDatabaseAPI();
        String playerUUID = player.getUniqueId().toString();
        int size = 9 * 3;
        Inventory inventory = Bukkit.createInventory(null, size, (ChatColor.DARK_AQUA.toString() + "Builder System Gui"));
        for (int j = 0; j < size; j++) {
            inventory.setItem(j, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());
        }

        ItemStack TeamGui = new ItemBuilder(api.getItemHead("9386")).setDisplayName((ChatColor.RED.toString() + "Team")).setLore(TeamloreList()).setGui(true).addIdentifier("Team_Opener").build();
        ItemStack TaskGui = new ItemBuilder(Material.PAPER).setGui(true).addIdentifier("Task_Opener").setDisplayName((ChatColor.GREEN.toString()) + "Task").setLore(TaskloreList()).build();
        ItemStack WorldGui = new ItemBuilder(api.getItemHead("32442")).setGui(true).addIdentifier("World_Opener").setDisplayName((ChatColor.BLUE.toString() + "Project")).setLore(WorldloreList()).build();

        inventory.setItem(10, TeamGui);
        inventory.setItem(13, TaskGui);
        inventory.setItem(16, WorldGui);
        player.openInventory(inventory);
    }

    //Lore list für BS_gui
    public ArrayList<String> TeamloreList() {
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add(ChatColor.GOLD.toString() + "--------------");
        loreList.add(ChatColor.GOLD.toString() + "Open Team Menu");
        loreList.add(ChatColor.GOLD.toString() + "--------------");
        return loreList;
    }

    public ArrayList<String> TaskloreList() {
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add(ChatColor.GOLD.toString() + "--------------");
        loreList.add(ChatColor.GOLD.toString() + "Open Task Menu");
        loreList.add(ChatColor.GOLD.toString() + "--------------");
        return loreList;
    }

    public ArrayList<String> WorldloreList() {
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add(ChatColor.GOLD.toString() + "-----------------");
        loreList.add(ChatColor.GOLD.toString() + "Open Project Menu");
        loreList.add(ChatColor.GOLD.toString() + "-----------------");
        return loreList;
    }
//Lore list für BS_gui   ENDE

    public void TeamGui(Player player) {
        HeadDatabaseAPI api = new HeadDatabaseAPI();
        String playerUUID = player.getUniqueId().toString();

        ItemStack backtoMain = new ItemBuilder(api.getItemHead("9334")).setDisplayName((ChatColor.RED.toString() + "To Main Menu")).setLore((ChatColor.AQUA.toString() + "Open The BuilderGui")).setGui(true).addIdentifier("Gui_Opener").build();
        int size = 9 * 3;
        Inventory inventory = Bukkit.createInventory(null, size, (ChatColor.GOLD.toString() + "Team Gui"));
        for (int i = 0; i < size; i++) {
            inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());
        }



        JSONObject team = builderSystem.getTeamNameByMember(playerUUID);
        String team_name = team.getString("name");

        //Kein Team
        if (Objects.equals(team.getString("name"), "false")) {
            inventory.setItem(11, new ItemBuilder(Material.PAPER).setGui(true).addIdentifier("Team_Create").setDisplayName(ChatColor.GRAY.toString() + "Team Create").setGlow(true).build());
            inventory.setItem(15, new ItemBuilder(Material.PAPER).setGui(true).addIdentifier("invite_list_Opener").setDisplayName(ChatColor.GRAY.toString() + "Invites List").build());
            inventory.setItem(size - 1, backtoMain);
        }else

        //User mit Team
        if (Objects.equals(team.getString("name"), "true"))    {
            inventory.setItem(11, new ItemBuilder(Material.PAPER).setGui(true).addIdentifier("invite_list_Opener").setDisplayName(ChatColor.GRAY.toString() + "Invites List").build());
            inventory.setItem(13, new ItemBuilder(Material.PAPER).setGui(true).addIdentifier("list_projecks").setDisplayName(ChatColor.GRAY.toString()+ "Projeckt List").build());
            inventory.setItem(16, new ItemBuilder(Material.PAPER).setGui(true).addIdentifier("team_manager").setDisplayName(ChatColor.GRAY.toString()+ "Team Manager").build());
            inventory.setItem(size - 1, backtoMain);
        }
        player.openInventory(inventory);
    }

    public void TaskGui(Player player) {
        HeadDatabaseAPI api = new HeadDatabaseAPI();
        ItemStack backtoMain = new ItemBuilder(api.getItemHead("9334")).setDisplayName((ChatColor.RED.toString() + "To Main Menu")).setLore((ChatColor.AQUA.toString() + "Open The BuilderGui")).setGui(true).addIdentifier("Gui_Opener").build();
        int size = 9 * 5;
        Inventory inventory = Bukkit.createInventory(null, size, (ChatColor.GOLD.toString() + "Task Gui"));
        for (int i = 0; i < size; i++) {
            inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());
        }
        inventory.setItem(size - 1, backtoMain);
        player.openInventory(inventory);
    }

    public void WorldGui(Player player) {
        HeadDatabaseAPI api = new HeadDatabaseAPI();
        ItemStack backtoMain = new ItemBuilder(api.getItemHead("9334")).setDisplayName((ChatColor.RED.toString() + "To Main Menu")).setLore((ChatColor.AQUA.toString() + "Open The BuilderGui")).setGui(true).addIdentifier("Gui_Opener").build();
        int size = 9 * 6;
        Inventory inventory = Bukkit.createInventory(null, size, (ChatColor.GOLD.toString() + "Projeckt Gui"));
        for (int i = 0; i < size; i++) {
            inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());
        }
        inventory.setItem(size - 1, backtoMain);
        player.openInventory(inventory);
    }


    public void TeamInvite(Player player) {
        HeadDatabaseAPI api = new HeadDatabaseAPI();

        ItemStack backtoTeam = new ItemBuilder(api.getItemHead("9334")).setDisplayName((ChatColor.RED.toString() + "To Team Menu")).setLore((ChatColor.AQUA.toString() + "Open The Team Gui")).setGui(true).addIdentifier("Team_Opener").build();
        int size = 9 * 6;
        Inventory inventory = Bukkit.createInventory(null, size, (ChatColor.GOLD.toString() + "Team Gui"));
        for (int i = 0; i <= 8; i++) {
            inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());
        }
        for (int i = 45; i <= 52; i++) {
            inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());
        }
        inventory.setItem(9, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());
        inventory.setItem(18, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());
        inventory.setItem(27, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());
        inventory.setItem(36, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());
        inventory.setItem(17, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());
        inventory.setItem(26, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());
        inventory.setItem(35, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());
        inventory.setItem(44, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());

        for (int i = 10; i <= 16; i++) {
            inventory.setItem(i, new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());

            if (i == 16) {
                for (int i2 = 19; i2 <= 25; i2++) {
                    inventory.setItem(i2, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());

                    if (i2 == 25) {
                        for (int i3 = 28; i3 <= 34; i3++) {
                            inventory.setItem(i3, new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());

                            if (i3 == 34) {
                                for (int i4 = 37; i4 <= 44; i4++) {
                                    if (i4 <= 43) {
                                        inventory.setItem(i4, new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());
                                    }
                                    if (i4 == 44) {
                                        inventory.setItem(51, new ItemBuilder(Material.ARROW).setDisplayName(" ").setGui(true).build());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        inventory.setItem(size - 1, backtoTeam);
        player.openInventory(inventory);
    }

    private String TeamName;
    private String TeamDesc;
    private AnvilGUI anvilgui;

    public void TeamCreatename(Player player){
             anvilgui = new AnvilGUI.Builder()
                .onClose(this::TeamGui
                ).onComplete(((player1, output) -> {
                    if (output == null) {
                        player1.sendMessage("Geben sie eine Namen ein");
                        TeamGui(player);

                    }else {
                        player1.sendMessage("team Name: " + TeamName);
                        TeamName = output;
                        TeamGui(player);
                    }
                    return null;
                }))
                .itemLeft(new ItemBuilder(Material.PAPER).setDisplayName("Team Name").build())
                .itemRight(new ItemBuilder(Material.PAPER).setDisplayName("Team Name").build())
                .title(ChatColor.RED.toString() + "Team Name").plugin(Main.getPlugin()).text("Wie S0ll dein Team heisen").open(player);
    }

    public void TeamCreateDesc(Player player){


    }


    @EventHandler
    public void ClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if (item == null) {
            return;
        } else {
            ItemMeta meta = item.getItemMeta();
            if (meta == null) {
                return;
            }
        }

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        if (data.has(new NamespacedKey(Main.getPlugin(), "identifie"), PersistentDataType.STRING)) {
            String gui = data.get(new NamespacedKey(Main.getPlugin(), "identifie"), PersistentDataType.STRING);
            switch (gui) {
                case "Team_Opener" -> TeamGui(player);
                case "Task_Opener" -> TaskGui(player);
                case "World_Opener" -> WorldGui(player);
                case "Gui_Opener" -> mainGui(player);
                case "invite_list_Opener" -> TeamInvite(player);
                case "Team_Create" -> {
                    TeamCreatename(player);
                    player.sendMessage("team create");
                    break;
                }

            }
        }
    }


    @EventHandler
    public void ClickInvCancel(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        if (item == null) {
            return;
        } else {
            ItemMeta meta = item.getItemMeta();
            if (meta == null) {
                return;
            }
        }
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        if (data.has(new NamespacedKey(Main.getPlugin(), "gui"), PersistentDataType.INTEGER)) {
            int is_gui = data.get(new NamespacedKey(Main.getPlugin(), "gui"), PersistentDataType.INTEGER);
            if (is_gui == 1) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void handleGuiOpener(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            PersistentDataContainer data = event.getItem().getItemMeta().getPersistentDataContainer();
            if (data.has(new NamespacedKey(Main.getPlugin(), "identifie"))) {
                String identifie = data.get(new NamespacedKey(Main.getPlugin(), "identifie"), PersistentDataType.STRING);
                if (identifie == "Gui_Opener") {
                    mainGui(event.getPlayer());
                }
            }
        }
    }


}
