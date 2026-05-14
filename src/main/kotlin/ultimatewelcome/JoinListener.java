package ultimatewelcome;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.title.Title;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import java.time.Duration;
import java.util.List;

public class JoinListener implements Listener {

    private final Main plugin;

    public JoinListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        String name = player.getName();

        plugin.recordJoin(player.getUniqueId());

        String primaryRank = getPrimaryRank(player);

        // RANK-SPECIFIC WELCOME MESSAGE
        if (plugin.getConfig().getBoolean("welcome-messages.enabled", true)) {

            List<String> messages = plugin.getConfig().getStringList(
                    "welcome-messages.messages." + primaryRank
            );

            // Fallback to default
            if (messages.isEmpty()) {
                messages = plugin.getConfig().getStringList(
                        "welcome-messages.messages.default"
                );
            }

            final List<String> finalMessages = messages;

            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                for (String line : finalMessages) {
                    String formatted = line.replace("%player%", name);
                    player.sendMessage(color(formatted));
                }
            }, 20L);
        }

        // GLOBAL JOIN COMMANDS
        List<String> joinCommands = plugin.getConfig().getStringList("on-join-commands");

        for (String cmd : joinCommands) {

            if (cmd == null || cmd.isEmpty()) {
                continue;
            }

            String formatted = cmd.replace("%player%", name);

            plugin.executeConfiguredCommand(player, formatted);
        }

        // RANK JOIN COMMANDS
        if (plugin.getConfig().getBoolean("rank-join-commands.enabled", true)) {

            List<String> rankCommands = plugin.getConfig().getStringList(
                    "rank-join-commands.commands." + primaryRank
            );

            if (rankCommands.isEmpty()) {
                rankCommands = plugin.getConfig().getStringList(
                        "rank-join-commands.commands.default"
                );
            }

            for (String cmd : rankCommands) {

                if (cmd == null || cmd.isEmpty()) {
                    continue;
                }

                String formatted = cmd.replace("%player%", name);

                plugin.executeConfiguredCommand(player, formatted);
            }
        }

        // BROADCAST ON JOIN
        if (plugin.getConfig().getBoolean("welcome-broadcasts.enabled", false)) {

            List<String> broadcasts = plugin.getConfig().getStringList(
                    "welcome-broadcasts.messages." + primaryRank
            );

            // Fallback to default
            if (broadcasts.isEmpty()) {
                broadcasts = plugin.getConfig().getStringList(
                        "welcome-broadcasts.messages.default"
                );
            }

            final List<String> finalBroadcasts = broadcasts;

            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {

                for (String line : finalBroadcasts) {

                    String formatted = line.replace("%player%", name);

                    plugin.getServer().broadcastMessage(
                            color(formatted)
                    );
                }

            }, 20L);
        }

        // TITLE
        if (plugin.getConfig().getBoolean("title.enabled", false)) {

            String main = plugin.getConfig()
                    .getString("title.main", "")
                    .replace("%player%", name)
                    .replace("&", "§");

            String sub = plugin.getConfig()
                    .getString("title.sub", "")
                    .replace("%player%", name)
                    .replace("&", "§");

            Title title = Title.title(
                    LegacyComponentSerializer.legacySection().deserialize(main),
                    LegacyComponentSerializer.legacySection().deserialize(sub),
                    Title.Times.times(
                            Duration.ofSeconds(plugin.getConfig().getInt("title.fade-in", 1)),
                            Duration.ofSeconds(plugin.getConfig().getInt("title.stay", 3)),
                            Duration.ofSeconds(plugin.getConfig().getInt("title.fade-out", 2))
                    )
            );

            player.showTitle(title);
        }

        // FIREWORK ON JOIN
        if (plugin.getConfig().getBoolean("firework.enabled", false)) {

            int delay = plugin.getConfig().getInt("firework.delay", 20);

            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {

                try {

                    Firework firework = player.getWorld().spawn(
                            player.getLocation(),
                            Firework.class
                    );

                    FireworkMeta meta = firework.getFireworkMeta();

                    FireworkEffect.Type type = FireworkEffect.Type.valueOf(
                            plugin.getConfig()
                                    .getString("firework.type", "BALL")
                                    .toUpperCase()
                    );

                    FireworkEffect.Builder builder = FireworkEffect.builder()
                            .with(type);

                    for (String c : plugin.getConfig().getStringList("firework.colors")) {
                        builder.withColor(getColor(c));
                    }

                    for (String c : plugin.getConfig().getStringList("firework.fade-colors")) {
                        builder.withFade(getColor(c));
                    }

                    meta.addEffect(builder.build());

                    meta.setPower(
                            plugin.getConfig().getInt("firework.power", 1)
                    );

                    firework.setFireworkMeta(meta);

                } catch (Exception e) {

                    plugin.getLogger().warning(
                            "Firework error for " + name + ": " + e.getMessage()
                    );
                }

            }, delay);
        }

        // SOUND ON JOIN
        if (plugin.getConfig().getBoolean("sound.enabled", true)) {

            String soundName = plugin.getConfig().getString(
                    "sound.type",
                    "entity.player.levelup"
            );
            soundName = soundName.toLowerCase().replace("_", ".");
            float volume = (float) plugin.getConfig().getDouble(
                    "sound.volume",
                    1.0
            );

            float pitch = (float) plugin.getConfig().getDouble(
                    "sound.pitch",
                    1.0
            );

            try {

                player.playSound(
                        player.getLocation(),
                        soundName,
                        volume,
                        pitch
                );

            } catch (Exception e) {

                plugin.getLogger().warning(
                        "Invalid sound: " + soundName + " | Using default"
                );

                player.playSound(
                        player.getLocation(),
                        "entity.player.levelup",
                        1.0f,
                        1.0f
                );
            }
        }
    }

    // HELPER METHODS
    private String getPrimaryRank(Player player) {

        List<String> rankOrder = plugin.getConfig().getStringList("rank-order");

        for (String rank : rankOrder) {

            String cleanRank = rank.toLowerCase().trim();

            if (cleanRank.isEmpty()) {
                continue;
            }

            if (player.hasPermission("group." + cleanRank)
                    || player.hasPermission(cleanRank)) {

                return cleanRank;
            }
        }

        return "default";
    }

    private Color getColor(String name) {

        if (name == null) {
            return Color.WHITE;
        }

        return switch (name.toUpperCase()) {

            case "RED" -> Color.RED;
            case "BLUE" -> Color.BLUE;
            case "GREEN" -> Color.GREEN;
            case "YELLOW" -> Color.YELLOW;
            case "ORANGE" -> Color.ORANGE;
            case "PURPLE" -> Color.PURPLE;
            case "WHITE" -> Color.WHITE;
            case "AQUA", "CYAN" -> Color.AQUA;
            case "BLACK" -> Color.BLACK;
            case "GRAY" -> Color.GRAY;

            default -> Color.WHITE;
        };
    }

    private String color(String msg) {
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', msg);
    }
}