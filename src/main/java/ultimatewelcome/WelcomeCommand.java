package ultimatewelcome;
import ultimatewelcome.utils.MessageUtil;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class WelcomeCommand implements CommandExecutor, TabCompleter {

    private final Main plugin;

    public WelcomeCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String cmd = command.getName().toLowerCase();

        // /wb
        if (cmd.equals("wb")) {

            if (!(sender instanceof Player player)) {
                String message = plugin.getSettingsMessages().getString("uw-only-players", "&cOnly players can use this command.");
                sender.sendMessage(MessageUtil.parse(message));
                return true;
            }

            if (args.length == 0) {
                sendHelp(sender);
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                String message = plugin.getSettingsMessages().getString("wb-player-not-found", "&cPlayer %target% not found or not online.");
                message = message.replace("%target%", args[0]);
                player.sendMessage(MessageUtil.parse(message));
                return true;
            }

            if (player.getUniqueId().equals(target.getUniqueId()) && !plugin.allowSelfWelcome) {
                String message = plugin.getSettingsMessages().getString("wb-no-selfwelcome", "&cYou cannot welcome yourself.");
                player.sendMessage(MessageUtil.parse(message));
                return true;
            }

            if (!plugin.canWelcome(player.getUniqueId(), target.getUniqueId())) {
                long remaining = plugin.getRemainingCooldown(player.getUniqueId(), target.getUniqueId());
                String message = plugin.getSettingsMessages().getString("wb-cooldown", "&cYou must wait %time% before welcoming %target% again.");
                message = message.replace("%time%", remaining + "s").replace("%target%", target.getName());
                player.sendMessage(MessageUtil.parse(message));
                return true;
            }

            int expirySeconds = plugin.getConfig().getInt("welcome-bounty.expiry-seconds", 60);

            Long joinTime = plugin.getJoinTime(target.getUniqueId());
        if (joinTime == null || (System.currentTimeMillis() - joinTime) / 1000 > expirySeconds) {
            String message = plugin.getSettingsMessages().getString("wb-expired", "&c%target% can no longer be welcomed because they joined more than %time% ago.");
            message = message.replace("%time%", expirySeconds + "s").replace("%target%", target.getName());
            player.sendMessage(MessageUtil.parse(message));
            return true;
        }

            String message = plugin.getSettingsMessages().getString("wb-no-reward", "&cYou don't receive a reward for welcoming %target% because they are in the welcome bounty system but don't have a reward configured for their rank.");
            message = message.replace("%target%", target.getName());
            player.sendMessage(MessageUtil.parse(message));
            return true;
        }

        // /uw
        if (cmd.equals("uw")) {

            if (!sender.hasPermission("welcome.admin")) {
                String message = plugin.getSettingsMessages().getString("uw-no-permission", "&cYou don't have permission to use this command.");
                sender.sendMessage(MessageUtil.parse(message));
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

            String message = plugin.getSettingsMessages().getString("uw-unknownsubcommand", "&cUnknown subcommand. Use /uw help for a list of commands.");
            sender.sendMessage(MessageUtil.parse(message));
            return true;
        }

        return false;
    }

    // TAB COMPLETE
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        String cmd = command.getName().toLowerCase();
        List<String> suggestions = new ArrayList<>();

        if (cmd.equals("wb")) {

            if (args.length == 1) {
                Bukkit.getOnlinePlayers().forEach(p -> suggestions.add(p.getName()));
            }

            return filter(suggestions, args);
        }

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
                "welcome-bounty.rewards." + targetRank + ".amount", 0);

        // reward command
        String rewardCmd = plugin.getConfig().getString(
                "welcome-bounty.rewards." + targetRank + ".reward-command"
        );

        if (rewardCmd == null || rewardCmd.isEmpty()) {
            rewardCmd = plugin.getConfig().getString("welcome-bounty.reward-command");
        }

        if (rewardCmd != null && !rewardCmd.isEmpty()) {

            String finalCmd = rewardCmd
                    .replace("%player%", welcomer.getName())
                    .replace("%target%", target.getName())
                    .replace("%amount%", String.valueOf(amount));

            plugin.executeConfiguredCommand(welcomer, finalCmd);
        }

        // rank commands
        for (String cmd : plugin.getConfig().getStringList("welcome-bounty.rewards." + targetRank + ".wb-commands")) {

            if (cmd == null || cmd.isEmpty()) continue;

            plugin.executeConfiguredCommand(welcomer,
                    cmd.replace("%player%", welcomer.getName())
                            .replace("%target%", target.getName())
                            .replace("%amount%", String.valueOf(amount))
            );
        }

        // global commands
        for (String cmd : plugin.getConfig().getStringList("welcome-bounty.wb-commands")) {

            if (cmd == null || cmd.isEmpty()) continue;

            plugin.executeConfiguredCommand(welcomer,
                    cmd.replace("%player%", welcomer.getName())
                            .replace("%target%", target.getName())
                            .replace("%amount%", String.valueOf(amount))
            );
        }

        // BROADCAST (FIXED: ALL FORMATS SUPPORTED)
        for (String line : plugin.getConfig().getStringList("welcome-bounty.broadcast-message")) {

            String msg = line
                    .replace("%player%", welcomer.getName())
                    .replace("%target%", target.getName())
                    .replace("%amount%", String.valueOf(amount))
                    .replace("%currency%", currency);

            Bukkit.getServer().sendMessage(MessageUtil.parse(msg));
        }

        // WELCOMER MESSAGE
        for (String line : plugin.getConfig().getStringList("welcome-bounty.welcomer-message")) {

            String msg = line
                    .replace("%player%", welcomer.getName())
                    .replace("%target%", target.getName())
                    .replace("%amount%", String.valueOf(amount))
                    .replace("%currency%", currency);

            welcomer.sendMessage(MessageUtil.parse(msg));
        }

        // TARGET MESSAGE
        for (String line : plugin.getConfig().getStringList("welcome-bounty.welcomed-message")) {

            String msg = line
                    .replace("%player%", welcomer.getName())
                    .replace("%target%", target.getName())
                    .replace("%amount%", String.valueOf(amount))
                    .replace("%currency%", currency);

            target.sendMessage(MessageUtil.parse(msg));
        }

        // SOUND
        if (plugin.getConfig().getBoolean("welcome-bounty.sound.enabled", true)) {

            String soundName = plugin.getConfig().getString(
                    "welcome-bounty.sound.type",
                    "ENTITY_PLAYER_LEVELUP"
            );

            float volume = (float) plugin.getConfig().getDouble("welcome-bounty.sound.volume", 1.0);
            float pitch = (float) plugin.getConfig().getDouble("welcome-bounty.sound.pitch", 1.0);

            try {
                welcomer.playSound(welcomer.getLocation(), soundName, volume, pitch);
            } catch (Exception e) {
                welcomer.playSound(welcomer.getLocation(),
                        "entity.player.levelup", 1.0f, 1.0f);
            }
        }

        plugin.setWelcomeCooldown(welcomer.getUniqueId(), target.getUniqueId());
    }

    // FORCE
    private boolean handleForceWelcome(CommandSender sender, String[] args) {

        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /uw forcewelcome <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[1]);

        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        if (sender instanceof Player welcomer) {
            executeWelcome(welcomer, target);
        }

        return true;
    }

    // COOLDOWN REMOVE
    private boolean handleRemoveCooldown(CommandSender sender, String[] args) {
        sender.sendMessage(ChatColor.YELLOW + "Cooldown system unchanged.");
        return true;
    }

    private boolean handleCheckCooldown(CommandSender sender, String[] args) {
        sender.sendMessage(ChatColor.YELLOW + "Coming soon...");
        return true;
    }

    private boolean toggleSelfWelcome(CommandSender sender) {

        plugin.allowSelfWelcome = !plugin.allowSelfWelcome;

        sender.sendMessage(ChatColor.GREEN + "Self-welcome: " +
                (plugin.allowSelfWelcome ? "ENABLED" : "DISABLED"));

        return true;
    }

    private void sendHelp(CommandSender sender) {
        String header = plugin.getSettingsMessages().getString("uw-help.header", "<gold>Welcome Commands</gold>");
        sender.sendMessage(MessageUtil.parse(header));
        
        String wb = plugin.getSettingsMessages().getString("uw-help.wb", "<yellow>/wb <player></yellow> <gray>- Welcome a player</gray>");
        sender.sendMessage(MessageUtil.parse(wb));

        if (sender.hasPermission("welcome.admin")) {
            String forcewelcome = plugin.getSettingsMessages().getString("uw-help.uw-forcewelcome", "<red>/uw forcewelcome <player></red> <gray>- Force welcome</gray>");
            sender.sendMessage(MessageUtil.parse(forcewelcome));
            
            String resetcooldown = plugin.getSettingsMessages().getString("uw-help.uw-resetcooldown", "<red>/uw resetcooldown <player></red> <gray>- Reset cooldown</gray>");
            sender.sendMessage(MessageUtil.parse(resetcooldown));
            
            String welcomeself = plugin.getSettingsMessages().getString("uw-help.uw-welcomeself", "<red>/uw welcomeself</red> <gray>- Toggle self-welcome</gray>");
            sender.sendMessage(MessageUtil.parse(welcomeself));
            
            String reload = plugin.getSettingsMessages().getString("uw-help.uw-reload", "<red>/uwreload</red> <gray>- Reload config</gray>");
            sender.sendMessage(MessageUtil.parse(reload));
        }
        
        String footer = plugin.getSettingsMessages().getString("uw-help.footer", "");
        if (!footer.isEmpty()) {
            sender.sendMessage(MessageUtil.parse(footer));
        }
    }
    
    private String getPrimaryRank(Player player) {

        for (String rank : plugin.getConfig().getStringList("rank-order")) {

            String clean = rank.toLowerCase().trim();

            if (player.hasPermission("group." + clean)
                    || player.hasPermission(clean)) {
                return clean;
            }
        }

        return "default";
    }
}