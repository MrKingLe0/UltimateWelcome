package ultimatewelcome;

import ultimatewelcome.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    private final Main plugin;

    public ReloadCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        plugin.reloadConfig();
        plugin.reloadSettingsMessages();
        String message = plugin.getSettingsMessages().getString("plugin-reload.success", "&aConfig reloaded!");
        sender.sendMessage(MessageUtil.parse(message));
        return true;
    }
}