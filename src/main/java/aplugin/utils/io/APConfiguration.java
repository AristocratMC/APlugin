package aplugin.utils.io;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

/**
 * Created by Aristocrat on 12/9/2017.
 */
public abstract class APConfiguration {
    protected YamlConfiguration configYaml;

    public YamlConfiguration getYaml() {
        return configYaml;
    }
}
