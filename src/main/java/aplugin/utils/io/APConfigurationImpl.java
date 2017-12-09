package aplugin.utils.io;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Aristocrat on 12/9/2017.
 */
public class APConfigurationImpl extends APConfiguration {
    private final Plugin plugin;
    private final File pluginDataFolder;

    // Instance fields
    private File configFile;

    /**
     * Instantiates a APConfiguration using the given file name.
     * @param configurationFileName The file name as located under /resources.
     */
    public APConfigurationImpl(String configurationFileName) {
        plugin = Bukkit.getPluginManager().getPlugin("aplugin");
        pluginDataFolder = plugin.getDataFolder();

        if (!pluginDataFolder.exists()) {
            pluginDataFolder.mkdir();
        }

        configFile = new File(pluginDataFolder, configurationFileName);
        if (!configFile.exists()) {
            copy(plugin.getResource(configurationFileName), configFile);
        }
        configYaml = new YamlConfiguration();
        try {
            configYaml.load(configFile);
        } catch (Exception ex) {
            ex.printStackTrace();
            Bukkit.getLogger().severe(String.format("Failed to load YAML configuration from %s!", configurationFileName));
        }
    }

    private void copy(InputStream IN, File FILE) {
        try {
            OutputStream OUT = new FileOutputStream(FILE);
            byte[] BUF = new byte[1024];
            int LEN;
            while ((LEN = IN.read(BUF)) > 0) {
                OUT.write(BUF, 0, LEN);
            }
            OUT.close();
            IN.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            plugin.getLogger().severe("Failed to copy configuration from resources to filesystem!");
        }
    }
}
