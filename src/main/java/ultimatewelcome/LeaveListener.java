package ultimatewelcome;
import ultimatewelcome.utils.MessageUtil;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveListener implements Listener {

    private final Main plugin;

    public LeaveListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        String rank = getPrimaryRank(player);

        // CUSTOM QUIT BROADCASTS
        if (plugin.getConfig().getBoolean("leave-broadcasts.enabled", true)) {

            List<String> messages;

            String path = "leave-broadcasts.messages." + rank;

            if (plugin.getConfig().contains(path) &&
                    !plugin.getConfig().getStringList(path).isEmpty()) {
                messages = plugin.getConfig().getStringList(path);
            } else {
                messages = plugin.getConfig().getStringList("leave-broadcasts.messages.default");
            }

            for (String line : messages) {

                String msg = line.replace("%player%", player.getName());

                Bukkit.broadcast(
                        MessageUtil.parse(msg)
                );
            }
        }

        // GLOBAL LEAVE COMMANDS
        List<String> globalCmds = plugin.getConfig().getStringList("on-leave-commands");

        for (String cmd : globalCmds) {

            if (cmd == null || cmd.isEmpty()) continue;

            String finalCmd = cmd.replace("%player%", player.getName());

            plugin.executeConfiguredCommand(player, finalCmd);
        }

        // RANK LEAVE COMMANDS
        if (plugin.getConfig().getBoolean("leave-rank-commands.enabled", false)) {

            List<String> rankCmds = plugin.getConfig().getStringList(
                    "leave-rank-commands.commands." + rank
            );

            for (String cmd : rankCmds) {

                if (cmd == null || cmd.isEmpty()) continue;

                String finalCmd = cmd.replace("%player%", player.getName());

                plugin.executeConfiguredCommand(player, finalCmd);
            }
        }
    }

    // RANK DETECTION
    private String getPrimaryRank(Player player) {

        for (String rank : plugin.getConfig().getStringList("rank-order")) {

            String clean = rank.toLowerCase().trim();

            if (clean.isEmpty()) continue;

            if (player.hasPermission("group." + clean)
                    || player.hasPermission(clean)) {
                return clean;
            }
        }

        return "default";
    }
}