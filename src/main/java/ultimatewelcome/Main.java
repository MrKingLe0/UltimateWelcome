package ultimatewelcome;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Main extends JavaPlugin {

    public boolean allowSelfWelcome = false;

    private final Map<UUID, Long> joinTimes = new ConcurrentHashMap<>();
    private final Map<String, Long> welcomeCooldowns = new ConcurrentHashMap<>();
    private FileConfiguration settingsMessages;
    private BossBarManager bossBarManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        
        // Save default settings-messages.yml if it doesn't exist
        if (!new File(getDataFolder(), "settings-messages.yml").exists()) {
            saveResource("settings-messages.yml", false);
        }

        this.bossBarManager = new BossBarManager(this);

        // Load settings messages
        settingsMessages = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "settings-messages.yml"));

        Bukkit.getConsoleSender().sendMessage(
                "§9[UltimateWelcome] §aPlugin Enabled! §7v" +
                getPluginMeta().getVersion()
        );
        Bukkit.getConsoleSender().sendMessage(
                "§d  ==============================================================================\n" +
                "§b       __  ______  _            __        _      __    __\n" +
                "§b      / / / / / /_(_)_ _  ___ _/ /____   | | /| / /__ / /______  __ _  ___\n" +
                "§b     / /_/ / / __/ /  ' \\/ _ `/ __/ -_)  | |/ |/ / -_) / __/ _ \\/  ' \\/ -_)\n" +
                "§b     \\____/_/\\__/_/_/_/_/\\_,_/\\__/\\__/   |__/|__/\\__/_/\\__/\\___/_/_/_/\\__/\n\n" +
                "§d  =============================================================================="
        );

        // EVENTS
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new LeaveListener(this), this);

        // COMMAND INSTANCE (ONE ONLY)
        WelcomeCommand welcomeCommand = new WelcomeCommand(this);

        // wb command
        if (getCommand("wb") != null) {
            getCommand("wb").setExecutor(welcomeCommand);
            getCommand("wb").setTabCompleter(welcomeCommand);
        }

        // uw command
        if (getCommand("uw") != null) {
            getCommand("uw").setExecutor(welcomeCommand);
            getCommand("uw").setTabCompleter(welcomeCommand);
        }

        // reload
        if (getCommand("uwreload") != null) {
            getCommand("uwreload").setExecutor(new ReloadCommand(this));
        }
    }

    // Getter for settings messages
    public FileConfiguration getSettingsMessages() {
        return settingsMessages;
    }

    // Reload method
    public void reloadSettingsMessages() {
        settingsMessages = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "settings-messages.yml"));
    }

    // JOIN TIME TRACKING
    public void recordJoin(UUID uuid) {
        joinTimes.put(uuid, System.currentTimeMillis());
    }

    public Long getJoinTime(UUID uuid) {
        return joinTimes.get(uuid);
    }
    // BOSS BAR MANAGER
    public BossBarManager getBossBarManager() {
    return bossBarManager;
    }

    public void executeConfiguredCommand(Player player, String commandLine) {

        if (commandLine == null || commandLine.isEmpty()) {
            return;
        }

        String cmd = commandLine.trim();

        // CONSOLE COMMAND
        if (cmd.startsWith("[CONSOLE]")) {

            cmd = cmd.replaceFirst("\\[CONSOLE\\]\\s*", "");

            Bukkit.dispatchCommand(
                    Bukkit.getConsoleSender(),
                    cmd
            );

            return;
        }

        // PLAYER COMMAND
        if (cmd.startsWith("[PLAYER]")) {

            cmd = cmd.replaceFirst("\\[PLAYER\\]\\s*", "");

            player.performCommand(cmd);

            return;
        }

        // DEFAULT = CONSOLE
        Bukkit.dispatchCommand(
                Bukkit.getConsoleSender(),
                cmd
        );
    }
    // COOLDOWN SYSTEM
    public void setWelcomeCooldown(UUID welcomer, UUID target) {
        String key = welcomer.toString() + ":" + target.toString();
        welcomeCooldowns.put(key, System.currentTimeMillis());
    }

    public boolean canWelcome(UUID welcomer, UUID target) {
        String key = welcomer.toString() + ":" + target.toString();
        Long lastTime = welcomeCooldowns.get(key);
        
        if (lastTime == null) return true;
        
        long cooldownMillis = getConfig().getInt("welcome-bounty.reward-cooldown-seconds", 1800) * 1000L;
        return (System.currentTimeMillis() - lastTime) >= cooldownMillis;
    }

    public long getRemainingCooldown(UUID welcomer, UUID target) {
        String key = welcomer.toString() + ":" + target.toString();
        Long lastTime = welcomeCooldowns.get(key);
        if (lastTime == null) return 0;
        
        long cooldownMillis = getConfig().getInt("welcome-bounty.reward-cooldown-seconds", 1800) * 1000L;
        long remaining = cooldownMillis - (System.currentTimeMillis() - lastTime);
        return Math.max(0, remaining / 1000);
    }

    public void removeSpecificCooldown(UUID welcomer, UUID target) {
        String key = welcomer.toString() + ":" + target.toString();
        welcomeCooldowns.remove(key);
    }

    public void removePlayerCooldowns(UUID player) {
        String playerStr = player.toString();
        welcomeCooldowns.keySet().removeIf(key -> key.startsWith(playerStr + ":") || key.endsWith(":" + playerStr));
    }

    @Override
    public void onDisable() {
        joinTimes.clear();
        welcomeCooldowns.clear();
        Bukkit.getConsoleSender().sendMessage(
                "§9[UltimateWelcome] §cPlugin Disabled! §7v" +
                getPluginMeta().getVersion()
        );
        Bukkit.getConsoleSender().sendMessage(
                "§d  ==============================================================================\n" +
                "§b       __  ______  _            __        _      __    __\n" +
                "§b      / / / / / /_(_)_ _  ___ _/ /____   | | /| / /__ / /______  __ _  ___\n" +
                "§b     / /_/ / / __/ /  ' \\/ _ `/ __/ -_)  | |/ |/ / -_) / __/ _ \\/  ' \\/ -_)\n" +
                "§b     \\____/_/\\__/_/_/_/_/\\_,_/\\__/\\__/   |__/|__/\\__/_/\\__/\\___/_/_/_/\\__/\n\n" +
                "§d  =============================================================================="
        );
    }
}