package ultimatewelcome;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WelcomeCommand implements CommandExecutor, TabCompleter {

    private final Main plugin;

    public WelcomeCommand(Main plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String cmd = command.getName().toLowerCase();

        // ====================== /wb ONLY PLAYER WELCOME ======================
        if (cmd.equals("wb")) {

            if (!(sender instanceof Player player)) {
                sender.sendMessage(ChatColor.RED + "Only players can use this command.");
                return true;
            }

            if (args.length == 0) {
                sendHelp(sender);
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                player.sendMessage(ChatColor.RED + "Player not found.");
                return true;
            }

            // self check
            if (player.getUniqueId().equals(target.getUniqueId()) && !plugin.allowSelfWelcome) {
                player.sendMessage(ChatColor.RED + "You cannot welcome yourself.");
                return true;
            }

            // cooldown
            if (!plugin.canWelcome(player.getUniqueId(), target.getUniqueId())) {
                long remaining = plugin.getRemainingCooldown(player.getUniqueId(), target.getUniqueId());
                player.sendMessage(ChatColor.RED + "Cooldown: " + remaining + "s");
                return true;
            }

            // expiry
            int expirySeconds = plugin.getConfig().getInt("welcome-bounty.expiry-seconds", 60);

            Long joinTime = plugin.getJoinTime(target.getUniqueId());
            if (joinTime == null ||
                    (System.currentTimeMillis() - joinTime) / 1000 > expirySeconds) {

                player.sendMessage(ChatColor.RED + "This player is no longer eligible.");
                return true;
            }

            executeWelcome(player, target);
            return true;
        }

        // ====================== /uw ADMIN COMMANDS ======================
        if (cmd.equals("uw")) {

            if (!sender.hasPermission("welcome.admin")) {
                sender.sendMessage(ChatColor.RED + "No permission.");
                return true;
            }

            if (args.length == 0) {
                sendHelp(sender);
                return true;
            }

            switch (args[0].toLowerCase()) {

                case "forcewelcome":
                case "force":
                    return handleForceWelcome(sender, args);

                case "removecooldown":
                case "rc":
                    return handleRemoveCooldown(sender, args);

                case "checkcooldown":
                case "cc":
                    return handleCheckCooldown(sender, args);

                case "welcomeself":
                    return toggleSelfWelcome(sender);
            }

            sender.sendMessage(ChatColor.RED + "Unknown command.");
            return true;
        }

        return false;
    }

    // TAB COMPLETION 
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        String cmd = command.getName().toLowerCase();
        List<String> suggestions = new ArrayList<>();

        //  /wb TAB
        if (cmd.equals("wb")) {

            if (args.length == 1) {
                Bukkit.getOnlinePlayers().forEach(p -> suggestions.add(p.getName()));
            }

            return filter(suggestions, args);
        }

        //  /uw TAB
        if (cmd.equals("uw")) {

            if (!sender.hasPermission("welcome.admin")) {
                return List.of();
            }

            if (args.length == 1) {
                return filter(List.of(
                        "forcewelcome",
                        "removecooldown",
                        "rc",
                        "checkcooldown",
                        "cc",
                        "welcomeself"
                ), args);
            }

            if (args.length == 2 &&
                    (args[0].equalsIgnoreCase("forcewelcome")
                            || args[0].equalsIgnoreCase("force")
                            || args[0].equalsIgnoreCase("removecooldown")
                            || args[0].equalsIgnoreCase("rc")
                            || args[0].equalsIgnoreCase("checkcooldown")
                            || args[0].equalsIgnoreCase("cc"))) {

                Bukkit.getOnlinePlayers().forEach(p -> suggestions.add(p.getName()));
            }

            return filter(suggestions, args);
        }

        return List.of();
    }

    private List<String> filter(List<String> list, String[] args) {
        String last = args[args.length - 1].toLowerCase();
        return list.stream()
                .filter(s -> s.toLowerCase().startsWith(last))
                .collect(Collectors.toList());
    }

    // EXECUTE WELCOME
    private void executeWelcome(Player welcomer, Player target) {

        String targetRank = getPrimaryRank(target);

        String currency = plugin.getConfig()
                .getString("welcome-bounty.currency-name", "money");

        int amount = plugin.getConfig().getInt(
                "welcome-bounty.rewards." + targetRank + ".amount",0);

        // MAIN REWARD COMMAND

        // Try rank-specific reward command first, then fallback to global reward command
        String rewardCmd = plugin.getConfig().getString(
                "welcome-bounty.rewards." + targetRank + ".reward-command"
        );

        if (rewardCmd == null || rewardCmd.isEmpty()) {

            rewardCmd = plugin.getConfig()
                    .getString("welcome-bounty.reward-command");
        }

        if (rewardCmd != null && !rewardCmd.isEmpty()) {

            String finalCmd = rewardCmd
                    .replace("%player%", welcomer.getName())
                    .replace("%target%", target.getName())
                    .replace("%amount%", String.valueOf(amount));

            plugin.executeConfiguredCommand(welcomer, finalCmd);
        }

        // RANK WB COMMANDS
        List<String> rankCommands = plugin.getConfig().getStringList(
                "welcome-bounty.rewards." + targetRank + ".wb-commands"
        );

        for (String cmd : rankCommands) {

            if (cmd == null || cmd.isEmpty()) continue;

            String finalCmd = cmd
                    .replace("%player%", welcomer.getName())
                    .replace("%target%", target.getName())
                    .replace("%amount%", String.valueOf(amount));

            plugin.executeConfiguredCommand(welcomer, finalCmd);
        }

        // ====================== GLOBAL WB COMMANDS ======================
        List<String> globalCommands = plugin.getConfig().getStringList(
                "welcome-bounty.wb-commands"
        );

        for (String cmd : globalCommands) {

            if (cmd == null || cmd.isEmpty()) continue;

            String finalCmd = cmd
                    .replace("%player%", welcomer.getName())
                    .replace("%target%", target.getName())
                    .replace("%amount%", String.valueOf(amount));

            plugin.executeConfiguredCommand(welcomer, finalCmd);
        }

        // ====================== BROADCAST ======================
        for (String line : plugin.getConfig()
                .getStringList("welcome-bounty.broadcast-message")) {

            String msg = line
                    .replace("%player%", welcomer.getName())
                    .replace("%target%", target.getName())
                    .replace("%amount%", String.valueOf(amount))
                    .replace("%currency%", currency);

            Bukkit.broadcastMessage(
                    ChatColor.translateAlternateColorCodes('&', msg)
            );
        }

        // ====================== MESSAGE TO WELCOMER ======================
        for (String line : plugin.getConfig()
                .getStringList("welcome-bounty.welcomer-message")) {

            String msg = line
                    .replace("%player%", welcomer.getName())
                    .replace("%target%", target.getName())
                    .replace("%amount%", String.valueOf(amount))
                    .replace("%currency%", currency);

            welcomer.sendMessage(
                    ChatColor.translateAlternateColorCodes('&', msg)
            );
        }

        // ====================== MESSAGE TO TARGET ======================
        for (String line : plugin.getConfig()
                .getStringList("welcome-bounty.welcomed-message")) {

            String msg = line
                    .replace("%player%", welcomer.getName())
                    .replace("%target%", target.getName())
                    .replace("%amount%", String.valueOf(amount))
                    .replace("%currency%", currency);

            target.sendMessage(
                    ChatColor.translateAlternateColorCodes('&', msg)
            );
        }

        // ====================== SOUND ======================
        if (plugin.getConfig().getBoolean(
                "welcome-bounty.sound.enabled",
                true
        )) {

            String soundName = plugin.getConfig().getString(
                    "welcome-bounty.sound.type",
                    "ENTITY_PLAYER_LEVELUP"
            );

            float volume = (float) plugin.getConfig()
                    .getDouble("welcome-bounty.sound.volume", 1.0);

            float pitch = (float) plugin.getConfig()
                    .getDouble("welcome-bounty.sound.pitch", 1.0);

            try {

                welcomer.playSound(
                        welcomer.getLocation(),
                        soundName,
                        volume,
                        pitch
                );

            } catch (Exception e) {

                welcomer.playSound(
                        welcomer.getLocation(),
                        "entity.player.levelup",
                        1.0f,
                        1.0f
                );
            }
        }

        // ====================== SET COOLDOWN ======================
        plugin.setWelcomeCooldown(
                welcomer.getUniqueId(),
                target.getUniqueId()
        );
    }

    // ====================== FORCE WELCOME ======================
    private boolean handleForceWelcome(CommandSender sender, String[] args) {

        if (args.length < 2) {

            sender.sendMessage(ChatColor.RED +
                    "Usage: /uw forcewelcome <player>");

            return true;
        }

        Player target = Bukkit.getPlayer(args[1]);

        if (target == null) {

            sender.sendMessage(ChatColor.RED + "Player not found.");

            return true;
        }

        if (sender instanceof Player welcomer) {

            executeWelcome(welcomer, target);

            sender.sendMessage(ChatColor.GREEN +
                    "Force welcomed " +
                    target.getName() +
                    " successfully!");

        } else {

            sender.sendMessage(ChatColor.RED +
                    "Only players can use force welcome.");
        }

        return true;
    }

    // ====================== REMOVE COOLDOWN ======================
    private boolean handleRemoveCooldown(CommandSender sender, String[] args) {

        if (args.length < 2) {

            sender.sendMessage(ChatColor.RED +
                    "Usage: /uw removewbcooldown <player> [target]");

            return true;
        }

        Player p1 = Bukkit.getPlayer(args[1]);

        if (p1 == null) {

            sender.sendMessage(ChatColor.RED + "Player not found.");

            return true;
        }

        if (args.length >= 3) {

            Player p2 = Bukkit.getPlayer(args[2]);

            if (p2 != null) {

                plugin.removeSpecificCooldown(
                        p1.getUniqueId(),
                        p2.getUniqueId()
                );

                sender.sendMessage(ChatColor.GREEN +
                        "Removed cooldown between " +
                        p1.getName() +
                        " and " +
                        p2.getName());
            }

        } else {

            plugin.removePlayerCooldowns(p1.getUniqueId());

            sender.sendMessage(ChatColor.GREEN +
                    "Removed all cooldowns for " +
                    p1.getName());
        }

        return true;
    }

    // ====================== CHECK COOLDOWN ======================
    private boolean handleCheckCooldown(CommandSender sender, String[] args) {

        sender.sendMessage(ChatColor.YELLOW +
                "Check cooldown feature coming soon...");

        return true;
    }

    // ====================== TOGGLE SELF WELCOME ======================
    private boolean toggleSelfWelcome(CommandSender sender) {

        plugin.allowSelfWelcome = !plugin.allowSelfWelcome;

        sender.sendMessage(
                ChatColor.GREEN + "Self-welcome is now " +
                        (plugin.allowSelfWelcome
                                ? ChatColor.GREEN + "ENABLED"
                                : ChatColor.RED + "DISABLED")
        );

        return true;
    }

    // ====================== HELP ======================
    private void sendHelp(CommandSender sender) {

        sender.sendMessage(ChatColor.GOLD + "§lWelcome Bounty Commands");

        sender.sendMessage(
                ChatColor.YELLOW +
                        "/wb <player> §7→ Welcome a player"
        );

        if (sender.hasPermission("welcome.admin")) {

            sender.sendMessage(ChatColor.RED + "§lAdmin Commands:");

            sender.sendMessage(
                    ChatColor.YELLOW + "/uw forcewelcome <player>"
            );

            sender.sendMessage(
                    ChatColor.YELLOW +
                            "/uw removewbcooldown <player> [target]"
            );

            sender.sendMessage(
                    ChatColor.YELLOW + "/uw welcomeself"
            );
        }
    }

    // ====================== RANK DETECTION ======================
    private String getPrimaryRank(Player player) {

        List<String> rankOrder = plugin.getConfig()
                .getStringList("rank-order");

        for (String rank : rankOrder) {

            String cleanRank = rank.toLowerCase().trim();

            if (cleanRank.isEmpty()) continue;

            if (player.hasPermission("group." + cleanRank)
                    || player.hasPermission(cleanRank)) {

                return cleanRank;
            }
        }

        return "default";
    }
}