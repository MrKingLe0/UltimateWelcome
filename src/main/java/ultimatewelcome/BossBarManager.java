package ultimatewelcome;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ultimatewelcome.utils.MessageUtil;

import java.util.ArrayList;
import java.util.List;

public class BossBarManager {

    private final Main plugin;
    private BossBar bossBar;
    private int colorIndex = 0;
    private List<BossBar.Color> colors = new ArrayList<>();

    public BossBarManager(Main plugin) {
        this.plugin = plugin;
    }

    public void showBossBar(Player player) {
        if (!plugin.getConfig().getBoolean("bossbar.enabled", true)) {
            return;
        }

        // Parse text with placeholders
        String text = plugin.getConfig().getString("bossbar.text", "&aWelcome %player%!");
        text = text.replace("%player%", player.getName());
        Component component = MessageUtil.parse(text);

        // Get initial color
        BossBar.Color color = getColor(plugin.getConfig().getString("bossbar.color", "PURPLE"));

        // Get style
        BossBar.Overlay style = getStyle(plugin.getConfig().getString("bossbar.style", "SOLID"));

        // Create bossbar
        bossBar = BossBar.bossBar(component, 1.0f, color, style);

        // Show to player
        player.showBossBar(bossBar);

        // Start progress countdown
        int duration = plugin.getConfig().getInt("bossbar.duration", 10);
        startProgressCountdown(duration);

        // Start color loop if enabled
        if (plugin.getConfig().getBoolean("bossbar.loop-color.enabled", false)) {
            loadLoopColors();
            startColorLoop();
        }
    }

    private void startProgressCountdown(int duration) {
        new BukkitRunnable() {
            double progress = 1.0;
            double decrement = 1.0 / (duration * 20); // 20 ticks per second

            @Override
            public void run() {
                progress -= decrement;
                if (progress <= 0 || bossBar == null) {
                    hideBossBar();
                    this.cancel();
                    return;
                }
                bossBar.progress((float) Math.max(0, progress));
            }
        }.runTaskTimer(plugin, 0L, 1L); // Run every tick

        // Auto-hide after duration
        new BukkitRunnable() {
            @Override
            public void run() {
                hideBossBar();
            }
        }.runTaskLater(plugin, duration * 20L);
    }

    private void startColorLoop() {
        int interval = plugin.getConfig().getInt("bossbar.loop-color.interval", 1);
        
        new BukkitRunnable() {
            @Override
            public void run() {
                if (bossBar == null || colors.isEmpty()) {
                    this.cancel();
                    return;
                }
                
                colorIndex = (colorIndex + 1) % colors.size();
                bossBar.color(colors.get(colorIndex));
            }
        }.runTaskTimer(plugin, interval * 20L, interval * 20L);
    }

    private void loadLoopColors() {
        colors.clear();
        for (String colorName : plugin.getConfig().getStringList("bossbar.loop-color.colors")) {
            colors.add(getColor(colorName));
        }
    }

    public void hideBossBar() {
        if (bossBar != null) {
            // Hide from all viewers
            Bukkit.getOnlinePlayers().forEach(player -> player.hideBossBar(bossBar));
            bossBar = null;
        }
    }

    private BossBar.Color getColor(String colorName) {
        try {
            return BossBar.Color.valueOf(colorName.toUpperCase());
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Invalid bossbar color: " + colorName + ". Using PURPLE.");
            return BossBar.Color.PURPLE;
        }
    }

    private BossBar.Overlay getStyle(String styleName) {
        try {
            return BossBar.Overlay.valueOf(styleName.toUpperCase());
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Invalid bossbar style: " + styleName + ". Using SOLID.");
            return BossBar.Overlay.PROGRESS;
        }
    }
}