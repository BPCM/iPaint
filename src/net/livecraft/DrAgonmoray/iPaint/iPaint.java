//The Package
package net.livecraft.DrAgonmoray.iPaint;


import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;


public class iPaint extends JavaPlugin {

    private final iPaintBlockListener blockListener = new iPaintBlockListener(this);
    public static PermissionHandler permissionHandler;
    public final HashMap<Player, Boolean> painters = new HashMap<Player, Boolean>();
    public Boolean usePermissions;

    public void onDisable() {
        System.out.println("iPaint disabled.");
    }

    public void onEnable() {
        setupPermissions();
        PluginDescriptionFile pdfFile = this.getDescription();
        iPaintBlockListener bandagePlayerListener = new iPaintBlockListener(this);
        getServer().getPluginManager().registerEvents(bandagePlayerListener, this);
        System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("ipaint")) {
            if (canPaint((Player) sender) == 0) return true;
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("off")) {
                    paintSet((Player) sender, false);
                    return true;
                } else if (args[0].equalsIgnoreCase("on")) {
                    paintSet((Player) sender, true);
                    return true;
                }
            }
        }
        return false;
    }

    private void setupPermissions() {
        Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");
        if (permissionHandler == null) {
            if (permissionsPlugin != null) {
                permissionHandler = ((Permissions) permissionsPlugin).getHandler();
                usePermissions = true;
            } else {
                usePermissions = false;
            }
        }
    }

    public int canPaint(Player player) {
        if (usePermissions) {
            if (permissionHandler.has(player, "ipaint.paint.infinite")) {
                return 2;
            }
            if (permissionHandler.has(player, "ipaint.paint")) {
                return 1;
            }
        } else {
            if (player.isOp()) {
                return 2;
            }
        }
        return 0;
    }

    public void paintSet(Player player, Boolean toggle) {
        if (toggle) {
            if (!painters.containsKey(player)) {
                painters.put(player, true);
                player.sendMessage(ChatColor.YELLOW + "iPaint activated.");
            }
        } else {
            if (painters.containsKey(player)) {
                painters.remove(player);
                player.sendMessage(ChatColor.YELLOW + "iPaint deactivated.");
            }
        }
    }


}