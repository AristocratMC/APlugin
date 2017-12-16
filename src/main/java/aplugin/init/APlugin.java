package aplugin.init;

import aplugin.api.DiscordApi;
import aplugin.api.DiscordApiImpl;
import aplugin.api.GameApi;
import aplugin.api.GameApiImpl;
import aplugin.discord.DiscordMessageSender;
import aplugin.discord.DiscordMessageSenderImpl;
import aplugin.listeners.ChatEventListener;
import aplugin.utils.io.APConfiguration;
import aplugin.utils.io.APConfigurationImpl;
import aplugin.utils.io.BukkitUtils;
import aplugin.utils.io.BukkitUtilsImpl;
import org.bukkit.Bukkit;
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
    private GameApi gameApi;
    private DiscordApi discordApi;

    public APlugin() {
        logger = getLogger();
        utils = new BukkitUtilsImpl(this);
        gameApi = new GameApiImpl(utils);
        discordApi = null;
    }

    public static APlugin getPlugin() {
        return (APlugin)Bukkit.getPluginManager().getPlugin("APlugin");
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

    private void loadDiscordIntegration() {
        discordMessageSender = new DiscordMessageSenderImpl(this, config.getConfigurationSection("aplugin.discord"));
        discordApi = new DiscordApiImpl(discordMessageSender);
    }

    private void loadEventListeners() {
        if (discordMessageSender != null) {
            getServer().getPluginManager().registerEvents(new ChatEventListener(discordMessageSender), this);
        }
    }

    @Deprecated
    public DiscordMessageSender getDiscordMessageSender() {
        return discordMessageSender;
    }

    @Deprecated
    public BukkitUtils getUtils() {
        return utils;
    }

    public GameApi getGameApi() {
        return gameApi;
    }

    public DiscordApi getDiscordApi() {
        return discordApi;
    }
}
