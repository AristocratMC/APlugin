package aplugin.init;

import aplugin.discord.DiscordMessageSender;
import aplugin.discord.DiscordMessageSenderImpl;
import aplugin.listeners.ChatEventListener;
import aplugin.utils.io.APConfiguration;
import aplugin.utils.io.APConfigurationImpl;
import aplugin.utils.io.BukkitUtils;
import aplugin.utils.io.BukkitUtilsImpl;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * Created by Aristocrat on 12/9/2017.
 */
public class APlugin extends JavaPlugin {
    private APConfiguration pluginConfiguration;
    private YamlConfiguration config;
    private Logger logger;
    private BukkitUtils utils;

    public APlugin() {
        utils = new BukkitUtilsImpl(this);
        logger = getLogger();
    }

    private DiscordMessageSender discordMessageSender;

    /**
     * Load configuration.
     *
     * Load Discord module
     */
    @Override
    public void onEnable() {
        // Load configuration file
        pluginConfiguration = new APConfigurationImpl("config.yml");
        config = pluginConfiguration.getYaml();

        if (config.getBoolean("aplugin.discord.enabled")) {
            loadDiscordIntegration();
        }

        loadEventListeners();
    }

    @Override
    public void onDisable() {

    }

    public void loadDiscordIntegration() {
        discordMessageSender = new DiscordMessageSenderImpl(this, config.getConfigurationSection("aplugin.discord"));
    }

    private void loadEventListeners() {
        if (discordMessageSender != null) {
            getServer().getPluginManager().registerEvents(new ChatEventListener(discordMessageSender), this);
        }
    }

    public DiscordMessageSender getDiscordMessageSender() {
        return discordMessageSender;
    }

    public void setDiscordMessageSender(DiscordMessageSender discordMessageSender) {
        this.discordMessageSender = discordMessageSender;
    }

    public BukkitUtils getUtils() {
        return utils;
    }
}
